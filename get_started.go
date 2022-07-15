//package main
//
//import (
//	"flag"
//
//	"github.com/apache/beam/sdks/v2/go/pkg/beam"
//	"github.com/apache/beam/sdks/v2/go/pkg/beam/io/textio"
//)
//
//// Creating custom options
//var (
//	input  = flag.String("input", "gs://my-bucket/input", "Input for the pipeline")
//	output = flag.String("output", "gs://my-bucket/output", "Output for the pipeline")
//)
//
//func main() {
//	// If beamx or Go flags are used, flags must be parsed first, before beam.Init() is called.
//	flag.Parse()
//
//	// beam.Init() is an initialization hook that must be called near the beginning of main(), before creating a pipeline
//	beam.Init()
//
//	// Create the Pipeline object and root scope
//	pipeline, scope := beam.NewPipelineWithRoot()
//
//	// Here’s how you would apply textio.Read to your Pipeline to create a PCollection
//	// Read the file at the URI
//	// the lines as a PCollection<string>.
//	// Notice the scope as the first variable when calling the method as is needed when calling all transforms.
//	lines := textio.Read(scope, "amghezi.txt")
//
//}
