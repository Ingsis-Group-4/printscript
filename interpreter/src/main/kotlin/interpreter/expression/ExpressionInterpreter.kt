package interpreter.expression

import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperationNode
import ast.PrintLnNode
import ast.ReadEnvNode
import ast.ReadInputNode
import interpreter.Environment
import interpreter.ExpressionInterpreterOutput
import interpreter.NullValue
import interpreter.readEnvFunction.ReadEnvFunction
import interpreter.readInputFunction.ReadInputFunction

class ExpressionInterpreter {
    fun interpret(
        node: ExpressionNode,
        environment: Environment,
        inputHandler: ReadInputFunction,
        envHandler: ReadEnvFunction,
    ): ExpressionInterpreterOutput {
        return when (node) {
            is OperationNode ->
                ExpressionInterpreterOutput(
                    OperationInterpreter().interpret(node, environment, inputHandler, envHandler),
                    listOf(),
                )
            is IdentifierNode ->
                ExpressionInterpreterOutput(
                    IdentifierInterpreter().interpret(node, environment),
                    listOf(),
                )
            is LiteralNode<*> -> ExpressionInterpreterOutput(LiteralInterpreter().interpret(node), listOf())
            is PrintLnNode -> {
                val value = ExpressionInterpreter().interpret(node.getExpression(), environment, inputHandler, envHandler).value.toString()
                ExpressionInterpreterOutput(NullValue, listOf(value))
            }
            is ReadEnvNode -> {
                ExpressionInterpreterOutput(
                    ReadEnvInterpreter().interpret(node, environment, inputHandler, envHandler),
                    listOf(),
                )
            }
            is ReadInputNode -> {
                val expressionOutput = ExpressionInterpreter().interpret(node.getExpression(), environment, inputHandler, envHandler)
                ExpressionInterpreterOutput(
                    ReadInputNodeInterpreter().interpret(node, environment, inputHandler, envHandler),
                    listOf(expressionOutput.value.toString()),
                )
            }
        }
    }
}
