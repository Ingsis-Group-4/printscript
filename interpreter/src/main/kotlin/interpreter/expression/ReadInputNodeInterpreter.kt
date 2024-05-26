package interpreter.expression

import ast.ReadInputNode
import interpreter.Environment
import interpreter.InputValue
import interpreter.NullValue
import interpreter.Value
import interpreter.readEnvFunction.ReadEnvFunction
import interpreter.readInputFunction.ReadInputFunction

class ReadInputNodeInterpreter {
    fun interpret(
        readInputNode: ReadInputNode,
        environment: Environment,
        readInputFunction: ReadInputFunction,
        readEnvFunction: ReadEnvFunction,
    ): Value {
        val param = ExpressionInterpreter().interpret(readInputNode.getExpression(), environment, readInputFunction, readEnvFunction)
        readInputFunction.read(param.value.toString()).let {
            return when (it) {
                is String -> InputValue(it)
                else -> NullValue
            }
        }
    }
}
