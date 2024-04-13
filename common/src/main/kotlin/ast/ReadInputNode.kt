package ast

import position.Position
import readInputFunction.ReadInputFunction
import readInputFunction.ReadInputFunctionFailure
import readInputFunction.ReadInputFunctionSuccess
import readInputFunction.StandardInputFunction

class ReadInputNode(
    private val start: Position,
    private val end: Position,
    private val expression: ExpressionNode,
    private var readInputFunction: ReadInputFunction = StandardInputFunction(),
) : FunctionNode {
    override fun getStart(): Position = start

    override fun getEnd(): Position = end

    override fun getExpression(): ExpressionNode = expression

    fun read(readInputFunction: ReadInputFunction): String {
        readInputFunction.read(expression.toString()).let {
            return when (it) {
                is ReadInputFunctionSuccess -> it.value
                is ReadInputFunctionFailure -> mockData()
                else -> {
                    throw Exception("Unknown ReadInputFunctionResult")
                }
            }
        }
    }

    private fun mockData(): String {
        return "1"
    }

    fun setReadInputFunction(readInputFunction: ReadInputFunction) {
        this.readInputFunction = readInputFunction
    }
}
