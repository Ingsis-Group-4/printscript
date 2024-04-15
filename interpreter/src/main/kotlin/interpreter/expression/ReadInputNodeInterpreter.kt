package interpreter.expression

import ast.ReadInputNode
import interpreter.Environment
import interpreter.InputValue
import interpreter.NullValue
import interpreter.Value
import interpreter.readInputFunction.ReadInputFunction

class ReadInputNodeInterpreter {
    fun interpret(
        readInputNode: ReadInputNode,
        environment: Environment,
        readInputFunction: ReadInputFunction,
    ): Value {
        readInputFunction.read(readInputNode.getExpression().toString()).let {
            return when (it) {
                is String -> InputValue(it)
                else -> NullValue
            }
        }
    }
}