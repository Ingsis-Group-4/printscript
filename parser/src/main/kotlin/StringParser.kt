package org.example.parser

import ast.LiteralNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import token.Token
import token.TokenType

class StringParser: Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return try {
            parseString(tokens, currentIndex)
        } catch (e: IndexOutOfBoundsException) {
            FailureResult("Unexpected end of input", currentIndex)
        }
    }

    private fun parseString(
        tokens: List<Token>,
        currentIndex: Int
    ): ParserResult {
        val string = at(tokens, currentIndex)
        if (string.type == TokenType.STRING) {
            return SuccessResult(
                LiteralNode(
                    string.value,
                    start = string.start,
                    end = string.end
                ),
                currentIndex
            )
        }
        return FailureResult("Expected a string", currentIndex)
    }
}