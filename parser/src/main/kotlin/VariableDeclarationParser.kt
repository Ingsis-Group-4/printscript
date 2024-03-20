package org.example.parser

import ast.ExpressionNode
import ast.IdentifierNode
import ast.VariableDeclarationNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.type.DefaultTypeProvider
import org.example.parser.utils.*
import position.Position
import token.Token
import token.TokenType

class VariableDeclarationParser(private val parserSelector: Map<TokenType, Parser>) : Parser {

    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        val identifierIndex = nextIndex(currentIndex)
        val colonIndex = nextIndex(identifierIndex)
        if (!areInitialTokensValid(tokens, currentIndex, identifierIndex, colonIndex)) {
            return FailureResult(
                "Expected a let token followed by an identifier and a colon token at position $currentIndex",
                currentIndex
            )
        }
        return when (val identifierNode = parseDeclaration(tokens, colonIndex, at(tokens, identifierIndex))) {
            is SuccessResult -> parseAssignation(
                tokens,
                identifierNode.lastValidatedIndex,
                identifierNode.value as IdentifierNode,
                at(tokens, currentIndex).start
            )

            is FailureResult -> identifierNode
        }
    }

    private fun areInitialTokensValid(
        tokens: List<Token>,
        letIndex: Int,
        identifierIndex: Int,
        colonIndex: Int
    ): Boolean {
        return isTokenValid(tokens, letIndex, TokenType.LET) &&
                isTokenValid(tokens, identifierIndex, TokenType.IDENTIFIER) &&
                isTokenValid(tokens, colonIndex, TokenType.COLON)
    }

    private fun parseDeclaration(tokens: List<Token>, currentIndex: Int, identifierToken: Token): ParserResult {
        val typeIndex = nextIndex(currentIndex)
        when (val type = DefaultTypeProvider.getType(at(tokens, typeIndex).type)) {
            null -> return FailureResult("Invalid type at position $typeIndex", typeIndex)
            else -> {
                val identifierNode = IdentifierNode(identifierToken.value, type, identifierToken.start, at(tokens, typeIndex).end)
                return SuccessResult(identifierNode, typeIndex)
            }
        }
    }

    private fun parseAssignation(tokens: List<Token>, currentIndex: Int, identifierNode: IdentifierNode, letPosition: Position): ParserResult {
        val newCurrentIndex = nextIndex(currentIndex)
        val token = at(tokens, newCurrentIndex)
        return when (token.type) {
            TokenType.SEMICOLON -> buildParserResult(identifierNode, null, nextIndex(currentIndex), letPosition, token.end)
            TokenType.ASSIGNATION -> this.parseAssignationSyntax(
                tokens,
                nextIndex(newCurrentIndex),
                identifierNode,
                parserSelector,
                letPosition
            )

            else -> FailureResult("Expected an assignation token or semicolon at position $currentIndex", currentIndex)
        }
    }

    private fun parseAssignationSyntax(
        tokens: List<Token>,
        currentIndex: Int,
        identifierNode: IdentifierNode,
        parserSelector: Map<TokenType, Parser>,
        letPosition: Position
    ): ParserResult {
        val syntaxSubtreeResult = getSyntaxSubtree(at(tokens, currentIndex), tokens, currentIndex, parserSelector)
        return when (syntaxSubtreeResult) {
            is SuccessResult -> {
                val semicolonIndex = nextIndex(syntaxSubtreeResult.lastValidatedIndex)
                if (!isEndOfStatement(tokens, semicolonIndex)) {
                    return FailureResult("Expected a semicolon at position $semicolonIndex", semicolonIndex)
                }
                buildParserResult(identifierNode, syntaxSubtreeResult.value as ExpressionNode, semicolonIndex, letPosition ,at(tokens, semicolonIndex).end)
            }

            is FailureResult -> syntaxSubtreeResult
        }
    }

    private fun buildParserResult(
        identifierNode: IdentifierNode,
        expressionNode: ExpressionNode?,
        lastValidatedIndex: Int,
        letPosition: Position,
        semicolonPosition: Position
    ): ParserResult {
        val ast = VariableDeclarationNode(identifierNode, expressionNode, letPosition, semicolonPosition)
        return SuccessResult(ast, lastValidatedIndex)
    }
}
