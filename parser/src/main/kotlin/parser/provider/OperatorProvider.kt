package parser.provider

import parser.utils.at
import token.Token
import token.TokenType

object OperatorProvider {
    private val primaryOperators: MutableSet<TokenType> =
        mutableSetOf(
            TokenType.SUM,
            TokenType.SUBTRACTION,
        )
    private val termOperators: MutableSet<TokenType> =
        mutableSetOf(
            TokenType.MULTIPLICATION,
            TokenType.DIVISION,
        )

    fun isPrimaryOperator(
        tokens: List<Token>,
        currentOperatorIndex: Int,
    ): Boolean {
        return at(tokens, currentOperatorIndex).type in primaryOperators
    }

    fun isTermOperator(
        tokens: List<Token>,
        currentOperatorIndex: Int,
    ): Boolean {
        return at(tokens, currentOperatorIndex).type in termOperators
    }
}
