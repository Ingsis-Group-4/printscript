package interpreter.expression

import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperationNode
import interpreter.Environment
import interpreter.Value

class ExpressionInterpreter {
    fun interpret(
        node: ExpressionNode,
        environment: Environment,
    ): Value {
        return when (node) {
            is OperationNode -> OperationInterpreter().interpret(node, environment)
            is IdentifierNode -> IdentifierInterpreter().interpret(node, environment)
            is LiteralNode<*> -> LiteralInterpreter().interpret(node)
        }
    }
}