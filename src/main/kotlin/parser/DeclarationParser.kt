package org.example.parser

import org.example.ast.AST
import org.example.ast.IdentifierNode
import org.example.ast.VariableDeclarationNode
import org.example.ast.VariableType
import org.example.token.Token
import org.example.token.TokenType

class DeclarationParser: Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): AST {
        val token = next(tokens, currentIndex)
        if (token.type == TokenType.IDENTIFIER) {
            val identifier = token.value
            val nextToken = next(tokens, currentIndex + 1)
            if (nextToken.type == TokenType.COLON) {
                val type = next(tokens, currentIndex + 2)
                when (type.type) {
                    TokenType.NUMBERTYPE,
                    TokenType.STRINGTYPE -> {
                        val symbol = next(tokens, currentIndex + 3)
                        return when(symbol.type) {
                            TokenType.SEMICOLON -> VariableDeclarationNode(IdentifierNode(identifier, getType(type.type)), null)
                            TokenType.ASSIGNATION -> {
                                val expression = ExpressionParser().parse(tokens, currentIndex + 4)
                                VariableDeclarationNode(IdentifierNode(identifier, getType(type.type)), expression)
                            }

                            else -> throw Exception("Invalid symbol at position ${symbol.start}")
                        }
                    }
                    else -> throw Exception("Invalid type at position ${type.start}")
                }
//                    return DeclarationNode(identifier, type.value)
            }
        }
        throw Exception("Invalid token at position ${token.start}")
    }

    private fun getType(type: TokenType): VariableType {
        return when (type) {
            TokenType.NUMBERTYPE -> VariableType.NUMBER
            TokenType.STRINGTYPE -> VariableType.STRING
            else -> throw Exception("Invalid type")
        }
    }

}