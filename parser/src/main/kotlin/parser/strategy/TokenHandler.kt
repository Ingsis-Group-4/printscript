package parser.strategy

import ast.AST
import ast.IdentifierNode
import ast.LiteralNode
import parser.ExpressionParserV3
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.isTokenValid
import parser.utils.nextIndex
import token.Token
import token.TokenType

interface TokenHandler {
    fun handleToken(tokens: List<Token>, currentIndex: Int): AST
}

class IdentifierTokenHandler : TokenHandler {
    override fun handleToken(tokens: List<Token>, currentIndex: Int): AST {
        val currentToken = at(tokens, currentIndex)
        return IdentifierNode(
            currentToken.value,
            start = currentToken.start,
            end = currentToken.end
        )
    }
}

class NumberTokenHandler : TokenHandler {
    override fun handleToken(tokens: List<Token>, currentIndex: Int): AST {
        val currentToken = at(tokens, currentIndex)
        return LiteralNode(
            currentToken.value.toDouble(),
            start = currentToken.start,
            end = currentToken.end
        )
    }
}

class StringTokenHandler : TokenHandler {
    override fun handleToken(tokens: List<Token>, currentIndex: Int): AST {
        val currentToken = at(tokens, currentIndex)
        return LiteralNode(
            currentToken.value,
            start = currentToken.start,
            end = currentToken.end
        )
    }
}

class ParenthesisTokenHandler(private val parser: ExpressionParserV3) : TokenHandler {
    override fun handleToken(tokens: List<Token>, currentIndex: Int): AST {
        val currentToken = at(tokens, currentIndex)
        val expressionResult = parser.parse(tokens, nextIndex(currentIndex))
        val expression = if (expressionResult is SuccessResult) {
            expressionResult.value
        } else {
            throw Exception("Invalid expression")
        }

        val rightParenIndex = nextIndex(expressionResult.lastValidatedIndex)
        if (!isTokenValid(tokens, rightParenIndex, TokenType.CLOSEPARENTHESIS)) {
            throw Exception("Expected a right parenthesis")
        }

        return expression
    }
}


// Agrega otras clases de manejo para diferentes tipos de tokens si es necesario
