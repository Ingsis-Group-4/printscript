package interpreter.expression

import ast.ReadEnvNode
import interpreter.InputValue
import interpreter.NullValue
import interpreter.Value

class ReadEnvInterpreter {
    fun interpret(node: ReadEnvNode): Value {
        return System.getenv(node.getExpression().toString())?.let { InputValue(it) } ?: NullValue
    }
}
