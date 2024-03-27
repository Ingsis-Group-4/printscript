package interpreter

import ast.FunctionStatementNode
import ast.PrintLnNode

class FunctionStatementInterpreter(private val node: FunctionStatementNode, private val environment: Environment) :
    Interpreter {
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
