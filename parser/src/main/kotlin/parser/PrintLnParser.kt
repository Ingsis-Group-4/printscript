package parser

import ast.ExpressionNode
import ast.PrintLnNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.getSyntaxSubtree
import parser.utils.isEndOfStatement
import parser.utils.isTokenValid
import parser.utils.nextIndex
import token.Token
import token.TokenType

class PrintLnParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val openParenthesisIndex = nextIndex(currentIndex)
        if (!areInitialTokensValid(tokens, currentIndex, openParenthesisIndex)) {
            return FailureResult(
                "Expected println token followed by an open parenthesis at position $currentIndex\")",
                currentIndex,
            )
        }
        val expressionIndex = nextIndex(openParenthesisIndex)
        val nextToken = at(tokens, expressionIndex)
        val syntaxSubtree = getSyntaxSubtree(nextToken, tokens, expressionIndex, parserSelector)
        return parsePrintLnSyntax(tokens, syntaxSubtree)
    }

    private fun areInitialTokensValid(
        tokens: List<Token>,
        printLnIndex: Int,
        openParenthesisIndex: Int,
    ): Boolean {
        return isTokenValid(tokens, printLnIndex, TokenType.PRINTLN) &&
            isTokenValid(
                tokens,
                openParenthesisIndex,
                TokenType.OPENPARENTHESIS,
            )
    }

    private fun areFinalTokensValid(
        tokens: List<Token>,
        closeParenthesisIndex: Int,
    ): Boolean {
        return isTokenValid(tokens, closeParenthesisIndex, TokenType.CLOSEPARENTHESIS) &&
            isEndOfStatement(
                tokens,
                nextIndex(closeParenthesisIndex),
            )
    }

    private fun parsePrintLnSyntax(
        tokens: List<Token>,
        syntaxSubtree: ParserResult,
    ): ParserResult {
        return when (syntaxSubtree) {
            is SuccessResult -> {
                val closeParenthesisIndex = nextIndex(syntaxSubtree.lastValidatedIndex)
                if (!areFinalTokensValid(tokens, closeParenthesisIndex)) {
                    return FailureResult(
                        "Expected a close parenthesis followed by a semicolon at position $closeParenthesisIndex",
                        closeParenthesisIndex,
                    )
                }
                return builtParserResult(syntaxSubtree.value as ExpressionNode, nextIndex(closeParenthesisIndex))
            }

            is FailureResult -> syntaxSubtree
        }
    }

    private fun builtParserResult(
        expressionNode: ExpressionNode,
        lastValidatedIndex: Int,
    ): ParserResult {
        val ast = PrintLnNode(expressionNode)
        return SuccessResult(ast, lastValidatedIndex)
    }
}
