package interpreter

import ast.AssignationNode
import ast.DeclarationNode
import ast.VariableStatementNode

class VariableStatementInterpreter(private val node: VariableStatementNode, private val environment: Environment) :
    Interpreter {
    override fun interpret(): Value {
        when (node) {
            is AssignationNode -> {
                val value = ExpressionInterpreter(node.expression, environment).interpret()
                environment.updateVariable(node.identifier.variableName, value)
                return VoidValue()
            }

            is DeclarationNode -> {
                if (node.expression != null) {
                    val value = ExpressionInterpreter(node.expression!!, environment).interpret()
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
