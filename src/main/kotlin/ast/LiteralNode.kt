package org.example.ast

import org.example.interpreter.Visitor

class LiteralNode<T>(val value: T) : ExpressionNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

