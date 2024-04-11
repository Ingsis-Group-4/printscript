package parser.factory

import parser.ExpressionParser
import parser.strategy.IdentifierTokenHandler
import parser.strategy.NumberTokenHandler
import parser.strategy.ParenthesisTokenHandler
import parser.strategy.StringTokenHandler
import parser.strategy.TokenHandler
import token.TokenType

interface TokenHandlerFactory {
    fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): TokenHandler
}

object DefaultTokenHandlerFactory : TokenHandlerFactory {
    override fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): TokenHandler {
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
