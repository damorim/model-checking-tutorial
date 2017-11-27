# Model Checking

Model Checking is a technique originally proposed for checking the correctness of system designs. A model is a characterization of a computing system and a property describes intended behavior of the system (e.g., deadlock freedom, data race freedom, etc.).  The languages for characterization models and properties vary. The traditional approach to model checking, often referred to as automata model checking, translates the model in a graph, where nodes correspond to abstract states and edges correspond to state transitions. Safety properties, like those mentioned above, translates to graph reachability (i.e., finding property violations consists of finding "bad" states, reachable from the initial state, that violates the given property). The main challenge of model checking is scalability as the number of states is typically very high.  For this reason, this problem is often referred to as "the state explosion problem".

In the last decade, the community started to gain interest in program model checking, where the subject of analysis is a program as opposed to program models. In this setup, states in the model correspond to real program states. The motivation is that dealing with programs directly may reduce the gap to transition model checking to practice. 

The focus of this tutorial is on program model checking!

## Disclaimer

We will use Docker containers to facilitate the setup of tools.  For your convenience, the only requirement for running this demo is Docker itself. All dependencies necessary to run the tools are encoded within each Docker container that we created.

Software Requirements:
- Docker >= 17.09.0-ce ([Download](https://store.docker.com/search?offering=enterprise&type=edition)).

Note that, in all docker containers below, you can edit files with **emacs**, **vi** or **nano**.

# Java Pathfinder (JPF)

JPF is an explicit-state model checker for Java programs.  It is specially focused in the verification of concurrent programs.

### Running:

Spawn JPF container:
```bash
$ docker run -it --rm lhsm/jpf-examples
```

If you want to play with those samples:
```bash
$ docker run -it --rm lhsm/jpf-examples bash
```

Modify samples (.java) and configuration (.jpf) files:
```bash
$ cd src/examples/
$ {emacs,nano,vim} "file you want to modify"{.java, .jpf}
$ cd ../../ && ant build
$ java -jar build/RunJPF.jar src/examples/"file you modified".jpf
```

To display every possible JPF configuration option:
```bash
$ java -jar build/RunJPF.jar src/examples/"file you modified".jpf -show
```

# Symbolic Pathfinder (SPF)

SPF is a symbolic execution engine implemented on top of the JPF model checker.

### Running:

Spawn SPF container:
```bash
$ docker run -it --rm davinomjr/spf-examples
```

If you want to play with those samples:
```bash
$ docker run -it --rm davinomjr/spf-examples bash
```

Modify samples (.java) and configuration (.jpf) files:
```bash
$ cd src/examples/
$ {emacs,nano,vim} "file you want to modify"{.java, .jpf}
$ cd ../../ && ant build
```

# Java Bounded Model Checker (JBMC)

JBMC is a Bounded Model Checker for Java program that uses the same back-end of the Bounded Model Checker for C programs, CBMC.

### Running

Spawn JBMC container:
```bash
$ docker run -it --rm davinomjr/jbmc-examples
```

You can run different tests in regression/cbmc-java folder. For example:
```bash
$ cd regression/cbmc-java
$ cd Inheritance1/
$ jbmc Inheritance1.class
```
