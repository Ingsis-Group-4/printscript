package parser

import ast.AST
import ast.DivisionNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.isOutOfBounds
import parser.utils.nextIndex
import token.Token
import token.TokenType

class ExpressionParser : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return parsePrimaryExpression(tokens, currentIndex)
    }

    private fun parsePrimaryExpression(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val leftResult = parseTerm(tokens, currentIndex)
        var left = leftResult.value

        var lastIndex = leftResult.lastValidatedIndex
        var currentOperatorIndex = nextIndex(lastIndex)

        while (!isOutOfBounds(tokens, currentOperatorIndex) && isPrimaryOperator(tokens, currentOperatorIndex)) {
            val operator = at(tokens, currentOperatorIndex)
            val rightResult = parseTerm(tokens, nextIndex(currentOperatorIndex))

            left = buildOperationNode(left, rightResult.value, operator)
            lastIndex = rightResult.lastValidatedIndex
            currentOperatorIndex = nextIndex(lastIndex)
        }

        return SuccessResult(
            left,
            lastIndex,
        )
    }

    private fun parseTerm(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val leftResult = parserFactor(tokens, currentIndex)
        var left = leftResult.value
        var lastIndex = leftResult.lastValidatedIndex

        var currentOperatorIndex = nextIndex(lastIndex)

        while (!isOutOfBounds(tokens, currentOperatorIndex) && isTermOperator(tokens, currentOperatorIndex)) {
            val operator = at(tokens, currentOperatorIndex)
            val rightResult = parserFactor(tokens, nextIndex(currentOperatorIndex))

            left = buildOperationNode(left, rightResult.value, operator)
            lastIndex = rightResult.lastValidatedIndex
            currentOperatorIndex = nextIndex(lastIndex)
        }

        return SuccessResult(
            left,
            lastIndex,
        )
    }

    private fun parserFactor(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val currentToken = at(tokens, currentIndex)

        return when (currentToken.type) {
            TokenType.IDENTIFIER -> {
                SuccessResult(
                    IdentifierNode(
                        currentToken.value,
                        start = currentToken.start,
                        end = currentToken.end,
                    ),
                    currentIndex,
                )
            }

            TokenType.NUMBER -> {
                SuccessResult(
                    LiteralNode(
                        currentToken.value.toDouble(),
                        start = currentToken.start,
                        end = currentToken.end,
                    ),
                    currentIndex,
                )
            }

            TokenType.STRING -> {
                SuccessResult(
                    LiteralNode(
                        currentToken.value,
                        start = currentToken.start,
                        end = currentToken.end,
                    ),
                    currentIndex,
                )
            }

            TokenType.OPENPARENTHESIS -> {
                val expressionResult = parsePrimaryExpression(tokens, nextIndex(currentIndex))
                val expression = expressionResult.value

                val rightParen = at(tokens, nextIndex(expressionResult.lastValidatedIndex))
                if (rightParen.type != TokenType.CLOSEPARENTHESIS) {
                    throw Exception("Expected a right parenthesis")
                }

                SuccessResult(
                    expression,
                    nextIndex(expressionResult.lastValidatedIndex),
                )
            }

            else -> throw Exception("Unexpected token: ${currentToken.value}")
        }
    }

    private fun isPrimaryOperator(
        tokens: List<Token>,
        operatorIndex: Int,
    ): Boolean {
        return when (at(tokens, operatorIndex).type) {
            TokenType.SUM,
            TokenType.SUBTRACTION,
            -> true

            else -> false
        }
    }

    private fun isTermOperator(
        tokens: List<Token>,
        operatorIndex: Int,
    ): Boolean {
        return when (at(tokens, operatorIndex).type) {
            TokenType.MULTIPLICATION,
            TokenType.DIVISION,
            -> true

            else -> false
        }
    }

    private fun buildOperationNode(
        left: AST,
        right: AST,
        operator: Token,
    ): AST {
        when (operator.type) {
            TokenType.SUM -> return SumNode(
                left as ExpressionNode,
                right as ExpressionNode,
                OperatorNode(operator.start, operator.end, OperatorType.SUM),
                left.getStart(),
                right.getEnd(),
            )
            TokenType.SUBTRACTION -> return SubtractionNode(
                left as ExpressionNode,
                right as ExpressionNode,
                OperatorNode(operator.start, operator.end, OperatorType.SUBTRACT),
                left.getStart(),
                right.getEnd(),
            )
            TokenType.MULTIPLICATION -> return ProductNode(
                left as ExpressionNode,
                right as ExpressionNode,
                OperatorNode(operator.start, operator.end, OperatorType.MULTIPLICATION),
                left.getStart(),
                right.getEnd(),
            )
            TokenType.DIVISION -> return DivisionNode(
                left as ExpressionNode,
                right as ExpressionNode,
                OperatorNode(operator.start, operator.end, OperatorType.DIVISION),
                left.getStart(),
                right.getEnd(),
            )
            else -> throw Exception("Unexpected operator: ${operator.value}")
        }
    }
}
