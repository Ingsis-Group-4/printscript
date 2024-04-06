package interpreter

import ast.AST
import ast.AssignationNode
import ast.VariableDeclarationNode
import ast.VariableStatementNode
import interpreter.expression.ExpressionInterpreter

class VariableStatementInterpreter :
    Interpreter {
    override fun interpret(
        ast: AST,
        environment: Environment,
    ): InterpretOutput {
        when (val node = getVariableStatementNodeOrThrow(ast)) {
            is AssignationNode -> {
                val value = ExpressionInterpreter().interpret(node.expression, environment)
                environment.updateVariable(node.identifier.variableName, value)
                return InterpretOutput(environment, listOf())
            }

            is VariableDeclarationNode -> {
                if (node.expression != null) {
                    val value = ExpressionInterpreter().interpret(node.expression!!, environment)
                    environment.createVariable(
                        node.identifier.variableName,
                        value,
                        node.identifier.variableType,
                    )
                    return InterpretOutput(environment, listOf())
                } else {
                    environment.createVariable(
                        node.identifier.variableName,
                        NullValue(),
                        node.identifier.variableType,
                    )
                    return InterpretOutput(environment, listOf())
                }
            }
        }
    }

    private fun getVariableStatementNodeOrThrow(node: AST): VariableStatementNode {
        if (node is VariableStatementNode) return node
        throw Exception(
            "Unknown statement at (line: ${node.getStart().line} column: ${node.getStart().column})",
        )
    }
}
