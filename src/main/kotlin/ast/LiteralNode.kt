package org.example.ast

import org.example.common.visitor.Visitor

interface LiteralNode : ExpressionNode

class StringNode(val value: String) : LiteralNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

class NumberNode(val value: Double) : LiteralNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}

