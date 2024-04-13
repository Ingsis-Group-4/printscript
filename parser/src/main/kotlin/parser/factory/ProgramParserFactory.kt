package parser.factory

import parser.AssignationParser
import parser.ExpressionParser
import parser.Parser
import parser.PrintLnParser
import parser.ProgramParser
import parser.StatementParser
import parser.VariableDeclarationParser
import parser.conditional.BlockParser
import parser.conditional.IfStatementParser
import parser.type.TypeProviderV1
import parser.type.TypeProviderV2
import token.TokenType
import version.Version

/**
 * Factory interface for creating parsers
 */
interface ParserFactory {
    /**
     * Creates a parser
     */
    fun create(version: Version = Version.V1): Parser
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

    override fun create(version: Version): Parser {
        return ProgramParser(parserSelector)
    }
}

object StatementParserFactory : ParserFactory {
    override fun create(version: Version): Parser {
        // Selector for parsers based on token type
        val parserSelector: Map<TokenType, Parser> =
            mapOf(
                TokenType.LET to VariableDeclarationParserFactory.create(version),
                TokenType.IDENTIFIER to AssignationParserFactory.create(version),
                TokenType.PRINTLN to PrintLnParserFactory.create(version),
            )

        val parserSelectorV2: Map<TokenType, Parser> =
            mapOf(
                TokenType.IF to IfStatementParserFactory.create(version),
            )

        if (version == Version.V2) {
            return StatementParser(parserSelector + parserSelectorV2)
        }

        return StatementParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the variable declaration
 */
object VariableDeclarationParserFactory : ParserFactory {
    override fun create(version: Version): Parser {
        val parserSelectorV1: Map<TokenType, Parser> =
            mapOf(
                TokenType.IDENTIFIER to ExpressionParserFactory.create(),
                TokenType.NUMBER to ExpressionParserFactory.create(),
                TokenType.STRING to ExpressionParserFactory.create(),
            )

        val parserSelectorV2: Map<TokenType, Parser> =
            mapOf(
                TokenType.BOOLEAN to ExpressionParserFactory.create(version),
                TokenType.READINPUT to ExpressionParserFactory.create(version),
                TokenType.READENV to ExpressionParserFactory.create(version),
            )

        if (version == Version.V2) {
            return VariableDeclarationParser(
                parserSelectorV1 + parserSelectorV2,
                TypeProviderV2,
            )
        }

        return VariableDeclarationParser(
            parserSelectorV1,
            TypeProviderV1,
        )
    }
}

/**
 * Factory to create a parser for the assignation
 */
object AssignationParserFactory : ParserFactory {
    /**
     * Selector for parsers based on token type
     */

    override fun create(version: Version): Parser {
        val parserSelector: Map<TokenType, Parser> =
            mapOf(
                TokenType.IDENTIFIER to ExpressionParserFactory.create(version),
                TokenType.NUMBER to ExpressionParserFactory.create(version),
                TokenType.STRING to ExpressionParserFactory.create(version),
            )

        val parserSelectorV2: Map<TokenType, Parser> =
            mapOf(
                TokenType.BOOLEAN to ExpressionParserFactory.create(version),
                TokenType.READINPUT to ExpressionParserFactory.create(version),
                TokenType.READENV to ExpressionParserFactory.create(version),
            )

        if (version == Version.V2) {
            return AssignationParser(parserSelector + parserSelectorV2)
        }

        return AssignationParser(parserSelector)
    }
}

/**
 * Factory to create a parser for the expression
 */
object ExpressionParserFactory : ParserFactory {
    override fun create(version: Version): Parser {
        if (version == Version.V2) {
            return ExpressionParser(
                DefaultOperationStrategyFactory,
                TokenHandlerFactoryV2,
            )
        }

        return ExpressionParser(
            DefaultOperationStrategyFactory,
            TokenHandlerFactoryV1,
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

    override fun create(version: Version): Parser {
        return PrintLnParser(parserSelector)
    }
}

object IfStatementParserFactory : ParserFactory {
    override fun create(version: Version): Parser {
        return IfStatementParser(BlockParserFactory.create(version))
    }
}

object BlockParserFactory : ParserFactory {
    private val parserSelector: Map<TokenType, Parser> =
        mapOf(
            TokenType.LET to VariableDeclarationParserFactory.create(),
            TokenType.IDENTIFIER to AssignationParserFactory.create(),
            TokenType.PRINTLN to PrintLnParserFactory.create(),
        )

    override fun create(version: Version): Parser {
        return BlockParser(parserSelector)
    }
}
