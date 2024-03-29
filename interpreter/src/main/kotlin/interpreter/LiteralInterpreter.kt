package interpreter

import ast.LiteralNode

class LiteralInterpreter(private val node: LiteralNode<*>, val environment: Environment) : Interpreter {
    override fun interpret(): Value {
        return when (node.value) {
            is Number -> NumberValue(node.value as Double)
            is String -> StringValue(node.value as String)
            else -> throw Exception("Unknown literal type")
        }
    }
}
