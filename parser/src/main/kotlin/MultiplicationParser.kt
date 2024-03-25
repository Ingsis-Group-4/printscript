package org.example.parser

import ast.ExpressionNode
import ast.ProductNode
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import org.example.parser.utils.parseOperation
import token.Token
import token.TokenType

class MultiplicationParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return parseOperation(tokens, currentIndex, ::isProduct, ::buildParserResult, parserSelector)
    }

    private fun isProduct(tokens: List< Token>, currentIndex: Int): Boolean {
        return at(tokens, currentIndex).type == TokenType.MULTIPLICATION
    }

    private fun buildParserResult(
        leftOperand: SuccessResult,
        rightOperand: SuccessResult,
        currentIndex: Int
    ): ParserResult {
        return SuccessResult(ProductNode(leftOperand.value as ExpressionNode, rightOperand.value as ExpressionNode), currentIndex)
    }
}