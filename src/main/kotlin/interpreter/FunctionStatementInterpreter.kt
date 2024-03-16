package org.example.interpreter

import org.example.ast.FunctionStatementNode
import org.example.ast.PrintLnNode

class FunctionStatementInterpreter(private val node: FunctionStatementNode, private val environment: Environment): Interpreter {
    override fun interpret(): Value {
        when (node) {
            is PrintLnNode -> {
                val value = ExpressionInterpreter(node.expression, environment).interpret()
                println(value)
            }
        }
        return VoidValue()
    }

}
