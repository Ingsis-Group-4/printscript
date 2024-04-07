package interpreter

import ast.FunctionStatementNode
import ast.PrintLnNode
import logger.Logger

class FunctionStatementInterpreter(
    private val node: FunctionStatementNode,
    private val environment: Environment,
    private val logger: Logger,
) : Interpreter {
    override fun interpret(): Value {
        when (val functionNode = node.getFunctionNode()) {
            is PrintLnNode -> {
                val value = ExpressionInterpreter(functionNode.getExpression(), environment).interpret()
                logger.log(value.toString())
            }
        }
        return VoidValue()
    }
}
