# Apache beam
Beam SDKs are used to create data processing pipelines.

## Overview
You need to first create a driver program.Your driver program defines your pipeline, including all of the inputs, 
transforms, and outputs; it also sets execution options for your pipeline. These include the Pipeline Runner, which 
determines what back-end your pipeline will run on.

The beam abstractions work with both batch and streaming data sources. Abstractions:

#### Pipeline: 
All Beam driver programs must create a **Pipeline**. When you create if the, you must also specify the execution options
that tell the **Pipeline** where and how to run.

#### PCollection:
A **PCollection represents a distributed data set that your Beam pipeline operates on**

#### PTransform:
A **PTransform** represents a data processing operation, or a step, in your pipeline. Every **PTransform** takes one or 
more **PCollection** objects as input, performs a processing function that you provide on the elements of that 
**PCollection**, and produces zero ot more output **PCollection** objects.

#### Scope:
The Go SDK has an explicit scope variable used to build a **Pipeline**. A **Pipeline** can return it’s root scope with
the **Root()** method. The scope variable is passed to **PTransform** functions to place them in the **Pipeline** that 
owns the **Scope**.

#### I/O transforms

### Typical Beam Driver Work Flow

#### Create a Pipeline

#### Create an initial PCollection
Either using the IOs (external storage) or using a **Create** transform to build a **PCollection** from in-memory data.

#### Apply PTransforms to each PCollection
A transform creates a new output **PCollection** without modifying the input collection. Think of **PCollection**s as 
variables and **PTransform**s as functions applied to these variables: the shape of the pipeline can be an arbitrary 
complex processing graph.

#### Use IOs to write final PCollection to an external source

#### Run using the designated Pipeline Runner
The Pipeline Runner that you designate constructs a **workflow graph**. That graph is then executed using the appropriate
distributed processing back-end, becoming an asynchronous "job" (or equivalent) on that back-end.

## Configuring pipeline options
### Setting PipelineOptions from command-line arguments
Use Go flags. Flags must be parsed before beam.Init() is called.

### Creating custom options
