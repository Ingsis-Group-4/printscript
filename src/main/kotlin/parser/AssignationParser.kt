package org.example.parser

import org.example.ast.*
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.*
import org.example.token.Token
import org.example.token.TokenType

class AssignationParser(private val parserSelector: Map<TokenType, Parser>): Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return when (val assignmentTokensResult = parseAssignmentTokens(tokens, currentIndex)) {
            is SuccessResult -> parseAssignationSyntax(tokens, assignmentTokensResult.lastValidatedIndex, assignmentTokensResult.value as IdentifierNode)
            is FailureResult -> assignmentTokensResult
        }
    }

    private fun parseAssignmentTokens(tokens: List<Token>, currentIndex: Int): ParserResult {
        val currentToken = at(tokens, currentIndex)
        val nextToken = next(tokens, currentIndex)
        if (currentToken.type != TokenType.IDENTIFIER || nextToken.type != TokenType.ASSIGNATION) {
            return FailureResult("Expected an identifier followed by an assignation token at position $currentIndex", currentIndex)
        }
        return SuccessResult(IdentifierNode(currentToken.value), nextIndex(currentIndex, 2))
    }

    private fun parseAssignationSyntax(tokens: List<Token>, currentIndex: Int, identifierNode: IdentifierNode): ParserResult {
        val syntaxSubtreeResult = getSyntaxSubtree(at(tokens, currentIndex), tokens, currentIndex, parserSelector)
        return when (syntaxSubtreeResult) {
            is SuccessResult -> {
                val semicolonIndex = nextIndex(syntaxSubtreeResult.lastValidatedIndex)
                if (!isEndOfStatement(tokens, semicolonIndex)) {
                    return FailureResult("Expected a semicolon at position $semicolonIndex", semicolonIndex)
                }
                buildParserResult(identifierNode, syntaxSubtreeResult.value as ExpressionNode, semicolonIndex)
            }
            is FailureResult -> syntaxSubtreeResult
        }
    }

    private fun buildParserResult(identifierNode: IdentifierNode, expressionNode: ExpressionNode, lastValidatedIndex: Int): ParserResult {
        val ast = AssignationNode(identifierNode, expressionNode)
        return SuccessResult(ast, lastValidatedIndex)
    }
}
