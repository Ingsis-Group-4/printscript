package org.example.interpreter

import org.example.ast.AssignationNode
import org.example.ast.VariableDeclarationNode
import org.example.ast.VariableStatementNode
import org.example.interpreter.Environment
import org.example.interpreter.Interpreter
import org.example.interpreter.Value

class VariableStatementInterpreter(private val node: VariableStatementNode, private val environment: Environment) :
    Interpreter {
    override fun interpret(): Value {
        when (node) {
            is AssignationNode -> {
                val value = ExpressionInterpreter(node.expression, environment).interpret()
                environment.updateVariable(node.identifier.variableName, value)
                return VoidValue()
            }

            is VariableDeclarationNode -> {
                if (node.expression != null) {
                    val value = ExpressionInterpreter(node.expression, environment).interpret()
                    environment.createVariable(node.identifier.variableName, value, node.identifier.variableType)
                    return VoidValue()
                } else {
                    environment.createVariable(node.identifier.variableName, NullValue(), node.identifier.variableType)
                    return VoidValue()
                }
            }
        }
    }
}
