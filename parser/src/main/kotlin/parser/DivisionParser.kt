package parser

import ast.DivisionNode
import ast.ExpressionNode
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.parseOperation
import token.Token
import token.TokenType

class DivisionParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return parseOperation(tokens, currentIndex, ::isDivision, ::buildParserResult, parserSelector)
    }

    private fun isDivision(
        tokens: List<Token>,
        currentIndex: Int,
    ): Boolean {
        return at(tokens, currentIndex).type == TokenType.DIVISION
    }

    private fun buildParserResult(
        leftOperand: SuccessResult,
        rightOperand: SuccessResult,
        currentIndex: Int,
    ): ParserResult {
        return SuccessResult(
            DivisionNode(
                leftOperand.value as ExpressionNode,
                rightOperand.value as ExpressionNode,
                start = leftOperand.value.getStart(),
                end = rightOperand.value.getEnd(),
            ),
            currentIndex,
        )
    }
}
