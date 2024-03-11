package org.example.interpreter

import org.example.ast.VariableType

class Environment {
    private val variables = HashMap<String, EnvironmentElement>()

    fun createVariable(name: String, value: Any, type: VariableType?) {
        if (variables.containsKey(name)) {
            throw Exception("Variable $name already exists")
        }
        variables[name] = EnvironmentElement(value, type!!)
    }

    fun updateVariable(name: String, value: Any) {
        if (!variables.containsKey(name)) {
            throw Exception("Variable $name does not exist")
        }
        variables[name]?.updateValue(value)
    }

    fun getVariable(name: String): Any {
        if (!variables.containsKey(name)) {
            throw Exception("Variable $name does not exist")
        }
        return variables[name]?.value ?: throw Exception("Variable $name is not initialized")
    }

    data class EnvironmentElement(val value: Any? = null, val type: VariableType) {
        fun updateValue(value: Any) {
            EnvironmentElement(value, type)
        }
    }


}
