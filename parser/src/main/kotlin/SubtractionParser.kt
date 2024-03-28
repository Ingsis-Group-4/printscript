package org.example.parser

import ast.ExpressionNode
import ast.SubtractionNode
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.at
import org.example.parser.utils.parseOperation
import token.Token
import token.TokenType

class SubtractionParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        return parseOperation(tokens, currentIndex, ::isSubtraction, ::buildParserResult, parserSelector)
    }

    private fun isSubtraction(tokens: List< Token>, currentIndex: Int): Boolean {
        return at(tokens, currentIndex).type == TokenType.SUBTRACTION
    }

    private fun buildParserResult(
        leftOperand: SuccessResult,
        rightOperand: SuccessResult,
        currentIndex: Int
    ): ParserResult {
        return SuccessResult(
            SubtractionNode(
                leftOperand.value as ExpressionNode,
                rightOperand.value as ExpressionNode,
                start = leftOperand.value.getStart(),
                end = rightOperand.value.getEnd()
            ),
            currentIndex
        )
    }
}