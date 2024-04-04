package parser.factory

import parser.AssignationParser
import parser.ExpressionParser
import parser.ExpressionParserV2
import parser.IdentifierParser
import parser.NumberParser
import parser.Parser
import parser.ParserClassRegistry
import parser.PrintLnParser
import parser.ProgramParser
import parser.StringParser
import parser.SumParserClass
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
        return ExpressionParser()
    }
}

/**
 * Factory to create a parser for strings.
 * This factory creates a parser that can parse strings in the code.
 */
object StringParserFactory : ParserFactory {
    /**
     * Creates a StringParser.
     * @return a new StringParser instance.
     */
    override fun create(): Parser {
        return StringParser()
    }
}

/**
 * Factory to create a parser for identifiers.
 * This factory creates a parser that can parse identifiers in the code.
 */
object IdentifierParserFactory : ParserFactory {
    /**
     * Creates an IdentifierParser.
     * @return a new IdentifierParser instance.
     */
    override fun create(): Parser {
        return IdentifierParser()
    }
}

/**
 * Factory to create a parser for numbers.
 * This factory creates a parser that can parse numbers in the code.
 */
object NumberParserFactory : ParserFactory {
    /**
     * Creates a NumberParser.
     * @return a new NumberParser instance.
     */
    override fun create(): Parser {
        return NumberParser()
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

object ExpressionParserV2Factory : ParserFactory {
    override fun create(): Parser {
        val sumParserClass = SumParserClass

        // Register the ParserClass instances
        ParserClassRegistry.register(sumParserClass)

        // Retrieve all registered ParserClass instances
        val parserClasses = ParserClassRegistry.getParserClasses()
        val baseParser = ExpressionParserV2(parserClasses) // Aquí debes proporcionar una instancia de Parser
        return baseParser
    }
}
