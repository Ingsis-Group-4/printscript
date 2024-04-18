package parser

import ast.ProgramNode
import ast.StatementNode
import parser.provider.BlockTokenProvider
import parser.provider.BlockTokenProviderV1
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.getSyntaxSubtree
import parser.utils.isEndOfStatement
import parser.utils.isOutOfBounds
import parser.utils.isTokenValid
import parser.utils.next
import parser.utils.nextIndex
import token.Token
import token.TokenType

class ProgramParser(
    private val parserSelector: Map<TokenType, Parser>,
    private val blockTokenProvider: BlockTokenProvider = BlockTokenProviderV1,
) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val statementTokens = divideIntoStatements(tokens)
        return parseStatements(statementTokens, currentIndex)
    }

    private fun divideIntoStatements(tokens: List<Token>): List<List<Token>> {
        val statements = mutableListOf<List<Token>>()
        var currentStatement = mutableListOf<Token>()
        var openBlock = false

        for (i in tokens.indices) {
            val token = at(tokens, i)
            if (isTokenValid(tokens, i, TokenType.OPENCURLY)) {
                openBlock = true
            }
            if (isTokenValid(tokens, i, TokenType.CLOSECURLY)) {
                openBlock = false
            }
            if (!isOutOfBounds(tokens, nextIndex(i))) {
                if (next(tokens, i).type in blockTokenProvider.getNestedBlockTokens()) {
                    currentStatement.add(token)
                    openBlock = true
                    continue
                }
            }
            if ((isEndOfStatement(token) && !openBlock) || isTokenValid(tokens, i, TokenType.CLOSECURLY)) {
                currentStatement.add(token)
                statements.add(currentStatement)
                currentStatement = mutableListOf()
            } else {
                currentStatement.add(token)
            }
        }
        return statements
    }

    private fun parseStatements(
        statementTokens: List<List<Token>>,
        currentIndex: Int,
    ): ParserResult {
        val statementNodes = mutableListOf<StatementNode>()
        var lastIndexedValue = currentIndex

        for (statement in statementTokens) {
            when (val result = parseStatement(statement, currentIndex)) {
                is SuccessResult -> {
                    lastIndexedValue = result.lastValidatedIndex
                    statementNodes.add(result.value as StatementNode)
                }

                is FailureResult -> return result
            }
        }

        return buildParserResult(statementNodes, lastIndexedValue)
    }

    private fun parseStatement(
        statement: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return when (val result = getSyntaxSubtree(statement, currentIndex, parserSelector)) {
            is SuccessResult -> {
                when (result.value) {
                    is StatementNode -> result
                    else -> FailureResult("Error parsing statement", result.lastValidatedIndex)
                }
            }

            is FailureResult -> result
        }
    }

    private fun buildParserResult(
        asts: List<StatementNode>,
        lastIndexedValue: Int,
    ): ParserResult {
        val startPosition = asts.first().getStart()
        val endPosition = asts.last().getEnd()

        return SuccessResult(ProgramNode(asts, startPosition, endPosition), lastIndexedValue)
    }
}
