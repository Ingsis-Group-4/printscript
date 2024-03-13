package org.example.parser

import org.example.ast.AST
import org.example.ast.ExpressionNode
import org.example.ast.LiteralNode
import org.example.ast.SumNode
import org.example.parser.status.ErrorStatus
import org.example.parser.status.SuccessStatus
import org.example.position.Position
import org.example.token.Token
import org.example.token.TokenType

//private val parserSelector: Map<Token, Parser>
class ExpressionParser(private val parserSelector: Map<TokenType, Parser>): Parser {
    //TODO("por la recursividad, las operaciones se resuelven de derecha a izquierda, lo cual no es lo esperado.")
    override fun parse(tokens: List<Token>, currentIndex: Int): Pair<AST?, ParserState> {
        val token = next(tokens, currentIndex)
        return when(token.type) {
            TokenType.NUMBER,
            TokenType.STRING,
            TokenType.IDENTIFIER -> parseExpression(tokens, nextIndex(currentIndex))
            else -> Pair(null, ParserState(ErrorStatus("Invalid token at position ${token.start}"), currentIndex))
        }
    }

    private fun parseExpression(tokens: List<Token>, currentIndex: Int): Pair<AST?, ParserState> {
        val nextToken = next(tokens, currentIndex)
        val token = at(tokens, currentIndex)
        return when(nextToken.type) {
            TokenType.SEMICOLON -> Pair(LiteralNode(token.value), ParserState(SuccessStatus(), nextIndex(currentIndex)))
            TokenType.SUM -> Pair(SumNode(LiteralNode(token.value), parse(tokens, nextIndex(currentIndex)).first as ExpressionNode), ParserState(SuccessStatus(), currentIndex + 2))
            //TODO("Implement other operators here!")
            else -> Pair(null, ParserState(ErrorStatus("Invalid token at position ${nextToken.start}"), currentIndex))
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