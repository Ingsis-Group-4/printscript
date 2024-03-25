package org.example.parser

import ast.ExpressionNode
import ast.SumNode
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.*
import token.Token
import token.TokenType

class SumParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return parseOperation(tokens, currentIndex, ::isSum, ::buildParserResult, parserSelector)
    }

    private fun isSum(tokens: List< Token>, currentIndex: Int): Boolean {
        return at(tokens, currentIndex).type == TokenType.SUM
    }

    private fun buildParserResult(
        leftOperand: SuccessResult,
        rightOperand: SuccessResult,
        currentIndex: Int
    ): ParserResult {
        return SuccessResult(
            SumNode(
                leftOperand.value as ExpressionNode,
                rightOperand.value as ExpressionNode,
                start = leftOperand.value.getStart(),
                end = rightOperand.value.getEnd()
            ),
            currentIndex
        )
    }
}