package main

import (
	"flag"
	"time"

	"github.com/apache/beam/sdks/v2/go/pkg/beam/register"
)

// I COULDN'T RUN THIS CODE
import (
	"context"

	"github.com/apache/beam/sdks/v2/go/pkg/beam"
	"github.com/apache/beam/sdks/v2/go/pkg/beam/core/graph/window"
	"github.com/apache/beam/sdks/v2/go/pkg/beam/io/pubsubio"
	"github.com/apache/beam/sdks/v2/go/pkg/beam/io/xlang/kafkaio"
	"github.com/apache/beam/sdks/v2/go/pkg/beam/log"
	"github.com/apache/beam/sdks/v2/go/pkg/beam/x/beamx"
)

var (
	expansionAddr = flag.String("expansion_addr", "",
		"Address of Expansion Service. If not specified, attempts to automatically start an appropriate expansion service.")
	bootstrapServers = flag.String("bootstrap_servers", "",
		"(Required) URL of the bootstrap servers for the Kafka cluster. Should be accessible by the runner.")
	topic = flag.String("topic", "kafka_taxirides_realtime", "Kafka topic to write to and read from.")
)

func init() {
	register.DoFn2x0[context.Context, []byte](&LogFn{})
}

// LogFn is a DoFn to log rides.
type LogFn struct{}

// ProcessElement logs each element it receives.
func (fn *LogFn) ProcessElement(ctx context.Context, elm []byte) {
	log.Infof(ctx, "Ride info: %v", string(elm))
}

// FinishBundle waits a bit so the job server finishes receiving logs.
func (fn *LogFn) FinishBundle() {
	time.Sleep(2 * time.Second)
}

func main() {
	flag.Parse()
	beam.Init()

	ctx := context.Background()

	p := beam.NewPipeline()
	s := p.Root()

	// Read from Pubsub and write to Kafka.
	data := pubsubio.Read(s, "pubsub-public-data", "taxirides-realtime", nil)
	kvData := beam.ParDo(s, func(elm []byte) ([]byte, []byte) { return []byte(""), elm }, data)
	windowed := beam.WindowInto(s, window.NewFixedWindows(15*time.Second), kvData)
	kafkaio.Write(s, *expansionAddr, *bootstrapServers, *topic, windowed)

	// Simultaneously read from Kafka and log any element received.
	read := kafkaio.Read(s, *expansionAddr, *bootstrapServers, []string{*topic})
	vals := beam.DropKey(s, read)
	beam.ParDo0(s, &LogFn{}, vals)

	if err := beamx.Run(ctx, p); err != nil {
		log.Fatalf(ctx, "Failed to execute job: %v", err)
	}
}
