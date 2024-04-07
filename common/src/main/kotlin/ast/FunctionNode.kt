package ast

sealed interface FunctionNode : AST {
    fun getExpression(): ExpressionNode
}
