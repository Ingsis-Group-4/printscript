package org.example.parser

import org.example.ast.*
import org.example.parser.expression.DefaultOperandValidator
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import org.example.token.Token
import org.example.token.TokenType

class ExpressionParser(private val parserSelector: Map<TokenType, Parser>): Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        if (isOperand(tokens, currentIndex)) {
            val operand = at(tokens, currentIndex).value
            return SuccessResult(LiteralNode(operand), currentIndex)
        }
        return FailureResult("Invalid operand at position ${at(tokens, currentIndex).start}", currentIndex)
    }

    private fun isOperand(tokens: List<Token>, currentIndex: Int): Boolean {
        val token = at(tokens, currentIndex)
        return DefaultOperandValidator.isValid(token)
    }
}










//    //TODO("por la recursividad, las operaciones se resuelven de derecha a izquierda, lo cual no es lo esperado.")
//    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
//        val token = at(tokens, currentIndex)
//        if (token.type == TokenType.SEMICOLON) {
//            return FailureResult("Invalid token at position ${token.start}", currentIndex)
//        }
//        when (val result = getSyntaxSubtree(token, tokens, currentIndex, parserSelector)) {
//            is FailureResult -> {
//                val expression = result as ExpressionNode
//                val nextToken = at(tokens, result.lastValidatedIndex)
//                if (nextToken.type == TokenType.SEMICOLON) {
//                    return SuccessResult(expression, nextIndex(result.lastValidatedIndex))
//                }
//                return FailureResult("Expected semicolon at position ${nextToken.start}", result.lastValidatedIndex)
//            }
//
//            else -> return FailureResult("Invalid result", currentIndex)
//        }
//        return parseExpression2(tokens, currentIndex)
//        val token = next(tokens, currentIndex)
//        return when(token.type) {
//            TokenType.NUMBER,
//            TokenType.STRING,
//            TokenType.IDENTIFIER -> parseExpression(tokens, nextIndex(currentIndex))
//            else -> Pair(null, ParserState(ErrorStatus("Invalid token at position ${token.start}"), currentIndex))
//        }
//    }

    //    private fun parseExpression(tokens: List<Token>, currentIndex: Int): Pair<AST?, ParserState> {
//        val nextToken = next(tokens, currentIndex)
//        val token = at(tokens, currentIndex)
//        return when(nextToken.type) {
//            TokenType.SEMICOLON,
//            TokenType.CLOSEPARENTHESIS -> Pair(LiteralNode(token.value), ParserState(SuccessStatus(), nextIndex(currentIndex)))
//            TokenType.SUM -> {
//                val nextExpression = parse(tokens, nextIndex(currentIndex))
//                Pair(
//                    SumNode(LiteralNode(token.value), nextExpression.first as ExpressionNode),
//                    ParserState(SuccessStatus(), nextExpression.second.lastValidatedIndex)
//                )
//            }
//            else -> Pair(null, ParserState(ErrorStatus("Invalid token at position ${nextToken.start}"), currentIndex))
//        }
//    }
//
//    private fun parseExpression2(tokens: List<Token>, currentIndex: Int): Pair<AST?, ParserState> {
//        val token = at(tokens, currentIndex)
//        return when (token.type) {
//            TokenType.NUMBER,
//            TokenType.STRING,
//            TokenType.IDENTIFIER -> Pair(LiteralNode(token.value), ParserState(SuccessStatus(), nextIndex(currentIndex)))
//            TokenType.OPENPARENTHESIS -> {
//                val (expression, nextState) = parse(tokens, nextIndex(currentIndex))
//                if (nextState.status is ErrorStatus) {
//                    return Pair(null, nextState)
//                }
//                val closingToken = at(tokens, nextState.lastValidatedIndex)
//                if (closingToken.type != TokenType.CLOSEPARENTHESIS) {
//                    return Pair(null, ParserState(ErrorStatus("Expected closing parenthesis"), nextState.lastValidatedIndex))
//                }
//                Pair(expression, ParserState(SuccessStatus(), nextIndex(nextState.lastValidatedIndex)))
//            }
//            TokenType.SUM,
//            TokenType.SUBTRACTION,
//            TokenType.MULTIPLICATION,
//            TokenType.DIVISION -> {
//                val nextExpression = parse(tokens, nextIndex(currentIndex))
//                val operatorToken = at(tokens, currentIndex)
//                val operator = when (operatorToken.type) {
//                    TokenType.SUM -> SumNode(LiteralNode(token.value), nextExpression.first as ExpressionNode)
//                    TokenType.SUBTRACTION -> SubtractionNode(LiteralNode(token.value), nextExpression.first as ExpressionNode)
//                    TokenType.MULTIPLICATION -> ProductNode(LiteralNode(token.value), nextExpression.first as ExpressionNode)
//                    TokenType.DIVISION -> DivisionNode(LiteralNode(token.value), nextExpression.first as ExpressionNode)
//                    else -> null
//                }
//                if (operator == null) {
//                    return Pair(null, ParserState(ErrorStatus("Invalid operator at position ${operatorToken.start}"), currentIndex))
//                }
//                Pair(operator, ParserState(SuccessStatus(), nextExpression.second.lastValidatedIndex))
//            }
//            else -> Pair(null, ParserState(ErrorStatus("Invalid token at position ${token.start}"), currentIndex))
//        }
//    }


