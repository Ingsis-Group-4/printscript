package org.example.parser

import ast.AssignationNode
import ast.ExpressionNode
import ast.IdentifierNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import org.example.parser.utils.next
import org.example.parser.utils.nextIndex
import org.example.parser.utils.parseAssignationSyntax
import token.Token
import token.TokenType

class AssignationParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return when (val assignmentTokensResult = parseAssignmentTokens(tokens, currentIndex)) {
            is SuccessResult ->
                parseAssignationSyntax(
                    tokens,
                    assignmentTokensResult.lastValidatedIndex,
                    assignmentTokensResult.value as IdentifierNode,
                    ::buildParserResult,
                    parserSelector,
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
        return SuccessResult(IdentifierNode(currentToken.value), nextIndex(currentIndex, 2))
    }

    private fun buildParserResult(
        identifierNode: IdentifierNode,
        expressionNode: ExpressionNode,
        lastValidatedIndex: Int,
    ): ParserResult {
        val ast = AssignationNode(identifierNode, expressionNode)
        return SuccessResult(ast, lastValidatedIndex)
    }
}
