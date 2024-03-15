package org.example.interpreter

import org.example.ast.ExpressionNode
import org.example.ast.IdentifierNode
import org.example.ast.LiteralNode
import org.example.ast.OperationNode

class ExpressionInterpreter(private val node: ExpressionNode, private val environment: Environment): Interpreter {
    override fun interpret(): Value {
        return when(node) {
            is OperationNode -> OperationInterpreter(node, environment).interpret()
            is IdentifierNode -> IdentifierInterpreter(node, environment).interpret()
            is LiteralNode<*> -> LiteralInterpreter(node, environment).interpret()
        }
    }

}
