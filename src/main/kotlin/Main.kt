package org.example

import org.example.parser.ExpressionParser
import org.example.parser.result.SuccessResult
import org.example.position.Position
import org.example.token.Token
import org.example.token.TokenType

fun main() {
    println("Hello World!")

    //let a:number = 1+2+2;
    val tokens2 = listOf(
        Token(TokenType.LET, Position(0, 0), Position(0, 1), "let"),
        Token(TokenType.IDENTIFIER, Position(0, 2), Position(0, 3), "a"),
        Token(TokenType.COLON, Position(0, 4), Position(0, 5), ":"),
        Token(TokenType.NUMBERTYPE, Position(0, 6), Position(0, 7), "number"),
        Token(TokenType.ASSIGNATION, Position(0, 8), Position(0, 9), "="),
        Token(TokenType.NUMBER, Position(0, 10), Position(0, 11), "1"),
        Token(TokenType.SUM, Position(0, 12), Position(0, 13), "+"),
        Token(TokenType.NUMBER, Position(0, 14), Position(0, 15), "2"),
        Token(TokenType.SUM, Position(0, 16), Position(0, 17), "+"),
        Token(TokenType.NUMBER, Position(0, 18), Position(0, 19), "2"),
        Token(TokenType.SEMICOLON, Position(0, 20), Position(0, 21), ";")
    )

    // let a:number;
    // VariableDeclarationNode(IdentifierNode(a, number), null)
    val tokens3 = listOf(
        Token(TokenType.LET, Position(0, 0), Position(0, 1), "let"),
        Token(TokenType.IDENTIFIER, Position(0, 2), Position(0, 3), "a"),
        Token(TokenType.COLON, Position(0, 4), Position(0, 5), ":"),
        Token(TokenType.NUMBERTYPE, Position(0, 6), Position(0, 7), "number"),
        Token(TokenType.SEMICOLON, Position(0, 20), Position(0, 21), ";")
    )

    // println(2 + 2)
    val tokens4 = listOf(
        Token(TokenType.PRINTLN, Position(0, 0), Position(0, 7), "println"),
        Token(TokenType.OPENPARENTHESIS, Position(0, 8), Position(0, 9), "("),
        Token(TokenType.NUMBER, Position(0, 10), Position(0, 11), "2"),
        Token(TokenType.SUM, Position(0, 12), Position(0, 13), "+"),
        Token(TokenType.NUMBER, Position(0, 14), Position(0, 15), "2"),
        Token(TokenType.CLOSEPARENTHESIS, Position(0, 16), Position(0, 17), ")"),
        Token(TokenType.SEMICOLON, Position(0, 18), Position(0,19), ";")

    )


//    val parser2 = PrintScriptParser.create()
//    val ast2 = parser2.parse(tokens2, 0)
//    val ast3 = parser2.parse(tokens3, 0)
//    val ast4 = parser2.parse(tokens4, 0)
//    println(ast2.second.status)
//    println(ast3.second.status)
//    println(ast4.second.status)

    println("///////////////////////////////////////////////////////////////////////////")


    val tokensSum = listOf(
        Token(TokenType.NUMBER, Position(0,0), Position(0,1), "1"),
        Token(TokenType.SUM, Position(0,2), Position(0,3), "+"),
        Token(TokenType.NUMBER, Position(0,4), Position(0,5), "2"),
        Token(TokenType.SEMICOLON, Position(0,6), Position(0,7), ";")
    )

    val tokensSubtraction = listOf(
        Token(TokenType.NUMBER, Position(0,0), Position(0,1), "5"),
        Token(TokenType.SUBTRACTION, Position(0,2), Position(0,3), "-"),
        Token(TokenType.NUMBER, Position(0,4), Position(0,5), "3"),
        Token(TokenType.SEMICOLON, Position(0,6), Position(0,7), ";")
    )

    val tokensMultiplication = listOf(
        Token(TokenType.NUMBER, Position(0,0), Position(0,1), "4"),
        Token(TokenType.MULTIPLICATION, Position(0,2), Position(0,3), "*"),
        Token(TokenType.NUMBER, Position(0,4), Position(0,5), "6"),
        Token(TokenType.SEMICOLON, Position(0,6), Position(0,7), ";")
    )

    val tokensDivision = listOf(
        Token(TokenType.NUMBER, Position(0,0), Position(0,1), "8"),
        Token(TokenType.DIVISION, Position(0,2), Position(0,3), "/"),
        Token(TokenType.NUMBER, Position(0,4), Position(0,5), "2"),
        Token(TokenType.SEMICOLON, Position(0,6), Position(0,7), ";")
    )

    val parser = ExpressionParser(mapOf())

    val astSum = parser.parse(tokensSum, 0) is SuccessResult
    println("AST para la suma: $astSum")

    val astSubtraction = parser.parse(tokensSubtraction, 0) is SuccessResult
    println("AST para la resta: $astSubtraction")

    val astMultiplication = parser.parse(tokensMultiplication, 0) is SuccessResult
    println("AST para la multiplicación: $astMultiplication")

    val astDivision = parser.parse(tokensDivision, 0) is SuccessResult
    println("AST para la división: $astDivision")

    }