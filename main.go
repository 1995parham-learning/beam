package main

import (
	"flag"

	"github.com/apache/beam/sdks/v2/go/pkg/beam"
)

var (
	input  = flag.String("input", "gs://my-bucket/input", "Input for the pipeline")
	output = flag.String("output", "gs://my-bucket/output", "Output for the pipeline")
)

func main() {
	// If beamx or Go flags are used, flags must be parsed first, before beam.Init() is called.
	flag.Parse()

	// beam.Init() is an initialization hook that must be called near the beginning of main(), before creating a pipeline
	beam.Init()

	// Create the Pipeline object and root scope
	pipeline, scope := beam.NewPipelineWithRoot()
}
