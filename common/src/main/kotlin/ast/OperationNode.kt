package ast

interface OperationNode : ExpressionNode {
    fun getOperator(): OperatorNode
}
