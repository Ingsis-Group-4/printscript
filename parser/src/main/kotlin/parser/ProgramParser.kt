package parser

import ast.ProgramNode
import ast.StatementNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.getSyntaxSubtree
import parser.utils.isEndOfStatement
import parser.utils.isTokenValid
import token.Token
import token.TokenType

class ProgramParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
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

        for (token in tokens) {
            if (isTokenValid(token, TokenType.OPENCURLY)) {
                openBlock = true
            }
            if (isTokenValid(token, TokenType.CLOSECURLY)) {
                openBlock = false
            }
            if ((isEndOfStatement(token) && !openBlock) || isTokenValid(token, TokenType.CLOSECURLY)) {
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
