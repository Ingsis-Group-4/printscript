package interpreter

import ast.DivisionNode
import ast.OperationNode
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode

class OperationInterpreter(private val node: OperationNode, private val environment: Environment) : Interpreter {
    override fun interpret(): Value {
        when (node) {
            is ProductNode -> {
                val left = ExpressionInterpreter(node.left, environment).interpret()
                val right = ExpressionInterpreter(node.right, environment).interpret()
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(left.value * right.value)
                } else {
                    throw Exception("Operands must be numbers")
                }
            }

            is SumNode -> {
                val left = ExpressionInterpreter(node.left, environment).interpret()
                val right = ExpressionInterpreter(node.right, environment).interpret()
                return when {
                    left is NumberValue && right is NumberValue -> NumberValue(left.value + right.value)
                    left is StringValue && right is StringValue -> StringValue(left.value + right.value)
                    else -> throw Exception("Operands must be both numbers or both strings")
                }
            }

            is SubtractionNode -> {
                val left = ExpressionInterpreter(node.left, environment).interpret()
                val right = ExpressionInterpreter(node.right, environment).interpret()
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(left.value - right.value)
                } else {
                    throw Exception("Operands must be numbers")
                }
            }

            is DivisionNode -> {
                val left = ExpressionInterpreter(node.left, environment).interpret()
                val right = ExpressionInterpreter(node.right, environment).interpret()
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
