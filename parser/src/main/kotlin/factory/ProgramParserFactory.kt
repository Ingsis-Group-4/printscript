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
    private val operatorSelector: Map<TokenType, Parser> = mapOf(
        TokenType.SUM to SumParserFactory.create(),
//        TokenType.SUBTRACTION to SubtractionParserFactory.create(),
//        TokenType.MULTIPLICATION to MultiplicationParserFactory.create(),
//        TokenType.DIVISION to DivisionParserFactory.create(),
    )

    private val operandSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to IdentifierParserFactory.create(),
        TokenType.NUMBER to NumberParserFactory.create(),
        TokenType.STRING to StringParserFactory.create(),
    )

    override fun create(): Parser {
        return ExpressionParser(operatorSelector, operandSelector)
    }
}

/**
 * Factory to create a parser for the sum operation.
 * This factory creates a parser that can parse sum operations in the code.
 * The parser is created with a selector that maps token types to their respective parsers.
 */
object SumParserFactory : ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to IdentifierParserFactory.create(),
        TokenType.NUMBER to NumberParserFactory.create(),
        TokenType.STRING to StringParserFactory.create(),
    )

    /**
     * Creates a SumParser with the defined parser selector.
     * @return a new SumParser instance.
     */
    override fun create(): Parser {
        return SumParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the subtraction operation.
 * This factory creates a parser that can parse subtraction operations in the code.
 * The parser is created with a selector that maps token types to their respective parsers.
 */
object SubtractionParserFactory : ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to IdentifierParserFactory.create(),
        TokenType.NUMBER to NumberParserFactory.create(),
        TokenType.STRING to StringParserFactory.create(),
    )

    /**
     * Creates a SubtractionParser with the defined parser selector.
     * @return a new SubtractionParser instance.
     */
    override fun create(): Parser {
        return SubtractionParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the multiplication operation.
 * This factory creates a parser that can parse multiplication operations in the code.
 * The parser is created with a selector that maps token types to their respective parsers.
 */
object MultiplicationParserFactory : ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to IdentifierParserFactory.create(),
        TokenType.NUMBER to NumberParserFactory.create(),
        TokenType.STRING to StringParserFactory.create(),
    )

    /**
     * Creates a MultiplicationParser with the defined parser selector.
     * @return a new MultiplicationParser instance.
     */
    override fun create(): Parser {
        return MultiplicationParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the division operation.
 * This factory creates a parser that can parse division operations in the code.
 * The parser is created with a selector that maps token types to their respective parsers.
 */
object DivisionParserFactory : ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to IdentifierParserFactory.create(),
        TokenType.NUMBER to NumberParserFactory.create(),
        TokenType.STRING to StringParserFactory.create(),
    )

    /**
     * Creates a DivisionParser with the defined parser selector.
     * @return a new DivisionParser instance.
     */
    override fun create(): Parser {
        return DivisionParser(parserSelector)
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
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.IDENTIFIER to ExpressionParserFactory.create(),
        TokenType.NUMBER to ExpressionParserFactory.create(),
        TokenType.STRING to ExpressionParserFactory.create(),
    )

    override fun create(): Parser {
        return PrintLnParser(parserSelector)
    }
}
