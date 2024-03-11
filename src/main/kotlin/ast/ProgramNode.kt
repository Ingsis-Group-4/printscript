package org.example.ast

import org.example.interpreter.Visitor

data class ProgramNode(val statements: List<StatementNode>) : AST {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }

}