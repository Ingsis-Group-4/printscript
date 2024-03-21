package org.example.parser

import ast.LiteralNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import token.Token
import token.TokenType

class NumberParser: Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return try {
            parseNumber(tokens, currentIndex)
        } catch (e: IndexOutOfBoundsException) {
            FailureResult("Unexpected end of input", currentIndex)
        }
    }

    private fun parseNumber(
        tokens: List<Token>,
        currentIndex: Int
    ): ParserResult {
        val number = at(tokens, currentIndex)
        if (number.type == TokenType.NUMBER) {
            return SuccessResult(LiteralNode(number.value.toDouble()), currentIndex)
        }
        return FailureResult("Expected a number", currentIndex)
    }
}