package parser

import ast.LiteralNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import token.Token
import token.TokenType

class StringParser : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return try {
            parseString(tokens, currentIndex)
        } catch (e: IndexOutOfBoundsException) {
            FailureResult("Unexpected end of input", currentIndex)
        }
    }

    private fun parseString(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val string = at(tokens, currentIndex)
        if (string.type == TokenType.STRING) {
            return SuccessResult(
                LiteralNode(
                    string.value,
                    start = string.start,
                    end = string.end,
                ),
                currentIndex,
            )
        }
        return FailureResult("Expected a string", currentIndex)
    }
}
