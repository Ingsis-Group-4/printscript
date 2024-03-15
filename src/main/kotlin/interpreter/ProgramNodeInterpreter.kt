package org.example.interpreter

import org.example.ast.*
import org.example.org.example.interpreter.FunctionStatementInterpreter


class ProgramNodeInterpreter(private val node: ProgramNode): Interpreter {
    private val environment: Environment = Environment()
    override fun interpret(): Value {
        for (statement in node.statements) {
            when (statement) {
                is VariableStatementNode -> VariableStatementInterpreter(statement, environment).interpret()
                is FunctionStatementNode -> FunctionStatementInterpreter(statement, environment).interpret()
            }
        }
        return VoidValue()
    }


}
