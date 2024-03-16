package org.example.parser.expression

import token.Token
import token.TokenType

/**
 * Interface that defines a validator for operands in an expression parser.
 * An operand validator is responsible for determining whether a given token is a valid operand.
 */
interface OperandValidator {
    /**
     * Checks if the provided token is a valid operand.
     * @param token The token to be validated.
     * @return true if the token is a valid operand, false otherwise.
     */
    fun isValid(token: Token): Boolean
}

/**
 * Default implementation of OperandValidator.
 * This implementation validates tokens based on their types: IDENTIFIER, NUMBER, and STRING are considered valid operands.
 */
object DefaultOperandValidator : OperandValidator {

    override fun isValid(token: Token): Boolean {
        return when (token.type) {
            TokenType.IDENTIFIER,
            TokenType.NUMBER,
            TokenType.STRING -> true

            else -> false
        }
    }
}

