package parser.strategy

import ast.AST
import ast.DivisionNode
import ast.ExpressionNode
import ast.OperatorNode
import ast.OperatorType
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import token.Token

interface OperationStrategy {
    fun execute(
        left: AST,
        right: AST,
        operator: Token,
    ): AST
}

class SumOperationStrategy : OperationStrategy {
    override fun execute(
        left: AST,
        right: AST,
        operator: Token,
    ): AST {
        return SumNode(
            left as ExpressionNode,
            right as ExpressionNode,
            OperatorNode(operator.start, operator.end, OperatorType.SUM),
            left.getStart(),
            right.getEnd(),
        )
    }
}

class SubtractionOperationStrategy : OperationStrategy {
    override fun execute(
        left: AST,
        right: AST,
        operator: Token,
    ): AST {
        return SubtractionNode(
            left as ExpressionNode,
            right as ExpressionNode,
            OperatorNode(operator.start, operator.end, OperatorType.SUB),
            left.getStart(),
            right.getEnd(),
        )
    }
}

class MultiplicationOperationStrategy : OperationStrategy {
    override fun execute(
        left: AST,
        right: AST,
        operator: Token,
    ): AST {
        return ProductNode(
            left as ExpressionNode,
            right as ExpressionNode,
            OperatorNode(operator.start, operator.end, OperatorType.MUL),
            left.getStart(),
            right.getEnd(),
        )
    }
}

class DivisionOperationStrategy : OperationStrategy {
    override fun execute(
        left: AST,
        right: AST,
        operator: Token,
    ): AST {
        return DivisionNode(
            left as ExpressionNode,
            right as ExpressionNode,
            OperatorNode(operator.start, operator.end, OperatorType.DIV),
            left.getStart(),
            right.getEnd(),
        )
    }
}

// Implementa las estrategias para multiplicación y división de manera similar
