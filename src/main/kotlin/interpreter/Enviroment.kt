package org.example.interpreter

import org.example.ast.VariableType

class Environment {
    private val variables = HashMap<String, EnvironmentElement>()

    fun createVariable(name: String, value: EnvironmentValue, type: VariableType?) {
        if (variables.containsKey(name)) {
            throw Exception("Variable $name already exists")
        }
        variables[name] = EnvironmentElement(value, type!!)
    }

    fun updateVariable(name: String, value: EnvironmentValue) {
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

    fun getVariable(name: String): EnvironmentValue {
        if (!variables.containsKey(name)) {
            throw Exception("Variable $name does not exist")
        }
        return variables[name]?.value ?: throw Exception("Variable $name is not initialized")
    }

    data class EnvironmentElement(val value: EnvironmentValue = NullValue(), val type: VariableType) {
        fun updateValue(value: EnvironmentValue) {
            EnvironmentElement(value, type)
        }
    }
}

interface EnvironmentValue
class NumberValue(val value: Double) : EnvironmentValue
class StringValue(val value: String) : EnvironmentValue
class NullValue : EnvironmentValue
