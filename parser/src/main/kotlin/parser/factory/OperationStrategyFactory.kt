package parser.factory

import parser.strategy.DivisionOperationStrategy
import parser.strategy.MultiplicationOperationStrategy
import parser.strategy.OperationStrategy
import parser.strategy.SubtractionOperationStrategy
import parser.strategy.SumOperationStrategy
import token.TokenType

interface OperationStrategyFactory {
    fun getStrategy(operatorType: TokenType): OperationStrategy
}

object DefaultOperationStrategyFactory : OperationStrategyFactory {
    override fun getStrategy(operatorType: TokenType): OperationStrategy {
        return when (operatorType) {
            TokenType.SUM -> SumOperationStrategy()
            TokenType.SUBTRACTION -> SubtractionOperationStrategy()
            TokenType.MULTIPLICATION -> MultiplicationOperationStrategy()
            TokenType.DIVISION -> DivisionOperationStrategy()
            else -> throw IllegalArgumentException("Unsupported operator type: $operatorType")
        }
    }
}
