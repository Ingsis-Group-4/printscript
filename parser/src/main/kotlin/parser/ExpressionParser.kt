package parser

import ast.LiteralNode
import parser.expression.DefaultOperandValidator
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import token.Token
import token.TokenType

class ExpressionParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        if (isOperand(tokens, currentIndex)) {
            val operand = at(tokens, currentIndex)
            return buildSuccessResult(operand, currentIndex)
        }
        return FailureResult("Invalid operand at position ${at(tokens, currentIndex).start}", currentIndex)
    }

    private fun isOperand(
        tokens: List<Token>,
        currentIndex: Int,
    ): Boolean {
        val token = at(tokens, currentIndex)
        return DefaultOperandValidator.isValid(token)
    }

    private fun buildSuccessResult(
        operand: Token,
        currentIndex: Int,
    ): ParserResult {
        return when (operand.type) {
            TokenType.NUMBER -> {
                return SuccessResult(LiteralNode(operand.value.toDouble()), currentIndex)
            }
            TokenType.STRING -> {
                return SuccessResult(LiteralNode(operand.value), currentIndex)
            }
            else -> {
                FailureResult("Invalid operand at position ${operand.start}", currentIndex)
            }
        }
    }
}
