package parser

import ast.LiteralNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import token.Token
import token.TokenType

class NumberParser : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return try {
            parseNumber(tokens, currentIndex)
        } catch (e: IndexOutOfBoundsException) {
            FailureResult("Unexpected end of input", currentIndex)
        }
    }

    private fun parseNumber(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val number = at(tokens, currentIndex)
        if (number.type == TokenType.NUMBER) {
            return SuccessResult(
                LiteralNode(
                    number.value.toDouble(),
                    start = number.start,
                    end = number.end,
                ),
                currentIndex,
            )
        }
        return FailureResult("Expected a number", currentIndex)
    }
}
