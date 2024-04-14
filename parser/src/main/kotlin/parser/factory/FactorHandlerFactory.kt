package parser.factory

import parser.ExpressionParser
import parser.strategy.FactorHandler
import parser.strategy.IdentifierHandler
import parser.strategy.NegativeHandler
import parser.strategy.NumberHandler
import parser.strategy.ParenthesisHandler
import parser.strategy.StringHandler
import token.TokenType

interface FactorHandlerFactory {
    fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): FactorHandler
}

object DefaultFactorHandlerFactory : FactorHandlerFactory {
    override fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): FactorHandler {
        return when (tokenType) {
            TokenType.IDENTIFIER -> IdentifierHandler()
            TokenType.NUMBER -> NumberHandler()
            TokenType.STRING -> StringHandler()
            TokenType.OPENPARENTHESIS -> ParenthesisHandler(parser)
            TokenType.SUBTRACTION -> NegativeHandler(parser)
            // Agrega casos para otros tipos de tokens si es necesario
            else -> throw IllegalArgumentException("Unsupported token type: $tokenType")
        }
    }
}
