# Model Checking

Model Checking is a verification technique. A model is a characterization of a computing system and a property is characterization of some behavior that should be present in the system (e.g., deadlock freedom, race freedom, etc.).  The languages for characterization model and properties on that model vary. The traditional approach to model checking, often referred to as algorithmic model checking or automata model checking, translates the model to be verified in a graph, where nodes correspond to abstract program states and edges correspond to state transitions. Safety properties, like those mentioned above, translates to graph reachability (i.e., finding property violations consists of finding "bad" states, reachable from the initial state, that violates the given property). The main challenge of model checking is scalability as the number of states is typically very high. 

In the last decade, the community started to gain interest in program model checking, where the subject of analysis is a program as opposed to program models. In this setup, states in the model correspond to program states, containing pc, heap, stack, and static area. The motivation is that dealing with programs directly may reduce the gap to transition model checking to practice. The focus of this tutorial is on program model checking.

# JPF

JPF is an explicit-state model checker for Java programs.  It is specially focused in the verification of concurrent programs.

# SPF

TODO

## Running Samples

We will use Docker containers to facilitate the setup of tools.  The only requirement for using this demo is Docker itself.  All dependencies necessary to run the tools are encoded within each Docker container.

Software Requirements:
- Docker >= 17.09.0-ce ([Download](https://store.docker.com/search?offering=enterprise&type=edition)).

### Running JPF:

Spawn JPF container:
> $ docker run -it lhsm/jpf-examples:latest

Run samples with:
> $ ./jpf-samples.sh

Modify samples (.java) and configuration (.jpf) files:
> $ cd src/examples/
> $ nano "class you want to modify"{.java, .jpf}
> $ cd ../../ && ant build

### Running SPF

Spawn SPF container:
> $ docker run -it dmtsj/spf-examples:latest

Run samples with:
> $ ./spf-samples.sh

Modify samples and .jpf files:
> $ cd src/examples/
> $ nano "class you want to modify"{.java, .jpf}
> $ cd ../../ && ant build

