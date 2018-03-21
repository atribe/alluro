package com.tribe3k.alluro.models


class RootContext implements Dependency {
    private final int depth = -1
    private final String name
    private List<NestedDependency> dependencies = new ArrayList<>()

    RootContext(final String name) {
        this.name = name
    }

    String getName() {
        return name
    }

    @Override
    int getDepth() {
        return depth
    }

    @Override
    void addDependency(Dependency nestedDependency) {
        dependencies.add(nestedDependency)
    }

    @Override
    List<NestedDependency> getDependencies() {
        return dependencies
    }

    @Override
    String toString() {
        return "RootContext{" +
                "depth=" + depth +
                ", name='" + name + '\'' +
                ", dependencies=" + dependencies +
                '}'
    }
}