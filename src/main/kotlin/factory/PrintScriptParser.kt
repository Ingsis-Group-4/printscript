package org.example.factory

import org.example.parser.*
import org.example.token.TokenType

object PrintScriptParser: ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.LET to VariableDeclarationParserFactory.create(),
        TokenType.IDENTIFIER to AssignationParserFactory.create()
    )

    override fun create(): Parser {
        return ProgramParser(parserSelector)
    }

}

object AssignationParserFactory: ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.ASSIGNATION to ExpressionParserFactory.create()
    )

    override fun create(): Parser {
        return AssignationParser(parserSelector)
    }
}

object VariableDeclarationParserFactory: ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        TokenType.ASSIGNATION to ExpressionParserFactory.create()
    )

    override fun create(): Parser {
        return VariableDeclarationParser(parserSelector)
    }
}

object ExpressionParserFactory: ParserFactory {
    private val parserSelector: Map<TokenType, Parser> = mapOf(
        //TODO: Add the rest of the parsers
    )

    override fun create(): Parser {
        return ExpressionParser(parserSelector)
    }
}