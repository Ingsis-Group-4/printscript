package parser

import ast.StatementNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.getSyntaxSubtree
import token.Token
import token.TokenType

class StatementParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return parseStatement(tokens, currentIndex)
    }

    private fun parseStatement(
        statement: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return when (val result = getSyntaxSubtree(statement, currentIndex, parserSelector)) {
            is SuccessResult -> {
                when (result.value) {
                    is StatementNode -> result
                    else -> FailureResult("Error parsing statement", result.lastValidatedIndex)
                }
            }

            is FailureResult -> result
        }
    }
}
