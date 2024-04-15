package interpreter

import ast.AST
import ast.AssignationNode
import ast.ConstNode
import ast.DeclarationNode
import ast.VariableStatementNode
import ast.VariableType
import interpreter.expression.ExpressionInterpreter
import interpreter.readInputFunction.ReadInputFunction

class VariableStatementInterpreter :
    Interpreter {
    override fun interpret(
        ast: AST,
        environment: Environment,
        inputHandler: ReadInputFunction,
    ): InterpretOutput {
        when (val node = getVariableStatementNodeOrThrow(ast)) {
            is AssignationNode -> {
                val expressionOutput = ExpressionInterpreter().interpret(node.expression, environment, inputHandler)
                var value = expressionOutput.value
                if (value is InputValue) {
                    value = castVariableTypeAssignationNode(node, environment, value)
                }
                val updatedEnv = environment.updateVariable(node.identifier.variableName, value)
                return InterpretOutput(updatedEnv, expressionOutput.logs)
            }

            is DeclarationNode -> {
                if (node.expression != null) {
                    val expressionOutput = ExpressionInterpreter().interpret(node.expression!!, environment, inputHandler)
                    var value = expressionOutput.value
                    if (value is InputValue) {
                        value = castVariableTypeDeclarationNode(node, value)
                    }
                    val isModifiable = node.keywordNode !is ConstNode
                    val updatedEnv =
                        environment.createVariable(
                            node.identifier.variableName,
                            value,
                            node.identifier.variableType,
                            isModifiable,
                        )
                    return InterpretOutput(updatedEnv, expressionOutput.logs)
                } else {
                    val updatedEnv =
                        environment.createVariable(
                            node.identifier.variableName,
                            NullValue,
                            node.identifier.variableType,
                        )
                    return InterpretOutput(updatedEnv, listOf())
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

    private fun castVariableTypeAssignationNode(
        node: AssignationNode,
        environment: Environment,
        value: InputValue,
    ): Value {
        val variableType = environment.getVariableType(node.identifier.variableName)
        val castedValue = castInputVariableToVariableType(value, variableType)
        return castedValue
    }

    private fun castVariableTypeDeclarationNode(
        node: DeclarationNode,
        value: InputValue,
    ): Value {
        val variableType = node.identifier.variableType
        val castedValue = castInputVariableToVariableType(value, variableType)
        return castedValue
    }

    private fun castInputVariableToVariableType(
        value: InputValue,
        variableType: VariableType?,
    ): Value {
        return when (variableType) {
            VariableType.STRING -> StringValue(value.value)
            VariableType.NUMBER ->
                value.value.toDoubleOrNull()?.let { NumberValue(it) }
                    ?: throw Exception("Cannot cast input value to number")

            VariableType.BOOLEAN ->
                value.value.toBooleanStrictOrNull()?.let { BooleanValue(it) }
                    ?: throw Exception("Cannot cast input value to boolean")

            else -> throw Exception("Cannot cast input value to ${variableType.toString().lowercase()}")
        }
    }
}
