package org.example.org.example.interpreter

import org.example.ast.FunctionStatementNode
import org.example.ast.PrintLnNode
import org.example.interpreter.Environment
import org.example.interpreter.Interpreter
import org.example.interpreter.Value
import org.example.org.example.org.example.interpreter.ExpressionInterpreter

class FunctionStatementInterpreter(val node: FunctionStatementNode,val environment: Environment): Interpreter {
    override fun interpret(): Value {
        when (node) {
            is PrintLnNode -> {
                val value = ExpressionInterpreter(node.expression, environment).interpret()
                println(value)
            }
        }
    }
}
