package com.tribe3k.alluro.service

import com.tribe3k.alluro.models.Dependency
import com.tribe3k.alluro.models.NestedDependency


class LevelParser {
    static void parseLevel(final Dependency currentParent,
                           final Stack<NestedDependency> dependencyStack) {
        NestedDependency previousDep = dependencyStack.peek();

        while (!dependencyStack.empty() && dependencyStack.peek().getDepth() > currentParent.getDepth()) {
            NestedDependency currentDep = dependencyStack.pop();

            if (currentParent.getDepth() + 1 == currentDep.getDepth()) {
                currentDep.setParentDependency(currentParent);
                currentParent.addDependency(currentDep);
                previousDep = currentDep;
            } else {
                dependencyStack.push(currentDep);
                parseLevel(previousDep, dependencyStack);
                // don't change previous dependency, as it is still the previous on this level
            }
        }
    }
}
