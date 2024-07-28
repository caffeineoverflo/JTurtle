package com.tonikrug.turtle;

import java.util.List;
interface TurtleCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
