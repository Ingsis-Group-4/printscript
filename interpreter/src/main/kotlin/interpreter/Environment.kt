package interpreter

import ast.VariableType

/**
 * Holds all the declared variables and their values.
 * It is immutable, so all modification operations return a new, modified Environment
 */
class Environment(
    private val variables: Map<String, EnvironmentElement> = mapOf(),
) {
    fun createVariable(
        name: String,
        value: Value,
        type: VariableType?,
        isModifiable: Boolean = true,
    ): Environment {
        if (variables.containsKey(name)) {
            throw Exception("Variable $name already exists")
        }

        if (!isOfType(value, type!!)) {
            throw Exception("Value assigned to variable $name is not a ${type.toString().lowercase()}")
        }

        return createNewEnvironment(name, EnvironmentElement(value, type, isModifiable))
    }

    fun updateVariable(
        name: String,
        value: Value,
    ): Environment {
        val oldValue =
            variables.getOrElse(name) {
                throw Exception("Variable $name does not exist")
            }
        if (!oldValue.isModifiable && oldValue.value != NullValue) {
            throw Exception("Variable $name is not modifiable")
        }

        if (!isOfType(value, oldValue.type)) {
            throw Exception("Value assigned to variable $name is not a ${oldValue.type.toString().lowercase()}")
        }

        return createNewEnvironment(name, EnvironmentElement(value, oldValue.type))
    }

    fun getVariable(name: String): Value {
        val envElement =
            variables.getOrElse(name) {
                throw Exception("Variable $name does not exist")
            }

        return envElement.value
    }

    fun getVariableType(name: String): VariableType {
        val envElement =
            variables.getOrElse(name) {
                throw Exception("Variable $name does not exist")
            }

        return envElement.type
    }

    private fun isOfType(
        value: Value,
        type: VariableType,
    ): Boolean {
        return when (value) {
            is NumberValue -> {
                type == VariableType.NUMBER
            }

            is StringValue -> {
                type == VariableType.STRING
            }
            is BooleanValue -> {
                type == VariableType.BOOLEAN
            }

            NullValue -> true
            is InputValue -> type == VariableType.INPUT
        }
    }

    private fun createNewEnvironment(
        name: String,
        environmentElement: EnvironmentElement,
    ): Environment {
        return Environment(
            variables.plus(name to environmentElement),
        )
    }

    data class EnvironmentElement(val value: Value = NullValue, val type: VariableType, val isModifiable: Boolean = true)
}
