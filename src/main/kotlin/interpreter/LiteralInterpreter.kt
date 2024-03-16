package org.example.interpreter

import org.example.ast.LiteralNode

class LiteralInterpreter(private val node: LiteralNode<*>, val environment: Environment): Interpreter {
    override fun interpret(): Value {
        return when(node.value) {
            is Number -> NumberValue(node.value as Double)
            is String -> StringValue(node.value)
            else -> throw Exception("Unknown literal type")
        }
    }

}
