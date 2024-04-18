package parser

import ast.ColonNode
import ast.ConstNode
import ast.DeclarationNode
import ast.EqualsNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.KeywordNode
import ast.LetNode
import ast.VariableTypeNode
import parser.provider.TypeProvider
import parser.provider.TypeProviderV1
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

class VariableDeclarationParser(
    private val parserSelector: Map<TokenType, Parser>,
    private val typeProvider: TypeProvider = TypeProviderV1,
) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val keywordToken = at(tokens, currentIndex)
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
                    keywordToken,
                    at(tokens, colonIndex).start,
                    at(tokens, nextIndex(colonIndex)),
                )

            is FailureResult -> identifierNode
        }
    }

    private fun areInitialTokensValid(
        tokens: List<Token>,
        keywordIndex: Int,
        identifierIndex: Int,
        colonIndex: Int,
    ): Boolean {
        return (isTokenValid(tokens, keywordIndex, TokenType.LET) || isTokenValid(tokens, keywordIndex, TokenType.CONST)) &&
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
        keyWordToken: Token,
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
                    keyWordToken,
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
                    keyWordToken,
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
        keyWordToken: Token,
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
                    keyWordToken,
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
        keyWordToken: Token,
        colonPosition: Position,
        variableTypeToken: Token,
        equalsPosition: Position?,
        semicolonPosition: Position,
    ): ParserResult {
        val ast =
            DeclarationNode(
                identifierNode,
                expressionNode,
                getKeyWordNode(keyWordToken),
                ColonNode(colonPosition, colonPosition),
                VariableTypeNode(identifierNode.variableType!!, variableTypeToken.start, variableTypeToken.end),
                equalsNode = equalsPosition?.let { EqualsNode(it, it) },
                keyWordToken.start,
                semicolonPosition,
            )
        return SuccessResult(ast, lastValidatedIndex)
    }

    private fun getKeyWordNode(token: Token): KeywordNode {
        return when (token.type) {
            TokenType.LET -> LetNode(token.start, token.end)
            TokenType.CONST -> ConstNode(token.start, token.end)
            else -> throw Exception("Invalid keyword at position ${token.start}")
        }
    }
}
