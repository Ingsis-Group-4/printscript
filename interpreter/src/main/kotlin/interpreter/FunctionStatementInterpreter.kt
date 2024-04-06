package interpreter

import ast.FunctionStatementNode
import ast.PrintLnNode
import interpreter.expression.ExpressionInterpreter
import logger.Logger

class FunctionStatementInterpreter(
    private val node: FunctionStatementNode,
    private val environment: Environment,
    private val logger: Logger,
) : Interpreter {
    override fun interpret(): Value {
        when (node) {
            is PrintLnNode -> {
                val value = ExpressionInterpreter().interpret(node.expression, environment)
                logger.log(value.toString())
            }
        }
        return VoidValue()
    }
}
