package interpreter.expression

import ast.BinaryOperation
import ast.DivisionNode
import ast.OperationNode
import ast.OperatorType
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import interpreter.Environment
import interpreter.NumberValue
import interpreter.StringValue
import interpreter.Value

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
                    left is StringValue && right is NumberValue -> StringValue(left.value + right.value.toString())
                    left is NumberValue && right is StringValue -> StringValue(left.value.toString() + right.value)
                    else -> throw Exception("Wrong operands types")
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

            is BinaryOperation -> {
                val left = ExpressionInterpreter().interpret(node.getLeft(), environment)
                val right = ExpressionInterpreter().interpret(node.getRight(), environment)
                return when (node.getOperator().getType()) {
                    OperatorType.SUM -> {
                        when {
                            left is NumberValue && right is NumberValue -> NumberValue(left.value + right.value)
                            left is StringValue && right is StringValue -> StringValue(left.value + right.value)
                            else -> throw Exception("Operands must be both numbers or both strings")
                        }
                    }
                    OperatorType.SUBTRACT -> {
                        if (left is NumberValue && right is NumberValue) {
                            return NumberValue(left.value - right.value)
                        } else {
                            throw Exception("Operands must be numbers")
                        }
                    }
                    OperatorType.MULTIPLICATION -> {
                        if (left is NumberValue && right is NumberValue) {
                            return NumberValue(left.value * right.value)
                        } else {
                            throw Exception("Operands must be numbers")
                        }
                    }
                    OperatorType.DIVISION -> {
                        if (left is NumberValue && right is NumberValue) {
                            return NumberValue(left.value / right.value)
                        } else {
                            throw Exception("Operands must be numbers")
                        }
                    }
                    else -> throw Exception(
                        "Unsupported binary operation at (line: ${node.getStart().line} column: ${node.getStart().column})",
                    )
                }
            }
        }
        throw Exception("Unsupported operation at (line: ${node.getStart().line} column: ${node.getStart().column})")
    }
}
