package parser

import ast.ExpressionNode
import ast.OperatorNode
import ast.OperatorType
import ast.SumNode
import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.isTokenValid
import token.Token
import token.TokenType

class SumParser(private val baseParser: Parser) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        for (i in currentIndex until tokens.size) {
            if (isTokenValid(tokens, i, TokenType.SUM)) {
                val leftTokens = tokens.subList(currentIndex, i)
                val rightTokens = tokens.subList(i + 1, tokens.size)
                val left = baseParser.parse(leftTokens, currentIndex)
                val right = baseParser.parse(rightTokens, i + 1)
                if (left is SuccessResult && right is SuccessResult) {
                    return SuccessResult(
                        SumNode(
                            left.value as ExpressionNode,
                            right.value as ExpressionNode,
                            OperatorNode(
                                at(tokens, i).start,
                                at(tokens, i).end,
                                OperatorType.SUM,
                            ),
                            at(tokens, currentIndex).start,
                            at(tokens, right.lastValidatedIndex).end
                        ),
                        i
                    )
                }
            }
        }
        return FailureResult("The sum at index $currentIndex is invalid.", currentIndex)
    }

}
