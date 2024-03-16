package org.example.interpreter

import ast.FunctionStatementNode
import ast.ProgramNode
import ast.VariableStatementNode


class ProgramInterpreter(private val node: ProgramNode) : Interpreter {
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
