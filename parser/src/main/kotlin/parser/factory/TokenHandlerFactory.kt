package parser.factory

import parser.ExpressionParserV3
import parser.strategy.*
import token.TokenType


interface TokenHandlerFactory {
    fun getHandler(tokenType: TokenType, parser: ExpressionParserV3): TokenHandler
}

object DefaultTokenHandlerFactory: TokenHandlerFactory {
    override fun getHandler(tokenType: TokenType, parser: ExpressionParserV3): TokenHandler {
        return when (tokenType) {
            TokenType.IDENTIFIER -> IdentifierTokenHandler()
            TokenType.NUMBER -> NumberTokenHandler()
            TokenType.STRING -> StringTokenHandler()
            TokenType.OPENPARENTHESIS -> ParenthesisTokenHandler(parser)
            // Agrega casos para otros tipos de tokens si es necesario
            else -> throw IllegalArgumentException("Unsupported token type: $tokenType")
        }
    }
}

