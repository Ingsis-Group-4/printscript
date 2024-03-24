package org.example.parser

import ast.ExpressionNode
import ast.IdentifierNode
import ast.VariableDeclarationNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.type.DefaultTypeProvider
import org.example.parser.utils.at
import org.example.parser.utils.isTokenValid
import org.example.parser.utils.nextIndex
import org.example.parser.utils.parseAssignationSyntax
import token.Token
import token.TokenType

class VariableDeclarationParser(     private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val identifierIndex = nextIndex(currentIndex)
        val colonIndex = nextIndex(identifierIndex)
        if (!areInitialTokensValid(tokens, currentIndex, identifierIndex, colonIndex)) {
            return FailureResult(
                "Expected a let token followed by an identifier and a colon token at position $currentIndex",
                currentIndex,
            )
        }
        return when (val identifierNode = parseDeclaration(tokens, colonIndex, at(tokens, identifierIndex))) {
            is SuccessResult ->
                parseAssignation(
                    tokens,
                    identifierNode.lastValidatedIndex,
                    identifierNode.value as IdentifierNode,
                )

            is FailureResult -> identifierNode
        }
    }

    private fun areInitialTokensValid(
        tokens: List<Token>,
        letIndex: Int,
        identifierIndex: Int,
        colonIndex: Int,
    ): Boolean {
        return isTokenValid(tokens, letIndex, TokenType.LET) &&
            isTokenValid(tokens, identifierIndex, TokenType.IDENTIFIER) &&
            isTokenValid(tokens, colonIndex, TokenType.COLON)
    }

    private fun parseDeclaration(
        tokens: List<Token>,
        currentIndex: Int,
        identifierToken: Token,
    ): ParserResult {
        val typeIndex = nextIndex(currentIndex)
        when (val type = DefaultTypeProvider.getType(at(tokens, typeIndex).type)) {
            null -> return FailureResult("Invalid type at position $typeIndex", typeIndex)
            else -> {
                val identifierNode = IdentifierNode(identifierToken.value, type)
                return SuccessResult(identifierNode, typeIndex)
            }
        }
    }

    private fun parseAssignation(
        tokens: List<Token>,
        currentIndex: Int,
        identifierNode: IdentifierNode,
    ): ParserResult {
        val newCurrentIndex = nextIndex(currentIndex)
        val token = at(tokens, newCurrentIndex)
        return when (token.type) {
            TokenType.SEMICOLON -> buildParserResult(identifierNode, null, nextIndex(currentIndex))
            TokenType.ASSIGNATION ->
                parseAssignationSyntax(
                    tokens,
                    nextIndex(newCurrentIndex),
                    identifierNode,
                    ::buildParserResult,
                    parserSelector,
                )

            else -> FailureResult("Expected an assignation token or semicolon at position $currentIndex", currentIndex)
        }
    }

    private fun buildParserResult(
        identifierNode: IdentifierNode,
        expressionNode: ExpressionNode?,
        lastValidatedIndex: Int,
    ): ParserResult {
        val ast = VariableDeclarationNode(identifierNode, expressionNode)
        return SuccessResult(ast, lastValidatedIndex)
    }
}
