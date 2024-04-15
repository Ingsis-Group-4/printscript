package parser.conditional

import ast.BlockNode
import ast.StatementNode
import parser.Parser
import parser.exception.ParserException
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.consume
import parser.utils.getSyntaxSubtree
import parser.utils.isEndOfStatement
import position.Position
import token.Token
import token.TokenType

class BlockParser(
    private val parserSelector: Map<TokenType, Parser>,
) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        val statementTokens = divideIntoStatements(tokens.slice(currentIndex until tokens.size))
        val statementNodes = parseStatements(statementTokens)

        val lastTokenIndex = indexOfToken(tokens, TokenType.CLOSECURLY)
        val lastToken = at(tokens, lastTokenIndex)
        val firstToken = at(tokens, currentIndex)

        return buildParserResult(
            statementNodes,
            firstToken.start,
            lastTokenIndex,
            lastToken.end,
        )
    }

    private fun divideIntoStatements(tokens: List<Token>): List<List<Token>> {
        val openCurlyIndex = 0
        val firstStatementIndex = consume(tokens, openCurlyIndex, TokenType.OPENCURLY)

        val closeCurlyIndex = indexOfToken(tokens, TokenType.CLOSECURLY)

        val statements = mutableListOf<List<Token>>()
        var currentStatement = mutableListOf<Token>()

        val statementTokens = tokens.slice(firstStatementIndex until closeCurlyIndex)

        for (token in statementTokens) {
            if (isEndOfStatement(token)) {
                currentStatement.add(token)
                statements.add(currentStatement)
                currentStatement = mutableListOf()
            } else {
                currentStatement.add(token)
            }
        }
        return statements
    }

    private fun parseStatements(statementTokens: List<List<Token>>): List<StatementNode> {
        val statementNodes = mutableListOf<StatementNode>()

        for (statement in statementTokens) {
            when (val result = parseStatement(statement)) {
                is SuccessResult -> {
                    statementNodes.add(result.value as StatementNode)
                }

                is FailureResult -> throw ParserException(result.message)
            }
        }

        return statementNodes
    }

    private fun parseStatement(statement: List<Token>): ParserResult {
        return when (val result = getSyntaxSubtree(statement, 0, parserSelector)) {
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
        openCurlyPosition: Position,
        lastIndexedValue: Int,
        closeCurlyPosition: Position,
    ): ParserResult {
        return SuccessResult(BlockNode(openCurlyPosition, closeCurlyPosition, asts), lastIndexedValue)
    }

    private fun indexOfToken(
        tokens: List<Token>,
        type: TokenType,
    ): Int {
        for (i in tokens.indices) {
            if (tokens.get(i).type == type) {
                return i
            }
        }

        throw ParserException("Close curly brace missing for block")
    }
}
