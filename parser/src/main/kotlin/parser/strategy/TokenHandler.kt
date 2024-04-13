package parser.strategy

import ast.IdentifierNode
import ast.LiteralNode
import ast.ReadInputNode
import parser.ExpressionParser
import parser.exception.ParserException
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.consume
import parser.utils.isTokenValid
import parser.utils.nextIndex
import token.Token
import token.TokenType

interface TokenHandler {
    fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult
}

class IdentifierTokenHandler : TokenHandler {
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

class NumberTokenHandler : TokenHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val currentToken = at(tokens, currentIndex)
        return SuccessResult(
            LiteralNode(
                currentToken.value.toDouble(),
                start = currentToken.start,
                end = currentToken.end,
            ),
            currentIndex,
        )
    }
}

class StringTokenHandler : TokenHandler {
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

class BooleanTokenHandler : TokenHandler {
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

class ParenthesisTokenHandler(private val parser: ExpressionParser) : TokenHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val currentToken = at(tokens, currentIndex)
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

class ReadInputHandler(
    private val parser: ExpressionParser,
) : TokenHandler {
    override fun handleToken(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val readInputIndex = currentIndex
        val openParenIndex = consume(tokens, readInputIndex, TokenType.READINPUT)
        val stringIndex = consume(tokens, openParenIndex, TokenType.OPENPARENTHESIS)
        val closeParenIndex = consume(tokens, stringIndex, TokenType.STRING)

        val parsedString = parser.parse(tokens, stringIndex) as SuccessResult

        val readInputToken = at(tokens, readInputIndex)
        val closeParenToken = at(tokens, closeParenIndex)

        when (val value = parsedString.value) {
            is LiteralNode<*> ->
                return SuccessResult(
                    ReadInputNode(
                        readInputToken.start,
                        closeParenToken.end,
                        value,
                    ),
                    closeParenIndex,
                )
            else -> throw ParserException("Expected String literal at position ${value.getStart()}")
        }
    }
}
