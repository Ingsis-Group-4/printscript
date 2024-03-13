package org.example.parser

import org.example.ast.*
import org.example.token.Token
import org.example.token.TokenType

class PrintLnParser(private val parserSelector: Map<TokenType, Parser>): Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): Pair<AST, ParserState> {
        val token = next(tokens, currentIndex)
        val nestedParseResult = getNestedParserResult(token, tokens, currentIndex, parserSelector)
        val ast = PrintLnNode(nestedParseResult.first as ExpressionNode)
        val finalState = nestedParseResult.second
        return Pair(ast, finalState)
    }
}