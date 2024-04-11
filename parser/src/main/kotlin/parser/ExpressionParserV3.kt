package parser

import ast.AST
import parser.factory.OperationStrategyFactory
import parser.utils.OperatorProvider
import parser.factory.TokenHandlerFactory
import parser.result.ParserResult
import parser.result.SuccessResult
import parser.utils.at
import parser.utils.isOutOfBounds
import parser.utils.nextIndex
import token.Token

class ExpressionParserV3(
    private val strategyFactory: OperationStrategyFactory,
    private val tokenHandlerFactory: TokenHandlerFactory,
) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        return parsePrimaryExpression(tokens, currentIndex)
    }

    private fun parsePrimaryExpression(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val leftResult = parseTerm(tokens, currentIndex)
        var left = leftResult.value

        var lastIndex = leftResult.lastValidatedIndex
        var currentOperatorIndex = nextIndex(lastIndex)

        while (!isOutOfBounds(tokens, currentOperatorIndex) && OperatorProvider.isPrimaryOperator(
                tokens,
                currentOperatorIndex
            )
        ) {
            val operator = at(tokens, currentOperatorIndex)
            val rightResult = parseTerm(tokens, nextIndex(currentOperatorIndex))

            left = buildOperationNode(left, rightResult.value, operator)
            lastIndex = rightResult.lastValidatedIndex
            currentOperatorIndex = nextIndex(lastIndex)
        }

        return SuccessResult(
            left,
            lastIndex,
        )
    }

    private fun parseTerm(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val leftResult = parserFactor(tokens, currentIndex)
        var left = leftResult.value
        var lastIndex = leftResult.lastValidatedIndex

        var currentOperatorIndex = nextIndex(lastIndex)

        while (!isOutOfBounds(tokens, currentOperatorIndex) && OperatorProvider.isTermOperator(
                tokens,
                currentOperatorIndex
            )
        ) {
            val operator = at(tokens, currentOperatorIndex)
            val rightResult = parserFactor(tokens, nextIndex(currentOperatorIndex))

            left = buildOperationNode(left, rightResult.value, operator)
            lastIndex = rightResult.lastValidatedIndex
            currentOperatorIndex = nextIndex(lastIndex)
        }

        return SuccessResult(
            left,
            lastIndex,
        )
    }

    private fun parserFactor(
        tokens: List<Token>,
        currentIndex: Int,
    ): SuccessResult {
        val currentToken = at(tokens, currentIndex)
        val handler = tokenHandlerFactory.getHandler(currentToken.type, this)
        val astNode = handler.handleToken(tokens, currentIndex)
        return SuccessResult(astNode, currentIndex)
    }


    private fun buildOperationNode(
        left: AST,
        right: AST,
        operator: Token,
    ): AST {
        val strategy = strategyFactory.getStrategy(operator.type)
        return strategy.execute(left, right, operator)
    }
}
