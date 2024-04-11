package parser.strategy

import ast.*
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
        return BinaryOperation(
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
        return BinaryOperation(
            left as ExpressionNode,
            right as ExpressionNode,
            OperatorNode(operator.start, operator.end, OperatorType.SUBTRACT),
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
        return BinaryOperation(
            left as ExpressionNode,
            right as ExpressionNode,
            OperatorNode(operator.start, operator.end, OperatorType.MULTIPLICATION),
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
        return BinaryOperation(
            left as ExpressionNode,
            right as ExpressionNode,
            OperatorNode(operator.start, operator.end, OperatorType.DIVISION),
            left.getStart(),
            right.getEnd(),
        )
    }
}

// Implementa las estrategias para multiplicación y división de manera similar
