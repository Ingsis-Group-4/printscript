package interpreter

import ast.AST
import ast.FunctionStatementNode
import ast.ProgramNode
import ast.VariableStatementNode

class ProgramInterpreter : Interpreter {
    override fun interpret(
        ast: AST,
        environment: Environment,
    ): InterpretOutput {
        val node = getProgramNodeOrThrow(ast)

        val logs = mutableListOf<String>()

        var currentEnv: Environment = environment

        for (statement in node.statements) {
            when (statement) {
                is VariableStatementNode -> {
                    val interpretOutput = VariableStatementInterpreter().interpret(statement, currentEnv)
                    logs.addAll(interpretOutput.logs)
                    currentEnv = interpretOutput.environment
                }
                is FunctionStatementNode -> {
                    val interpretOutput = FunctionStatementInterpreter().interpret(statement, currentEnv)
                    logs.addAll(interpretOutput.logs)
                    currentEnv = interpretOutput.environment
                }
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
