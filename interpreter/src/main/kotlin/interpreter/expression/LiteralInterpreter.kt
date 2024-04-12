package interpreter.expression

import ast.LiteralNode
import interpreter.BooleanValue
import interpreter.NumberValue
import interpreter.StringValue
import interpreter.Value

class LiteralInterpreter {
    fun interpret(node: LiteralNode<*>): Value {
        return when (node.value) {
            is Number -> NumberValue(node.value as Double)
            is String -> StringValue(node.value as String)
            is Boolean -> BooleanValue(node.value as Boolean)
            else -> throw Exception("Unknown literal type")
        }
    }
}
