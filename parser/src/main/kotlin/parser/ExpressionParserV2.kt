package parser

import parser.result.FailureResult
import parser.result.ParserResult
import parser.result.SuccessResult
import token.Token

class ExpressionParserV2(private val parserClasses: List<ParserClass>) : Parser {
    override fun parse(
        tokens: List<Token>,
        currentIndex: Int,
    ): ParserResult {
        for (parserClass in parserClasses) {
            val parser = parserClass.create(this)
            when (val result = parser.parse(tokens, currentIndex)) {
                is SuccessResult -> return result
                is FailureResult -> continue
            }
        }
        return FailureResult("The expression at index $currentIndex is invalid.", currentIndex)
    }
}
