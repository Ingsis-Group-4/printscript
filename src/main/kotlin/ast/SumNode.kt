package org.example.ast

import org.example.common.visitor.Visitor

class SumNode(val left: ExpressionNode, val right: ExpressionNode) : ExpressionNode {
    override fun <T> accept(visitor: Visitor<T>): T {
        return visitor.visit(this)
    }
}
