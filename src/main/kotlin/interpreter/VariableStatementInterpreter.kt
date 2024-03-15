package org.example.interpreter

import org.example.ast.VariableStatementNode
import org.example.interpreter.Environment
import org.example.interpreter.Interpreter
import org.example.interpreter.Value

class VariableStatementInterpreter(val node: VariableStatementNode,val environment: Environment) : Interpreter {
    override fun interpret(): Value {
        TODO("Not yet implemented")
    }

}
