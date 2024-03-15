package org.example.interpreter

import org.example.ast.VariableType

class Environment {
    private val variables = HashMap<String, EnvironmentElement>()

    fun createVariable(name: String, value: Value, type: VariableType?) {
        if (variables.containsKey(name)) {
            throw Exception("Variable $name already exists")
        }
        variables[name] = EnvironmentElement(value, type!!)
    }

    fun updateVariable(name: String, value: Value) {
        if (!variables.containsKey(name)) {
            throw Exception("Variable $name does not exist")
        }
        val oldValue = variables[name]
        when (value) {
            is NumberValue -> {
                if (oldValue?.type != VariableType.NUMBER) {
                    throw Exception("Variable $name is not a number")
                }
            }
            is StringValue -> {
                if (oldValue?.type != VariableType.STRING) {
                    throw Exception("Variable $name is not a string")
                }
            }
        }
        variables[name] = EnvironmentElement(value, oldValue?.type!!)
    }

    fun getVariable(name: String): Value {
        if (!variables.containsKey(name)) {
            throw Exception("Variable $name does not exist")
        }
        return variables[name]?.value ?: throw Exception("Variable $name is not initialized")
    }

    data class EnvironmentElement(val value: Value = NullValue(), val type: VariableType)
}

