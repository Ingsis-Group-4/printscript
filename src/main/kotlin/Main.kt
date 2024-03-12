package org.example

import org.example.parser.ExpressionParser
import org.example.parser.ProgramParser
import org.example.position.Position
import org.example.token.Token
import org.example.token.TokenType

fun main() {
    println("Hello World!")
    val tokens = listOf(
        Token(TokenType.ASSIGNATION, Position(0,0), Position(0,1), "="),
        Token(TokenType.NUMBER, Position(0,0), Position(0,1), "1"),
        Token(TokenType.SUM, Position(0,2), Position(0,3), "+"),
        Token(TokenType.NUMBER, Position(0,4), Position(0,5), "2"),
        Token(TokenType.SUM, Position(0,2), Position(0,3), "+"),
        Token(TokenType.NUMBER, Position(0,4), Position(0,5), "2"),
        Token(TokenType.SEMICOLON, Position(0,6), Position(0,7), ";")
    )
    val parser = ExpressionParser()
    val ast = parser.parse(tokens, 0)

    val tokens2 = listOf(
        Token(TokenType.LET, Position(0,0), Position(0,1), "let"),
        Token(TokenType.IDENTIFIER, Position(0,2), Position(0,3), "a"),
        Token(TokenType.COLON, Position(0,4), Position(0,5), ":"),
        Token(TokenType.NUMBERTYPE, Position(0,6), Position(0,7), "number"),
        Token(TokenType.ASSIGNATION, Position(0,8), Position(0,9), "="),
        Token(TokenType.NUMBER, Position(0,10), Position(0,11), "1"),
        Token(TokenType.SUM, Position(0,12), Position(0,13), "+"),
        Token(TokenType.NUMBER, Position(0,14), Position(0,15), "2"),
        Token(TokenType.SUM, Position(0,16), Position(0,17), "+"),
        Token(TokenType.NUMBER, Position(0,18), Position(0,19), "2"),
        Token(TokenType.SEMICOLON, Position(0,20), Position(0,21), ";")
    )

    val tokens3 = listOf(
        Token(TokenType.LET, Position(0,0), Position(0,1), "let"),
        Token(TokenType.IDENTIFIER, Position(0,2), Position(0,3), "a"),
        Token(TokenType.COLON, Position(0,4), Position(0,5), ":"),
        Token(TokenType.NUMBERTYPE, Position(0,6), Position(0,7), "number"),
        Token(TokenType.SEMICOLON, Position(0,20), Position(0,21), ";")
    )
    val parser2 = ProgramParser(mapOf())
    val ast2 = parser2.parse(tokens3, 0)
    println(ast2)
}