package interpreter

import ast.AssignationNode
import ast.VariableDeclarationNode
import ast.VariableStatementNode
import interpreter.expression.ExpressionInterpreter

class VariableStatementInterpreter(private val node: VariableStatementNode, private val environment: Environment) :
    Interpreter {
    override fun interpret(): Value {
        when (node) {
            is AssignationNode -> {
                val value = ExpressionInterpreter().interpret(node.expression, environment)
                environment.updateVariable(node.identifier.variableName, value)
                return VoidValue()
            }

            is VariableDeclarationNode -> {
                if (node.expression != null) {
                    val value = ExpressionInterpreter().interpret(node.expression!!, environment)
                    environment.createVariable(
                        node.identifier.variableName,
                        value,
                        node.identifier.variableType,
                    )
                    return VoidValue()
                } else {
                    environment.createVariable(
                        node.identifier.variableName,
                        NullValue(),
                        node.identifier.variableType,
                    )
                    return VoidValue()
                }
            }
        }
    }
}
