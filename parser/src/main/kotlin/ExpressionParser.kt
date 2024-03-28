package org.example.parser


import ast.LiteralNode
import org.example.parser.expression.OperandValidator
import org.example.parser.expression.OperatorValidator
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import org.example.parser.utils.getSyntaxSubtree
import org.example.parser.utils.nextIndex
import token.Token
import token.TokenType

//TODO el expression podria tener dos diccionarios, uno de operands y otro de operators
class ExpressionParser(private val operatorSelector: Map<TokenType, Parser>, private val operandSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        if (isOperand(tokens, currentIndex)) {
            val operand = at(tokens, currentIndex)
            return buildSuccessResult(tokens, operand, currentIndex)
        }
        return FailureResult("Invalid operand at position ${at(tokens, currentIndex).start}", currentIndex)
    }

    private fun isOperand(tokens: List<Token>, currentIndex: Int): Boolean {
        val token = at(tokens, currentIndex)
        return OperandValidator.isValid(token)
    }

    private fun isOperator(tokens: List<Token>, currentIndex: Int): Boolean {
        if (currentIndex >= tokens.size) {
            return false
        }
        val token = at(tokens, currentIndex)
        return OperatorValidator.isValid(token)
    }

    private fun buildSuccessResult(tokens: List<Token>, operand: Token, currentIndex: Int): ParserResult {
        val operatorIndex = nextIndex(currentIndex)
        if (isOperator(tokens, nextIndex(currentIndex))) {
            return getSyntaxSubtree(tokens, operatorIndex, operatorSelector)
        }
        return getSyntaxSubtree(tokens, currentIndex, operandSelector)
    }
}
