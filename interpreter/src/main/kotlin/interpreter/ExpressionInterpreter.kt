package interpreter

import ast.ExpressionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperationNode

class ExpressionInterpreter(private val node: ExpressionNode, private val environment: Environment) : Interpreter {
    override fun interpret(): Value {
        return when (node) {
            is OperationNode -> OperationInterpreter(node, environment).interpret()
            is IdentifierNode -> IdentifierInterpreter(node, environment).interpret()
            is LiteralNode<*> -> LiteralInterpreter(node, environment).interpret()
        }
    }
}
