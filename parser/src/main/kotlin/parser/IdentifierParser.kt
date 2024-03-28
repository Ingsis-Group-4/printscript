package parser

import ast.IdentifierNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import token.Token
import token.TokenType

class IdentifierParser : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return try {
            parseIdentifier(tokens, currentIndex)
        } catch (e: IndexOutOfBoundsException) {
            FailureResult("Unexpected end of input", currentIndex)
        }
    }

    private fun parseIdentifier(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val identifier = at(tokens, currentIndex)
        if (identifier.type == TokenType.IDENTIFIER) {
            return SuccessResult(
                IdentifierNode(
                    identifier.value,
                    start = identifier.start,
                    end = identifier.end,
                ),
                currentIndex,
            )
        }
        return FailureResult("Expected a identifier", currentIndex)
    }
}
