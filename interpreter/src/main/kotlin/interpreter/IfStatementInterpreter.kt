package interpreter
import ast.AST
import ast.FunctionStatementNode
import ast.IfStatement
import ast.StatementNode
import ast.VariableStatementNode
import interpreter.expression.ExpressionInterpreter
import interpreter.readEnvFunction.ReadEnvFunction
import interpreter.readInputFunction.ReadInputFunction

class IfStatementInterpreter : Interpreter {
    override fun interpret(
        ast: AST,
        environment: Environment,
        inputHandler: ReadInputFunction,
        envHandler: ReadEnvFunction,
    ): InterpretOutput {
        val node = getIfNodeOrThrow(ast)

        val condition = ExpressionInterpreter().interpret(node.getCondition(), environment, inputHandler, envHandler).value

        if (condition !is BooleanValue) {
            throw Exception("Condition must be a boolean value")
        }

        val statements: MutableList<StatementNode> =
            if (condition.value) {
                node.getThenBlock().getStatements().toMutableList()
            } else {
                node.getElseBlock()?.getStatements()?.toMutableList() ?: mutableListOf()
            }
        var updatedEnvironment = environment
        val updatedLogs = mutableListOf<String>()
        for (statement in statements) {
            val interpretResult =
                when (statement) {
                    is VariableStatementNode -> VariableStatementInterpreter().interpret(statement, environment, inputHandler, envHandler)
                    is FunctionStatementNode -> FunctionStatementInterpreter().interpret(statement, environment, inputHandler, envHandler)
                    is IfStatement -> interpret(statement, environment, inputHandler, envHandler)
                    else -> throw Exception(
                        "Unknown statement at (line: ${statement.getStart().line} column: ${statement.getStart().column})",
                    )
                }
            updatedEnvironment = interpretResult.environment
            updatedLogs.addAll(interpretResult.logs)
        }
        return InterpretOutput(updatedEnvironment, updatedLogs)
    }

    private fun getIfNodeOrThrow(node: AST): IfStatement {
        if (node is IfStatement) return node
        throw Exception(
            "Unknown statement at (line: ${node.getStart().line} column: ${node.getStart().column})",
        )
    }
}
