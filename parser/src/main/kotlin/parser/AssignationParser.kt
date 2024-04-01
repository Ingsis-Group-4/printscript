package parser

import ast.AssignationNode
import ast.EqualsNode
import ast.ExpressionNode
import ast.IdentifierNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.getSyntaxSubtree
import parser.utils.isEndOfStatement
import parser.utils.next
import parser.utils.nextIndex
import position.Position
import token.Token
import token.TokenType

class AssignationParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return when (val assignmentTokensResult = parseAssignmentTokens(tokens, currentIndex)) {
            is SuccessResult ->
                this.parseAssignationSyntax(
                    tokens = tokens,
                    currentIndex = assignmentTokensResult.lastValidatedIndex,
                    identifierNode = assignmentTokensResult.value as IdentifierNode,
                    parserSelector = parserSelector,
                )

            is FailureResult -> assignmentTokensResult
        }
    }

    private fun parseAssignmentTokens(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val currentToken = at(tokens, currentIndex)
        val nextToken = next(tokens, currentIndex)

        if (currentToken.type != TokenType.IDENTIFIER || nextToken.type != TokenType.ASSIGNATION) {
            return FailureResult(
                "Expected an identifier followed by an assignation token at position $currentIndex",
                currentIndex,
            )
        }

        return SuccessResult(
            IdentifierNode(
                currentToken.value,
                start = currentToken.start,
                end = currentToken.end,
            ),
            nextIndex(currentIndex, 1),
        )
    }

    private fun parseAssignationSyntax(
        tokens: List<Token>,
        currentIndex: Int,
        identifierNode: IdentifierNode,
        parserSelector: Map<TokenType, Parser>,
    ): ParserResult {
        val assignationToken = at(tokens, currentIndex)
        val expressionIndex = nextIndex(currentIndex)

        val expressionSubtree = getSyntaxSubtree(tokens, expressionIndex, parserSelector)

        return when (expressionSubtree) {
            is SuccessResult -> {
                val semicolonIndex = nextIndex(expressionSubtree.lastValidatedIndex)

                if (!isEndOfStatement(tokens, semicolonIndex)) {
                    return FailureResult("Expected a semicolon at position $semicolonIndex", semicolonIndex)
                }

                buildParserResult(
                    identifierNode = identifierNode,
                    expressionNode = expressionSubtree.value as ExpressionNode,
                    assignationTokenPosition = assignationToken.start,
                    lastValidatedIndex = semicolonIndex,
                    assignationEnd = at(tokens, semicolonIndex).end,
                )
            }

            is FailureResult -> expressionSubtree
        }
    }

    private fun buildParserResult(
        identifierNode: IdentifierNode,
        expressionNode: ExpressionNode,
        assignationTokenPosition: Position,
        lastValidatedIndex: Int,
        assignationEnd: Position,
    ): ParserResult {
        val ast =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode = EqualsNode(assignationTokenPosition, assignationTokenPosition),
                start = identifierNode.getStart(),
                end = assignationEnd,
            )

        return SuccessResult(ast, lastValidatedIndex)
    }
}
