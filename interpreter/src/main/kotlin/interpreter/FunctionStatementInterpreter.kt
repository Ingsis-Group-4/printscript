package interpreter

import ast.AST
import ast.FunctionStatementNode
import ast.PrintLnNode
import interpreter.expression.ExpressionInterpreter

class FunctionStatementInterpreter : Interpreter {
    override fun interpret(
        ast: AST,
        environment: Environment,
    ): InterpretOutput {
        when (val node = getFunctionNodeOrThrow(ast)) {
            is PrintLnNode -> {
                val value = ExpressionInterpreter().interpret(node.expression, environment)
                return InterpretOutput(environment, listOf(value.toString()))
            }
        }
    }

    private fun getFunctionNodeOrThrow(node: AST): FunctionStatementNode {
        if (node is FunctionStatementNode) return node
        throw Exception(
            "Unknown statement at (line: ${node.getStart().line} column: ${node.getStart().column})",
        )
    }
}
