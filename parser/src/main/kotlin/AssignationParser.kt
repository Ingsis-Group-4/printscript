package org.example.parser

import ast.AssignationNode
import ast.ExpressionNode
import ast.IdentifierNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.*
import position.Position
import token.Token
import token.TokenType

class AssignationParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return when (val assignmentTokensResult = parseAssignmentTokens(tokens, currentIndex)) {
            is SuccessResult ->
                this.parseAssignationSyntax(
                    tokens = tokens,
                    currentIndex = assignmentTokensResult.lastValidatedIndex,
                    identifierNode = assignmentTokensResult.value as IdentifierNode,
                    parserSelector = parserSelector
                )

            is FailureResult -> assignmentTokensResult
        }
    }

    private fun parseAssignmentTokens(tokens: List<Token>, currentIndex: Int): ParserResult {
        val currentToken = at(tokens, currentIndex)
        val nextToken = next(tokens, currentIndex)

        if (currentToken.type != TokenType.IDENTIFIER || nextToken.type != TokenType.ASSIGNATION) {
            return FailureResult(
                "Expected an identifier followed by an assignation token at position $currentIndex",
                currentIndex
            )
        }

        return SuccessResult(
            IdentifierNode(
                currentToken.value,
                start = currentToken.start,
                end = currentToken.end
            ), nextIndex(currentIndex, 2))
    }

    private fun parseAssignationSyntax(
        tokens: List<Token>,
        currentIndex: Int,
        identifierNode: IdentifierNode,
        parserSelector: Map<TokenType, Parser>
    ): ParserResult {
        val expressionSubtree = getSyntaxSubtree(tokens, currentIndex, parserSelector)

        return when (expressionSubtree) {
            is SuccessResult -> {
                val semicolonIndex = nextIndex(expressionSubtree.lastValidatedIndex)

                if (!isEndOfStatement(tokens, semicolonIndex)) {
                    return FailureResult("Expected a semicolon at position $semicolonIndex", semicolonIndex)
                }

                buildParserResult(
                    identifierNode = identifierNode,
                    expressionNode = expressionSubtree.value as ExpressionNode,
                    lastValidatedIndex = semicolonIndex,
                    assignationEnd = at(tokens, semicolonIndex).end
                )
            }

            is FailureResult -> expressionSubtree
        }
    }

    private fun buildParserResult(
        identifierNode: IdentifierNode,
        expressionNode: ExpressionNode,
        lastValidatedIndex: Int,
        assignationEnd: Position
    ): ParserResult {
        val ast = AssignationNode(
            identifierNode,
            expressionNode,
            start = identifierNode.getStart(),
            end = assignationEnd
        )

        return SuccessResult(ast, lastValidatedIndex)
    }
}
