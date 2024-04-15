package parser.conditional

import ast.BlockNode
import ast.ExpressionNode
import ast.IfStatement
import parser.Parser
import parser.factory.ExpressionParserFactory
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.consume
import parser.utils.isTokenValid
import token.Token
import token.TokenType

class IfStatementParser(
    private val blockParser: Parser,
) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        // Check initial tokens and get their indexes
        val openParentIndex = consume(tokens, currentIndex, TokenType.IF)
        val identifierIndex = consume(tokens, openParentIndex, TokenType.OPENPARENTHESIS)
        val closeParentIndex = consume(tokens, identifierIndex, TokenType.IDENTIFIER)
        val openCurlyIndex = consume(tokens, closeParentIndex, TokenType.CLOSEPARENTHESIS)
        consume(tokens, openCurlyIndex, TokenType.OPENCURLY)

        // Parse then block
        val thenBlockResult = parseBlock(tokens, openCurlyIndex)

        // Get potential else index
        val elseIndex = getElseIndex(thenBlockResult)

        // If else token present, return result with else. If not, return without it
        return if (hasElse(elseIndex, tokens)) {
            buildResultWithElse(elseIndex, tokens, currentIndex, thenBlockResult, identifierIndex)
        } else {
            buildResultWithoutElse(tokens, currentIndex, thenBlockResult, identifierIndex)
        }
    }

    private fun buildResultWithoutElse(
        tokens: List<Token>,
        currentIndex: Int,
        thenBlockResult: SuccessResult,
        identifierIndex: Int,
    ): SuccessResult {
        return SuccessResult(
            IfStatement(
                start = at(tokens, currentIndex).start,
                end = thenBlockResult.value.getEnd(),
                condition = getExpressionNode(tokens, identifierIndex),
                thenBlock = thenBlockResult.value as BlockNode,
                elseBlock = null,
            ),
            thenBlockResult.lastValidatedIndex,
        )
    }

    private fun buildResultWithElse(
        elseIndex: Int,
        tokens: List<Token>,
        currentIndex: Int,
        thenBlockResult: SuccessResult,
        identifierIndex: Int,
    ): SuccessResult {
        val elseOpenCurlyIndex = elseIndex + 1

        val elseBlockResult = parseBlock(tokens, elseOpenCurlyIndex)

        return SuccessResult(
            IfStatement(
                start = at(tokens, currentIndex).start,
                end = thenBlockResult.value.getEnd(),
                condition =
                    getExpressionNode(tokens, identifierIndex),
                thenBlock = thenBlockResult.value as BlockNode,
                elseBlock = elseBlockResult.value as BlockNode,
            ),
            thenBlockResult.lastValidatedIndex,
        )
    }

    private fun getExpressionNode(
        tokens: List<Token>,
        identifierIndex: Int,
    ) = (
        ExpressionParserFactory.create()
            .parse(tokens, identifierIndex) as SuccessResult
    ).value as ExpressionNode

    private fun hasElse(
        elseIndex: Int,
        tokens: List<Token>,
    ) = elseIndex < tokens.size && isTokenValid(tokens, elseIndex, TokenType.ELSE)

    private fun getElseIndex(thenBlockResult: SuccessResult) = thenBlockResult.lastValidatedIndex + 5

    private fun parseBlock(
        tokens: List<Token>,
        openCurlyIndex: Int,
    ) = blockParser.parse(tokens.slice(openCurlyIndex until tokens.size), 0) as SuccessResult
}
