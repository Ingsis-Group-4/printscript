package interpreter

import ast.FunctionStatementNode
import ast.ProgramNode
import ast.VariableStatementNode
import logger.ConsoleLogger
import logger.Logger

class ProgramInterpreter(
    private val node: ProgramNode,
    private val logger: Logger = ConsoleLogger(),
) : Interpreter {
    private val environment: Environment = Environment()

    override fun interpret(): Value {
        for (statement in node.statements) {
            when (statement) {
                is VariableStatementNode -> VariableStatementInterpreter(statement, environment).interpret()
                is FunctionStatementNode -> FunctionStatementInterpreter(statement, environment, logger).interpret()
            }
        }
        return VoidValue()
    }
}
