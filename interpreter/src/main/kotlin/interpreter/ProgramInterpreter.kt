package interpreter

import ast.AST
import ast.FunctionStatementNode
import ast.ProgramNode
import ast.VariableStatementNode
import interpreter.readEnvFunction.ReadEnvFunction
import interpreter.readInputFunction.ReadInputFunction

@Deprecated("This class is deprecated", ReplaceWith("StatementInterpreter"))
class ProgramInterpreter : Interpreter {
    override fun interpret(
        ast: AST,
        environment: Environment,
        inputHandler: ReadInputFunction,
        envHandler: ReadEnvFunction,
    ): InterpretOutput {
        val node = getProgramNodeOrThrow(ast)

        val logs = mutableListOf<String>()

        var currentEnv: Environment = environment

        for (statement in node.statements) {
            when (statement) {
                is VariableStatementNode -> {
                    val interpretOutput = VariableStatementInterpreter().interpret(statement, currentEnv, inputHandler, envHandler)
                    logs.addAll(interpretOutput.logs)
                    currentEnv = interpretOutput.environment
                }
                is FunctionStatementNode -> {
                    val interpretOutput = FunctionStatementInterpreter().interpret(statement, currentEnv, inputHandler, envHandler)
                    logs.addAll(interpretOutput.logs)
                    currentEnv = interpretOutput.environment
                }
                else -> throw Exception(
                    "Unknown statement at (line: ${statement.getStart().line} column: ${statement.getStart().column})",
                )
            }
        }

        return InterpretOutput(environment, logs)
    }

    private fun getProgramNodeOrThrow(node: AST): ProgramNode {
        if (node is ProgramNode) return node
        throw Exception(
            "Unknown statement at (line: ${node.getStart().line} column: ${node.getStart().column})",
        )
    }
}
