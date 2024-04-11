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
        val node = getFunctionNodeOrThrow(ast)
        when (val function = node.getFunctionNode()) {
            is PrintLnNode -> {
                val value = ExpressionInterpreter().interpret(function.getExpression(), environment)
                return InterpretOutput(environment, listOf(value.toString()))
            }
            else -> throw Exception("Unknown function at (line: ${node.getStart().line} column: ${node.getStart().column})")
        }
    }

    private fun getFunctionNodeOrThrow(node: AST): FunctionStatementNode {
        if (node is FunctionStatementNode) return node
        throw Exception(
            "Unknown statement at (line: ${node.getStart().line} column: ${node.getStart().column})",
        )
    }
}
