package interpreter

import ast.DivisionNode
import ast.OperationNode
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import interpreter.expression.ExpressionInterpreter

class OperationInterpreter() {
    fun interpret(
        node: OperationNode,
        environment: Environment,
    ): Value {
        when (node) {
            is ProductNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment)
                val right = ExpressionInterpreter().interpret(node.right, environment)
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(left.value * right.value)
                } else {
                    throw Exception("Operands must be numbers")
                }
            }

            is SumNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment)
                val right = ExpressionInterpreter().interpret(node.right, environment)
                return when {
                    left is NumberValue && right is NumberValue -> NumberValue(left.value + right.value)
                    left is StringValue && right is StringValue -> StringValue(left.value + right.value)
                    else -> throw Exception("Operands must be both numbers or both strings")
                }
            }

            is SubtractionNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment)
                val right = ExpressionInterpreter().interpret(node.right, environment)
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(left.value - right.value)
                } else {
                    throw Exception("Operands must be numbers")
                }
            }

            is DivisionNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment)
                val right = ExpressionInterpreter().interpret(node.right, environment)
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(left.value / right.value)
                } else {
                    throw Exception("Operands must be numbers")
                }
            }
        }
//        Not sure if this is the best way to handle this
        return VoidValue()
    }
}
