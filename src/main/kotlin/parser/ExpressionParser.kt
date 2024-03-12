package org.example.parser

import org.example.ast.AST
import org.example.ast.ExpressionNode
import org.example.ast.LiteralNode
import org.example.ast.SumNode
import org.example.position.Position
import org.example.token.Token
import org.example.token.TokenType

class ExpressionParser(private val parserSelector: Map<Token, Parser>): Parser {
    //TODO("por la recursividad, las operaciones se resuelven de derecha a izquierda, lo cual no es lo esperado.")
    override fun parse(tokens: List<Token>, currentIndex: Int): ExpressionNode {
        val token = next(tokens, currentIndex)
        when(token.type) {
            TokenType.NUMBER,
            TokenType.STRING,
            TokenType.IDENTIFIER -> return parseExpression(tokens, currentIndex+1)
            else -> throw Exception("Invalid expression at position ${token.start}")
        }
    }

    private fun parseExpression(tokens: List<Token>, currentIndex: Int): ExpressionNode {
        val nextToken = next(tokens, currentIndex)
        val token = at(tokens, currentIndex)
        return when(nextToken.type) {
            TokenType.SEMICOLON -> LiteralNode(token.value)
            TokenType.SUM -> SumNode(LiteralNode(token.value), parse(tokens, currentIndex + 1))
            //TODO("Implement other operators here!")
            else -> throw Exception("Invalid expression at position ${token.start}")
        }
    }

//    fun main(args: Array<String>) {
//        val tokens = listOf(
//            Token(TokenType.NUMBER, Position(0,0), Position(0,1), "1"),
//            Token(TokenType.SUM, Position(0,2), Position(0,3), "+"),
//            Token(TokenType.NUMBER, Position(0,4), Position(0,5), "2"),
//            Token(TokenType.SEMICOLON, Position(0,6), Position(0,7), ";")
//        )
//        val parser = ExpressionParser(mapOf())
//        val ast = parser.parse(tokens, 0)
//        println(ast)
//    }


}