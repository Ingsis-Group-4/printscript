package org.example.parser

import ast.ProgramNode
import ast.StatementNode
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.result.FailureResult
import org.example.parser.utils.at
import org.example.parser.utils.getSyntaxSubtree
import org.example.parser.utils.isEndOfStatement
import token.Token
import token.TokenType


class ProgramParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        val statementTokens = divideIntoStatements(tokens)
        return parseStatements(statementTokens, currentIndex)
    }

    private fun divideIntoStatements(tokens: List<Token>): List<List<Token>> {
        val statements = mutableListOf<List<Token>>()
        var currentStatement = mutableListOf<Token>()

        for (token in tokens) {
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

    private fun parseStatements(statementTokens: List<List<Token>>, currentIndex: Int): ParserResult {
        val asts = mutableListOf<StatementNode>()
        var lastIndexedValue = currentIndex

        for (statement in statementTokens) {
            when (val result = parseStatement(statement, currentIndex)) {
                is SuccessResult -> {
                    lastIndexedValue = result.lastValidatedIndex
                    asts.add(result.value as StatementNode)
                }

                is FailureResult -> return result
            }
        }

        return buildParserResult(asts, lastIndexedValue)
    }

    private fun parseStatement(statement: List<Token>, currentIndex: Int): ParserResult {
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

    private fun buildParserResult(asts: List<StatementNode>, lastIndexedValue: Int): ParserResult {
        return SuccessResult(ProgramNode(asts), lastIndexedValue)
    }
}

