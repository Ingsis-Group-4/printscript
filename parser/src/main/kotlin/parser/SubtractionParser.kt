package parser

import ast.ExpressionNode
import ast.SubtractionNode
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.parseOperation
import token.Token
import token.TokenType

class SubtractionParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return parseOperation(tokens, currentIndex, ::isSubtraction, ::buildParserResult, parserSelector)
    }

    private fun isSubtraction(
        tokens: List<Token>,
        currentIndex: Int,
    ): Boolean {
        return at(tokens, currentIndex).type == TokenType.SUBTRACTION
    }

    private fun buildParserResult(
        leftOperand: SuccessResult,
        rightOperand: SuccessResult,
        currentIndex: Int,
    ): ParserResult {
        return SuccessResult(
            SubtractionNode(
                leftOperand.value as ExpressionNode,
                rightOperand.value as ExpressionNode,
                start = leftOperand.value.getStart(),
                end = rightOperand.value.getEnd(),
            ),
            currentIndex,
        )
    }
}
