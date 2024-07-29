package com.tonikrug.turtle;

import java.util.List;
import java.util.Map;
class TurtleClass implements TurtleCallable {
    final String name;
    TurtleClass(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object call(Interpreter interpreter,
                       List<Object> arguments) {
        TurtleInstance instance = new TurtleInstance(this);
        return instance;
    }

    @Override
    public int arity() {
        return 0;
    }

}
