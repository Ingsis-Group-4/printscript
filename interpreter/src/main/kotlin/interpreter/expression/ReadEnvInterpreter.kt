package interpreter.expression

import ast.ReadEnvNode
import interpreter.Environment
import interpreter.InputValue
import interpreter.NullValue
import interpreter.StringValue
import interpreter.Value
import interpreter.readInputFunction.ReadInputFunction
import java.lang.Exception

class ReadEnvInterpreter {
    fun interpret(
        node: ReadEnvNode,
        environment: Environment,
        inputHandler: ReadInputFunction,
    ): Value {
        val param = ExpressionInterpreter().interpret(node.getExpression(), environment, inputHandler).value
        if (param !is StringValue) {
            throw Exception("ReadEnvInterpreter: param is not a string")
        } else {
            val envVar = System.getenv(param.toString())
            return if (envVar != null) {
                InputValue(envVar)
            } else {
                NullValue
            }
        }
    }
}
