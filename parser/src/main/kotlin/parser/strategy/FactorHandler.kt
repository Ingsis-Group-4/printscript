package parser.strategy

import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.UnaryOperation
import parser.ExpressionParser
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.convertStringToNumber
import parser.utils.isTokenValid
import parser.utils.nextIndex
import token.Token
import token.TokenType

interface FactorHandler {
    fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult
}

class IdentifierHandler : FactorHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val currentToken = at(tokens, currentIndex)
        return SuccessResult(
            IdentifierNode(
                currentToken.value,
                start = currentToken.start,
                end = currentToken.end,
            ),
            currentIndex,
        )
    }
}

class NumberHandler : FactorHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val currentToken = at(tokens, currentIndex)
        return SuccessResult(
            LiteralNode(
                convertStringToNumber(currentToken.value),
                start = currentToken.start,
                end = currentToken.end,
            ),
            currentIndex,
        )
    }
}

class StringHandler : FactorHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val currentToken = at(tokens, currentIndex)
        return SuccessResult(
            LiteralNode(
                currentToken.value,
                start = currentToken.start,
                end = currentToken.end,
            ),
            currentIndex,
        )
    }
}

class ParenthesisHandler(private val parser: ExpressionParser) : FactorHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val expressionResult = parser.parse(tokens, nextIndex(currentIndex))
        val expression =
            if (expressionResult is SuccessResult) {
                expressionResult.value
            } else {
                throw Exception("Invalid expression")
            }

        val rightParenIndex = nextIndex(expressionResult.lastValidatedIndex)
        if (!isTokenValid(tokens, rightParenIndex, TokenType.CLOSEPARENTHESIS)) {
            throw Exception("Expected a right parenthesis")
        }

        return SuccessResult(expression, rightParenIndex)
    }
}

class NegativeHandler(private val parser: ExpressionParser) : FactorHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val expressionIndex = nextIndex(currentIndex)
        val expressionResult = parser.parse(tokens, expressionIndex)
        val expression =
            if (expressionResult is SuccessResult) {
                expressionResult.value as ExpressionNode
            } else {
                throw Exception("Invalid expression")
            }

        return SuccessResult(
            UnaryOperation(
                expression,
                OperatorNode(
                    type = OperatorType.NEGATION,
                    start = at(tokens, currentIndex).start,
                    end = at(tokens, currentIndex).end,
                ),
                start = at(tokens, currentIndex).start,
                end = at(tokens, expressionResult.lastValidatedIndex).end,
            ),
            expressionResult.lastValidatedIndex,
        )
    }
}
