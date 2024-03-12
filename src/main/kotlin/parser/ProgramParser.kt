package org.example.parser

import org.example.ast.AST
import org.example.token.Token
import org.example.token.TokenType

class ProgramParser(parserSelector: Map<Token, Parser>): Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): AST {
        val token = at(tokens, currentIndex)
        return when(token.type) {
            TokenType.LET -> DeclarationParser().parse(tokens, currentIndex)
            TokenType.IDENTIFIER -> AssignationParser().parse(tokens, currentIndex)
//            TokenType.PRINTLN -> {}
            else -> throw Exception("Invalid program at position ${token.start}")
        }
    }

}