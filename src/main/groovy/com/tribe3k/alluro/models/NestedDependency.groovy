package com.tribe3k.alluro.models


class NestedDependency implements Dependency {
    private int depth
    private String relationship
    private String group
    private String name
    private Dependency parentDependency
    List<NestedDependency> dependencies = new ArrayList<>()

    NestedDependency(final int depth, final String relationship, final String group, final String name) {
        this.depth = depth
        this.relationship = relationship
        this.group = group.replace("-", "_")
        this.name = name.replace("-", "_")
    }

    String getGroupAndName() {
        return group + "." + name
    }

    @Override
    int getDepth() {
        return depth
    }

    @Override
    void addDependency(final Dependency nestedDependency) {
        dependencies.add(nestedDependency)
    }

    @Override
    List<NestedDependency> getDependencies() {
        return dependencies
    }

    void setParentDependency(final Dependency parentDependency) {
        this.parentDependency = parentDependency
    }

    @Override
    String toString() {
        return group + '.' + name
    }
}
