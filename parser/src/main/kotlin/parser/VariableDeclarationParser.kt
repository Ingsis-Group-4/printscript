package parser

import ast.ColonNode
import ast.DeclarationNode
import ast.EqualsNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.LetNode
import ast.VariableTypeNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.type.TypeProvider
import parser.type.TypeProviderV1
import parser.utils.at
import parser.utils.getSyntaxSubtree
import parser.utils.isEndOfStatement
import parser.utils.isTokenValid
import parser.utils.nextIndex
import position.Position
import token.Token
import token.TokenType

class VariableDeclarationParser(
    private val parserSelector: Map<TokenType, Parser>,
    private val typeProvider: TypeProvider = TypeProviderV1,
) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val keywordPositionStart = at(tokens, currentIndex).start
        val keywordPositionEnd = at(tokens, currentIndex).end
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
                    keywordPositionStart,
                    keywordPositionEnd,
                    at(tokens, colonIndex).start,
                    at(tokens, nextIndex(colonIndex)),
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
        val typeToken = at(tokens, typeIndex)

        when (val type = typeProvider.getType(typeToken.type)) {
            null -> return FailureResult("Invalid type at position $typeIndex", typeIndex)
            else -> {
                val identifierNode =
                    IdentifierNode(
                        identifierToken.value,
                        type,
                        identifierToken.start,
                        at(tokens, typeIndex).end,
                    )
                return SuccessResult(identifierNode, typeIndex)
            }
        }
    }

    private fun parseAssignation(
        tokens: List<Token>,
        currentIndex: Int,
        identifierNode: IdentifierNode,
        letPositionStart: Position,
        letPositionEnd: Position,
        colonPosition: Position,
        variableTypeToken: Token,
    ): ParserResult {
        val semicolonOrAssignationIndex = nextIndex(currentIndex)
        val token = at(tokens, semicolonOrAssignationIndex)
        return when (token.type) {
            TokenType.SEMICOLON ->
                buildParserResult(
                    identifierNode,
                    null,
                    nextIndex(currentIndex),
                    letPositionStart,
                    letPositionEnd,
                    colonPosition,
                    variableTypeToken,
                    null,
                    token.end,
                )
            TokenType.ASSIGNATION ->
                this.parseAssignationSyntax(
                    tokens,
                    semicolonOrAssignationIndex,
                    identifierNode,
                    parserSelector,
                    letPositionStart,
                    letPositionEnd,
                    colonPosition,
                    variableTypeToken,
                )

            else -> FailureResult("Expected an assignation token or semicolon at position $currentIndex", currentIndex)
        }
    }

    private fun parseAssignationSyntax(
        tokens: List<Token>,
        equalsIndex: Int,
        identifierNode: IdentifierNode,
        parserSelector: Map<TokenType, Parser>,
        letPositionStart: Position,
        letPositionEnd: Position,
        colonPosition: Position,
        variableTypeToken: Token,
    ): ParserResult {
        val syntaxSubtreeResult = getSyntaxSubtree(tokens, nextIndex(equalsIndex), parserSelector)

        return when (syntaxSubtreeResult) {
            is SuccessResult -> {
                val semicolonIndex = nextIndex(syntaxSubtreeResult.lastValidatedIndex)
                if (!isEndOfStatement(tokens, semicolonIndex)) {
                    return FailureResult("Expected a semicolon at position $semicolonIndex", semicolonIndex)
                }
                buildParserResult(
                    identifierNode,
                    syntaxSubtreeResult.value as ExpressionNode,
                    semicolonIndex,
                    letPositionStart,
                    letPositionEnd,
                    colonPosition,
                    variableTypeToken,
                    equalsPosition = at(tokens, equalsIndex).start,
                    semicolonPosition = at(tokens, semicolonIndex).end,
                )
            }

            is FailureResult -> syntaxSubtreeResult
        }
    }

    private fun buildParserResult(
        identifierNode: IdentifierNode,
        expressionNode: ExpressionNode?,
        lastValidatedIndex: Int,
        letPositionStart: Position,
        letPositionEnd: Position,
        colonPosition: Position,
        variableTypeToken: Token,
        equalsPosition: Position?,
        semicolonPosition: Position,
    ): ParserResult {
        val ast =
            DeclarationNode(
                identifierNode,
                expressionNode,
                LetNode(letPositionStart, letPositionEnd),
                ColonNode(colonPosition, colonPosition),
                VariableTypeNode(identifierNode.variableType!!, variableTypeToken.start, variableTypeToken.end),
                equalsNode = equalsPosition?.let { EqualsNode(it, it) },
                letPositionStart,
                semicolonPosition,
            )
        return SuccessResult(ast, lastValidatedIndex)
    }
}
