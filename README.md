clusterprimitives
=================

Library that adds a set of cluster programming primitives to Spring Framework

## Goal

Provide high level clustering primitives that are easy to use, naturally integrate into Spring Framework, and solve some of the typical cluster related problems. Library is intended to be an abstraction layer over implementations such as JGroups or Apache ZooKeeper.

Currently, only leader election via JGroups is provided.

## Disclamer

This is only proof of concept code created in my effort to evaluate usefulness of high level abstractions over cluster functionallity that integrates well with Spring Framework's programming paradigms. It's not well tested and possibly not even correct.

