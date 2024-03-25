package org.example.parser

import ast.IdentifierNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import token.Token
import token.TokenType

class IdentifierParser: Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return try {
            parseIdentifier(tokens, currentIndex)
        } catch (e: IndexOutOfBoundsException) {
            FailureResult("Unexpected end of input", currentIndex)
        }
    }

    private fun parseIdentifier(
        tokens: List<Token>,
        currentIndex: Int
    ): ParserResult {
        val identifier = at(tokens, currentIndex)
        if (identifier.type == TokenType.IDENTIFIER) {
            return SuccessResult(IdentifierNode(identifier.value), currentIndex)
        }
        return FailureResult("Expected a identifier", currentIndex)
    }
}