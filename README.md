Model Checking

Model Checking is a technique to verify that a model satisfies a given property. A model typically characterizes a computing system and a property is what the user wants to verify, e.g., deadlock freedom, race freedom, or some domain-specific rule. The traditional approach to model checking translates models in a graph, where nodes correponds to program states and edges correspond to state transition.  Safety properties, like those mentioned above, translates to graph reachability.  More recently, the community started to gain interest in program model checking, where the subject of analysis is the program.  In this setup, model states correspond to program states (with pc, heap, stack, and static area).

# JPF

...


### Running a basic sample:

System requirements:
- Docker >= 17.09.0-ce ([Download](https://store.docker.com/search?offering=enterprise&type=edition)).

To run the samples, simply run the following command:
```markdown

> $ docker run -it lhsm/jpf-examples:latest

```

# SPF

TODO
