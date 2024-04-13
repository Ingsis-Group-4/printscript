package parser.factory

import parser.ExpressionParser
import parser.strategy.BooleanTokenHandler
import parser.strategy.IdentifierTokenHandler
import parser.strategy.NumberTokenHandler
import parser.strategy.ParenthesisTokenHandler
import parser.strategy.ReadInputHandler
import parser.strategy.StringTokenHandler
import parser.strategy.TokenHandler
import token.TokenType

interface TokenHandlerFactory {
    fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): TokenHandler
}

object TokenHandlerFactoryV1 : TokenHandlerFactory {
    override fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): TokenHandler {
        return when (tokenType) {
            TokenType.IDENTIFIER -> IdentifierTokenHandler()
            TokenType.NUMBER -> NumberTokenHandler()
            TokenType.STRING -> StringTokenHandler()
            TokenType.OPENPARENTHESIS -> ParenthesisTokenHandler(parser)
            else -> throw IllegalArgumentException("Unsupported token type: $tokenType")
        }
    }
}

object TokenHandlerFactoryV2 : TokenHandlerFactory {
    override fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): TokenHandler {
        return when (tokenType) {
            TokenType.IDENTIFIER -> IdentifierTokenHandler()
            TokenType.NUMBER -> NumberTokenHandler()
            TokenType.STRING -> StringTokenHandler()
            TokenType.BOOLEAN -> BooleanTokenHandler()
            TokenType.OPENPARENTHESIS -> ParenthesisTokenHandler(parser)
            TokenType.READINPUT -> ReadInputHandler(parser)
            else -> throw IllegalArgumentException("Unsupported token type: $tokenType")
        }
    }
}
