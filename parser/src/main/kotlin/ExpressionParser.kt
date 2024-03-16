package org.example.parser


import ast.LiteralNode
import org.example.parser.expression.DefaultOperandValidator
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import token.Token
import token.TokenType

class ExpressionParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        if (isOperand(tokens, currentIndex)) {
            val operand = at(tokens, currentIndex).value
            return SuccessResult(LiteralNode(operand), currentIndex)
        }
        return FailureResult("Invalid operand at position ${at(tokens, currentIndex).start}", currentIndex)
    }

    private fun isOperand(tokens: List<Token>, currentIndex: Int): Boolean {
        val token = at(tokens, currentIndex)
        return DefaultOperandValidator.isValid(token)
    }
}
