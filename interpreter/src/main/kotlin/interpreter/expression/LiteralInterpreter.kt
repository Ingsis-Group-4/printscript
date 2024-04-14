package interpreter.expression

import ast.LiteralNode
import interpreter.NumberValue
import interpreter.StringValue
import interpreter.Value

class LiteralInterpreter {
    fun interpret(node: LiteralNode<*>): Value {
        return when (node.value) {
            is Number -> NumberValue(node.value as Number)
            is String -> StringValue(node.value as String)
            else -> throw Exception("Unknown literal type")
        }
    }
}
