package org.example.parser.factory

import org.example.parser.*
import token.TokenType

/**
 * Factory interface for creating parsers
 */
interface ParserFactory {
    /**
     * Creates a parser
     */
    fun create(): Parser
}

/**
 * Factory to create a parser for the program
 */
object ProgramParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.LET to VariableDeclarationParserFactory.create(),
        TokenType.IDENTIFIER to AssignationParserFactory.create(),
        TokenType.PRINTLN to PrintLnParserFactory.create()
    )

    override fun create(): Parser {
        return ProgramParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the variable declaration
 */
object VariableDeclarationParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to ExpressionParserFactory.create(),
        TokenType.NUMBER to ExpressionParserFactory.create(),
        TokenType.STRING to ExpressionParserFactory.create(),
    )

    override fun create(): Parser {
        return VariableDeclarationParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the assignation
 */
object AssignationParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to ExpressionParserFactory.create(),
        TokenType.NUMBER to ExpressionParserFactory.create(),
        TokenType.STRING to ExpressionParserFactory.create(),
    )

    override fun create(): Parser {
        return AssignationParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the expression
 */
object ExpressionParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> = mapOf()

    override fun create(): Parser {
        return ExpressionParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the println statement
 */
object PrintLnParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to ExpressionParserFactory.create(),
        TokenType.NUMBER to ExpressionParserFactory.create(),
        TokenType.STRING to ExpressionParserFactory.create(),
    )

    override fun create(): Parser {
        return PrintLnParser(parserSelector)
    }
}
