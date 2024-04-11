package parser.factory

import parser.AssignationParser
import parser.ExpressionParser
import parser.Parser
import parser.PrintLnParser
import parser.ProgramParser
import parser.StatementParser
import parser.VariableDeclarationParser
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
    private val parserSelector: Map<TokenType, Parser> =
        mapOf(
            TokenType.LET to VariableDeclarationParserFactory.create(),
            TokenType.IDENTIFIER to AssignationParserFactory.create(),
            TokenType.PRINTLN to PrintLnParserFactory.create(),
        )

    override fun create(): Parser {
        return ProgramParser(parserSelector)
    }
}

object StatementParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> =
        mapOf(
            TokenType.LET to VariableDeclarationParserFactory.create(),
            TokenType.IDENTIFIER to AssignationParserFactory.create(),
            TokenType.PRINTLN to PrintLnParserFactory.create(),
        )

    override fun create(): Parser {
        return StatementParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the variable declaration
 */
object VariableDeclarationParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> =
        mapOf(
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
    private val parserSelector: Map<TokenType, Parser> =
        mapOf(
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
    override fun create(): Parser {
        return ExpressionParser(
            DefaultOperationStrategyFactory,
            DefaultTokenHandlerFactory,
        )
    }
}

/**
 * Factory to create a parser for the println statement
 */
object PrintLnParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */
    private val parserSelector: Map<TokenType, Parser> =
        mapOf(
            TokenType.IDENTIFIER to ExpressionParserFactory.create(),
            TokenType.NUMBER to ExpressionParserFactory.create(),
            TokenType.STRING to ExpressionParserFactory.create(),
        )

    override fun create(): Parser {
        return PrintLnParser(parserSelector)
    }
}
