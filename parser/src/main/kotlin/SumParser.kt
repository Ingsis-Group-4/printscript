package org.example.parser

import ast.AST
import ast.ExpressionNode
import ast.SumNode
import org.example.parser.result.FailureResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.utils.*
import token.Token
import token.TokenType

class SumParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        if (!isSum(tokens, currentIndex)) {
            return FailureResult("Invalid sum at position ${at(tokens, currentIndex).start}", currentIndex)
        }
        val leftOperandIndex = prevIndex(currentIndex)
        val leftOperand = getSyntaxSubtree(tokens, leftOperandIndex, parserSelector)
        val rightOperandIndex = nextIndex(currentIndex)
        val rightOperand = getSyntaxSubtree(tokens, rightOperandIndex, parserSelector)
        if (leftOperand is SuccessResult && rightOperand is SuccessResult) {
            return buildParserResult(leftOperand, rightOperand, rightOperand.lastValidatedIndex)
        }
        return FailureResult("Invalid sum at position ${at(tokens, currentIndex).start}", currentIndex)
    }

    private fun isSum(tokens: List< Token>, currentIndex: Int): Boolean {
        return at(tokens, currentIndex).type == TokenType.SUM
    }

    private fun buildParserResult(
        leftOperand: SuccessResult,
        rightOperand: SuccessResult,
        currentIndex: Int
    ): ParserResult {
        return SuccessResult(SumNode(leftOperand.value as ExpressionNode, rightOperand.value as ExpressionNode), currentIndex)
    }
}