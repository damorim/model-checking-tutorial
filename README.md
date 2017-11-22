# Model Checking

Model Checking is a verification technique. A model is a characterization of a computing system and a property is characterization of some behavior that should be present in the system (e.g., deadlock freedom, race freedom, etc.).  The languages for characterization model and properties on that model vary. The traditional approach to model checking, often referred to as algorithmic model checking or automata model checking, translates the model to be verified in a graph, where nodes correspond to abstract program states and edges correspond to state transitions. Safety properties, like those mentioned above, translates to graph reachability (i.e., finding property violations consists of finding "bad" states, reachable from the initial state, that violates the given property). The main challenge of model checking is scalability as the number of states is typically very high. 

In the last decade, the community started to gain interest in program model checking, where the subject of analysis is a program as opposed to program models. In this setup, states in the model correspond to program states, containing pc, heap, stack, and static area. The motivation is that dealing with programs directly may reduce the gap to transition model checking to practice. The focus of this tutorial is on program model checking.

# JPF

JPF is an explicit-state model checker for Java programs.  It is specially focused in the verification of concurrent programs.

# SPF

TODO

# JBMC

TODO

## Running Samples

We will use Docker containers to facilitate the setup of tools.  The only requirement for using this demo is Docker itself.  All dependencies necessary to run the tools are encoded within each Docker container.

Software Requirements:
- Docker >= 17.09.0-ce ([Download](https://store.docker.com/search?offering=enterprise&type=edition)).

Note that, in all docker containers below, you can edit .java files with **emacs**, **vi** or **nano**.

### Running JPF:

Spawn JPF container:
```bash
$ docker run -it lhsm/jpf-examples
```

If you want to play with those samples:
```bash
$ docker run -it lhsm/jpf-examples bash
```

Modify samples (.java) and configuration (.jpf) files:
```bash
$ cd src/examples/
$ nano "class you want to modify"{.java, .jpf}
$ cd ../../ && ant build
```

### Running SPF:

Spawn SPF container:
```bash
$ docker run -it dmtsj/spf-examples
```

If you want to play with those samples:
```bash
$ docke run -it dmtsj/spf-examples bash
```

Modify samples and .jpf files:
```bash
$ cd src/examples/
$ nano "class you want to modify"{.java, .jpf}
$ cd ../../ && ant build
```

### Running JBMC

Spawn JBMC container:
```bash
$ docker run -it dmtsj/jbmc-examples
```

You can run different tests located on regression/cbmc-java folder. For example:
```bash
$ cd regression/cbmc-java
$ cd Inheritance1/
$ jbmc Inheritance1.class
```
