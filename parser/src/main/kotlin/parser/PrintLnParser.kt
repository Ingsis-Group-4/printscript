package parser

import ast.ExpressionNode
import ast.FunctionStatementNode
import ast.PrintLnNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.getSyntaxSubtree
import parser.utils.isEndOfStatement
import parser.utils.isTokenValid
import parser.utils.nextIndex
import position.Position
import token.Token
import token.TokenType

class PrintLnParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val printLnStartPosition = at(tokens, currentIndex).start
        val openParenthesisIndex = nextIndex(currentIndex)

        if (!areInitialTokensValid(tokens, currentIndex, openParenthesisIndex)) {
            return FailureResult(
                "Expected println token followed by an open parenthesis at position $currentIndex\")",
                currentIndex,
            )
        }

        val expressionIndex = nextIndex(openParenthesisIndex)
        val expressionSubtree = getSyntaxSubtree(tokens, expressionIndex, parserSelector)
        return parsePrintLnSyntax(tokens, expressionSubtree, printLnStartPosition)
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
        expressionSubtree: ParserResult,
        startPosition: Position,
    ): ParserResult {
        return when (expressionSubtree) {
            is SuccessResult -> {
                val closeParenthesisIndex = nextIndex(expressionSubtree.lastValidatedIndex)

                if (!areFinalTokensValid(tokens, closeParenthesisIndex)) {
                    return FailureResult(
                        message = "Expected a close parenthesis followed by a semicolon at position $closeParenthesisIndex",
                        lastValidatedIndex = closeParenthesisIndex,
                    )
                }

                val semicolonPosition = at(tokens, nextIndex(closeParenthesisIndex)).end

                return buildParserResult(
                    expressionNode = expressionSubtree.value as ExpressionNode,
                    lastValidatedIndex = nextIndex(closeParenthesisIndex),
                    startPosition = startPosition,
                    semicolonPosition = semicolonPosition,
                    closeParenthesisPosition = at(tokens, closeParenthesisIndex).start,
                )
            }

            is FailureResult -> expressionSubtree
        }
    }

    private fun buildParserResult(
        expressionNode: ExpressionNode,
        lastValidatedIndex: Int,
        startPosition: Position,
        closeParenthesisPosition: Position,
        semicolonPosition: Position,
    ): ParserResult {
        val functionStatementNode =
            FunctionStatementNode(
                startPosition,
                semicolonPosition,
                PrintLnNode(expressionNode, startPosition, closeParenthesisPosition),
            )
        return SuccessResult(functionStatementNode, lastValidatedIndex)
    }
}
