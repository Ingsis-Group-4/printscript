package org.example.parser

import org.example.ast.*
import org.example.token.Token
import org.example.token.TokenType

class DeclarationParser: Parser {
//    override fun parse(tokens: List<Token>, currentIndex: Int): AST {
//        val token = next(tokens, currentIndex)
//        if (token.type == TokenType.IDENTIFIER) {
//            val identifier = token.value
//            val nextToken = next(tokens, currentIndex + 1)
//            if (nextToken.type == TokenType.COLON) {
//                val type = next(tokens, currentIndex + 2)
//                when (type.type) {
//                    TokenType.NUMBERTYPE,
//                    TokenType.STRINGTYPE -> {
//                        val symbol = next(tokens, currentIndex + 3)
//                        return when(symbol.type) {
//                            TokenType.SEMICOLON -> VariableDeclarationNode(IdentifierNode(identifier, getType(type.type)), null)
//                            TokenType.ASSIGNATION -> {
//                                val expression = ExpressionParser().parse(tokens, currentIndex + 4)
//                                VariableDeclarationNode(IdentifierNode(identifier, getType(type.type)), expression)
//                            }
//
//                            else -> throw Exception("Invalid symbol at position ${symbol.start}")
//                        }
//                    }
//                    else -> throw Exception("Invalid type at position ${type.start}")
//                }
////                    return DeclarationNode(identifier, type.value)
//            }
//        }
//        throw Exception("Invalid token at position ${token.start}")
//    }
    override fun parse(tokens: List<Token>, currentIndex: Int): AST {
        val token = next(tokens, currentIndex)
        if (token.type == TokenType.IDENTIFIER) {
            val identifier = token.value
            val nextToken = next(tokens, currentIndex + 1)
            if (nextToken.type == TokenType.COLON) {
                return parseDeclaration(tokens, currentIndex, identifier)
            }
        }
        throw Exception("Invalid token at position ${token.start}")
    }

    private fun parseDeclaration(tokens: List<Token>, currentIndex: Int, identifier: String): AST {
        val type = next(tokens, currentIndex + 2)
        return when (type.type) {
            TokenType.NUMBERTYPE,
            TokenType.STRINGTYPE -> {
                val identifierNode = IdentifierNode(identifier, getType(type.type))
                handleSymbol(tokens, currentIndex + 3, identifierNode)
            }
            else -> throw Exception("Invalid type at position ${type.start}")
        }
    }

    private fun handleSymbol(tokens: List<Token>, currentIndex: Int, identifierNode: IdentifierNode): AST {
        val symbol = next(tokens, currentIndex)
        return when (symbol.type) {
            TokenType.SEMICOLON -> VariableDeclarationNode(identifierNode, null)
            TokenType.ASSIGNATION -> parseVariableAssignment(tokens, currentIndex + 1, identifierNode)
            else -> throw Exception("Invalid symbol at position ${symbol.start}")
        }
    }

    private fun parseType(tokens: List<Token>, currentIndex: Int): AST {
        val typeToken = next(tokens, currentIndex)
        return when (typeToken.type) {
            TokenType.NUMBERTYPE, TokenType.STRINGTYPE -> IdentifierNode(typeToken.value, null) // Assuming IdentifierNode is used for type as well
            else -> throw Exception("Invalid type at position ${typeToken.start}")
        }
    }

    private fun parseVariableAssignment(tokens: List<Token>, currentIndex: Int, identifierNode: IdentifierNode): AST {
        val expression = parseExpression(tokens, currentIndex)
        return VariableDeclarationNode(identifierNode, expression)
    }


    private fun parseExpression(tokens: List<Token>, currentIndex: Int): ExpressionNode {
        return ExpressionParser().parse(tokens, currentIndex)
    }


    private fun getType(type: TokenType): VariableType {
        return when (type) {
            TokenType.NUMBERTYPE -> VariableType.NUMBER
            TokenType.STRINGTYPE -> VariableType.STRING
            else -> throw Exception("Invalid type")
        }
    }



}