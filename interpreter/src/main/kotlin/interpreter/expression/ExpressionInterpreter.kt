package interpreter.expression

import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperationNode
import ast.PrintLnNode
import ast.ReadEnvNode
import ast.ReadInputNode
import interpreter.Environment
import interpreter.NullValue
import interpreter.Value
import interpreter.readInputFunction.ReadInputFunction

class ExpressionInterpreter {
    fun interpret(
        node: ExpressionNode,
        environment: Environment,
        inputHandler: ReadInputFunction,
    ): Value {
        return when (node) {
            is OperationNode -> OperationInterpreter().interpret(node, environment, inputHandler)
            is IdentifierNode -> IdentifierInterpreter().interpret(node, environment)
            is LiteralNode<*> -> LiteralInterpreter().interpret(node)
            is PrintLnNode -> {
                print(node.getExpression())
                NullValue
            }
            is ReadEnvNode -> TODO()
            is ReadInputNode -> ReadInputNodeInterpreter().interpret(node, environment, inputHandler)
        }
    }
}
