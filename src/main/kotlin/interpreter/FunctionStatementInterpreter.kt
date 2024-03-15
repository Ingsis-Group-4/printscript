package org.example.interpreter

import org.example.ast.FunctionStatementNode
import org.example.ast.PrintLnNode

class FunctionStatementInterpreter(val node: FunctionStatementNode,val environment: Environment): Interpreter {
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
