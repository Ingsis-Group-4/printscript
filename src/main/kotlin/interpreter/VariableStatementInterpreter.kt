package org.example.interpreter

import org.example.ast.VariableStatementNode

class VariableStatementInterpreter(val node: VariableStatementNode,val  environment: Environment): Interpreter {
    override fun interpret(): Value {
//        TODO:
    }
}
