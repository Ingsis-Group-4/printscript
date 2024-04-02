package ast

interface OperationNode : ExpressionNode {
    fun getOperator(): OperatorNode

    fun getLeft(): ExpressionNode

    fun getRight(): ExpressionNode
}
