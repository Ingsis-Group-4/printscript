package parser

import ast.ExpressionNode
import ast.ProductNode
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.parseOperation
import token.Token
import token.TokenType

class MultiplicationParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return parseOperation(tokens, currentIndex, ::isProduct, ::buildParserResult, parserSelector)
    }

    private fun isProduct(
        tokens: List<Token>,
        currentIndex: Int,
    ): Boolean {
        return at(tokens, currentIndex).type == TokenType.MULTIPLICATION
    }

    private fun buildParserResult(
        leftOperand: SuccessResult,
        rightOperand: SuccessResult,
        currentIndex: Int,
    ): ParserResult {
        return SuccessResult(
            ProductNode(
                leftOperand.value as ExpressionNode,
                rightOperand.value as ExpressionNode,
                start = leftOperand.value.getStart(),
                end = rightOperand.value.getEnd(),
            ),
            currentIndex,
        )
    }
}
