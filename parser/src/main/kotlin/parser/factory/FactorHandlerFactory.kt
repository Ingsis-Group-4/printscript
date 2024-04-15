package parser.factory

import parser.ExpressionParser
import parser.strategy.BooleanTokenHandler
import parser.strategy.FactorHandler
import parser.strategy.IdentifierHandler
import parser.strategy.NegativeHandler
import parser.strategy.NumberHandler
import parser.strategy.ParenthesisHandler
import parser.strategy.ReadEnvHandler
import parser.strategy.ReadInputHandler
import parser.strategy.StringHandler
import token.TokenType

interface FactorHandlerFactory {
    fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): FactorHandler
}

object FactorHandlerFactoryV1 : FactorHandlerFactory {
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
            else -> throw IllegalArgumentException("Unsupported token type: $tokenType")
        }
    }
}

object FactorHandlerFactoryV2 : FactorHandlerFactory {
    override fun getHandler(
        tokenType: TokenType,
        parser: ExpressionParser,
    ): FactorHandler {
        return when (tokenType) {
            TokenType.IDENTIFIER -> IdentifierHandler()
            TokenType.NUMBER -> NumberHandler()
            TokenType.STRING -> StringHandler()
            TokenType.BOOLEAN -> BooleanTokenHandler()
            TokenType.OPENPARENTHESIS -> ParenthesisHandler(parser)
            TokenType.READINPUT -> ReadInputHandler(parser)
            TokenType.READENV -> ReadEnvHandler(parser)
            else -> throw IllegalArgumentException("Unsupported token type: $tokenType")
        }
    }
}
