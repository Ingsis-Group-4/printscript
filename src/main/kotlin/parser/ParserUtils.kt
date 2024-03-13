package org.example.parser

import org.example.ast.AST
import org.example.parser.status.ErrorStatus
import org.example.token.Token
import org.example.token.TokenType

fun next(tokens: List<Token>, index: Int): Token {
    return tokens[index + 1]
}

fun at(tokens: List<Token>, index: Int): Token {
    return tokens[index]
}

fun isEndOfProgram(tokens: List<Token>, index: Int): Boolean {
    return index >= tokens.size
}

fun getNestedParserResult(token: Token, tokens: List<Token>, currentIndex: Int, parserSelector: Map<TokenType, Parser>): Pair<AST?, ParserState> {
    val errorStatus = ErrorStatus("Invalid token at position ${token.start}")
    return parserSelector[token.type]?.parse(tokens, currentIndex)
        ?: Pair(null, ParserState(errorStatus, currentIndex))
}

fun nextIndex(currentIndex: Int, step: Int = 1): Int {
    return currentIndex + step
}