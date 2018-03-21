package com.tribe3k.alluro.models

interface Dependency {
    int getDepth()

    void addDependency(Dependency dependency)

    List<Dependency> getDependencies()
}
