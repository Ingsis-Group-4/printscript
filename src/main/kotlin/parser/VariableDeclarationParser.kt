package org.example.parser

import org.example.ast.*
import org.example.parser.status.ErrorStatus
import org.example.parser.status.SuccessStatus
import org.example.token.Token
import org.example.token.TokenType

class VariableDeclarationParser(private val parserSelector: Map<TokenType, Parser>): Parser {

    override fun parse(tokens: List<Token>, currentIndex: Int): Pair<AST?, ParserState> {
        val token = next(tokens, currentIndex)
        if (token.type == TokenType.IDENTIFIER) {
            val identifier = token.value
            val nextToken = next(tokens, nextIndex(currentIndex))
            if (nextToken.type == TokenType.COLON) {
                return parseDeclaration(tokens, currentIndex, identifier)
            }
        }
        return Pair(null, ParserState(ErrorStatus("Invalid token at position ${token.start}"), currentIndex))
    }

    private fun parseDeclaration(tokens: List<Token>, currentIndex: Int, identifier: String): Pair<AST?, ParserState> {
        val type = next(tokens, nextIndex(currentIndex, 2))
        return when (type.type) {
            TokenType.NUMBERTYPE,
            TokenType.STRINGTYPE -> {
                val identifierNode = IdentifierNode(identifier, getType(type.type))
                handleSymbol(tokens, nextIndex(currentIndex, 3), identifierNode)
            }
            else -> throw Exception("Invalid type at position ${type.start}")
        }
    }

    private fun handleSymbol(tokens: List<Token>, currentIndex: Int, identifierNode: IdentifierNode): Pair<AST?, ParserState> {
        val symbol = next(tokens, currentIndex)
        return when (symbol.type) {
            TokenType.SEMICOLON -> {
                val ast = VariableDeclarationNode(identifierNode, null)
                return Pair(ast, ParserState(SuccessStatus(), nextIndex(currentIndex)))
            }
            // aca recien se puede dividir
//            TokenType.ASSIGNATION -> parseVariableAssignment(tokens, currentIndex + 1, identifierNode)
//            else -> Pair(null, ParserState(ErrorStatus("Invalid token at position ${symbol.start}"), currentIndex))
            else -> {
                val result = getNestedParserResult(symbol, tokens, nextIndex(currentIndex), parserSelector)
                val ast = VariableDeclarationNode(identifierNode, result.first as ExpressionNode)
                Pair(ast, result.second)
            }
        }
    }

//    private fun parseVariableAssignment(tokens: List<Token>, currentIndex: Int, identifierNode: IdentifierNode): Pair<AST?, ParserState> {
//        val result = parseExpression(tokens, currentIndex)
//        val ast = VariableDeclarationNode(identifierNode, result.first as ExpressionNode)
//        return Pair(ast, result.second)
//    }
//
//
//    private fun parseExpression(tokens: List<Token>, currentIndex: Int): Pair<AST?, ParserState> {
//        return ExpressionParser().parse(tokens, currentIndex)
//    }


    private fun getType(type: TokenType): VariableType {
        return when (type) {
            TokenType.NUMBERTYPE -> VariableType.NUMBER
            TokenType.STRINGTYPE -> VariableType.STRING
            else -> throw Exception("Invalid type")
        }
    }



}