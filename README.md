# Model Checking

Model Checking is a technique to verify that a model satisfies a given property. A model typically characterizes a computing system and a property is what the user wants to verify, e.g., deadlock freedom, race freedom, or some domain-specific rule. The traditional approach to model checking translates the model to be verified in a graph, where nodes correspond to program states and edges correspond to state transitions. Safety properties, like those mentioned above, translates to graph reachability (i.e., finding property violations consists of finding "bad" states, reachable from the initial state, that violates the given property). This approach to model checking is often referred to as algorithmic model checking or automata model checking. 

In the last decade, the community started to gain interest in program model checking, where the subject of analysis is a program as opposed to a model of a program. In this setup, states in the model correspond to program states, containing pc, heap, stack, and static area. The focus of this tutorial is on program model checking.

# JPF

JPF is an explicit-state model checker for Java programs.  It is specially focused in the verification of concurrent programs.

### Running JPF:

We will use Docker containers to facilitate the setup of tools.  The only requirement for using this demo is Docker itself.  All dependencies necessary to run the tools are encoded within each Docker container.

Software Requirements:
- Docker >= 17.09.0-ce ([Download](https://store.docker.com/search?offering=enterprise&type=edition)).

Spawn JPF container:
> $ docker run -it lhsm/jpf-examples:latest

```

# SPF

TODO
