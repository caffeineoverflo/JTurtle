package com.tonikrug.turtle;

import java.util.List;
import java.util.Map;
class TurtleClass implements TurtleCallable {
    final TurtleClass superclass;
    final String name;
    private final Map<String, TurtleFunction> methods;
    TurtleClass(String name, TurtleClass superclass, Map<String, TurtleFunction> methods) {
        this.superclass = superclass;
        this.name = name;
        this.methods = methods;
    }

    TurtleFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }
        if (superclass != null) {
            return superclass.findMethod(name);
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter,
                       List<Object> arguments) {
        TurtleInstance instance = new TurtleInstance(this);
        TurtleFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }

    @Override
    public int arity() {
        TurtleFunction initializer = findMethod("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }

}
