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
import interpreter.readInputFunction.ReadInputFunction

class OperationInterpreter() {
    fun interpret(
        node: OperationNode,
        environment: Environment,
        inputHandler: ReadInputFunction,
    ): Value {
        when (node) {
            is ProductNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment, inputHandler).value
                val right = ExpressionInterpreter().interpret(node.right, environment, inputHandler).value
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(
                        if (left.value is Int && right.value is Int) {
                            left.value * right.value
                        } else {
                            left.value.toDouble() * right.value.toDouble()
                        },
                    )
                } else {
                    throw Exception("Operands must be numbers")
                }
            }

            is SumNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment, inputHandler).value
                val right = ExpressionInterpreter().interpret(node.right, environment, inputHandler).value
                if (isStringConcatenation(left, right)) {
                    return StringValue((left as StringValue).value + (right as StringValue).value)
                }
                try {
                    if (left is NumberValue && right is NumberValue) {
                        return NumberValue(
                            if (left.value is Int && right.value is Int) {
                                left.value + right.value
                            } else {
                                left.value.toDouble() + right.value.toDouble()
                            },
                        )
                    }
                } catch (e: Exception) {
                    throw Exception("Operands must be both numbers or both strings")
                }
            }

            is SubtractionNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment, inputHandler).value
                val right = ExpressionInterpreter().interpret(node.right, environment, inputHandler).value
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(
                        if (left.value is Int && right.value is Int) {
                            left.value - right.value
                        } else {
                            left.value.toDouble() - right.value.toDouble()
                        },
                    )
                } else {
                    throw Exception("Operands must be numbers")
                }
            }

            is DivisionNode -> {
                val left = ExpressionInterpreter().interpret(node.left, environment, inputHandler).value
                val right = ExpressionInterpreter().interpret(node.right, environment, inputHandler).value
                if (left is NumberValue && right is NumberValue) {
                    return NumberValue(
                        if (left.value is Int && right.value is Int) {
                            left.value / right.value
                        } else {
                            left.value.toDouble() / right.value.toDouble()
                        },
                    )
                } else {
                    throw Exception("Operands must be numbers")
                }
            }

            is BinaryOperation -> {
                val left = ExpressionInterpreter().interpret(node.getLeft(), environment, inputHandler).value
                val right = ExpressionInterpreter().interpret(node.getRight(), environment, inputHandler).value
                val operatorType = node.getOperator().getType()
                if (isStringConcatenation(left, right)) {
                    return StringValue((left as StringValue).value + (right as StringValue).value)
                }
                if (!isNumericOperation(left, right)) {
                    if (isSum(operatorType)) {
                        throw Exception("Operands must be both numbers or both strings")
                    }
                    throw Exception("Operands must be numbers")
                }

                val leftValue = (left as NumberValue).value
                val rightValue = (right as NumberValue).value

                return NumberValue(performOperation(leftValue, rightValue, operatorType))
            }
        }
        throw Exception("Unsupported operation at (line: ${node.getStart().line} column: ${node.getStart().column})")
    }

    private fun isSum(operatorType: OperatorType) = operatorType == OperatorType.SUM

    private fun performOperation(
        leftValue: Number,
        rightValue: Number,
        operatorType: OperatorType,
    ): Number {
        return when (operatorType) {
            OperatorType.SUM -> {
                if (leftValue is Int && rightValue is Int) {
                    leftValue + rightValue
                } else {
                    leftValue.toDouble() + rightValue.toDouble()
                }
            }
            OperatorType.SUBTRACT -> {
                if (leftValue is Int && rightValue is Int) {
                    leftValue - rightValue
                } else {
                    leftValue.toDouble() - rightValue.toDouble()
                }
            }
            OperatorType.MULTIPLICATION -> {
                if (leftValue is Int && rightValue is Int) {
                    leftValue * rightValue
                } else {
                    leftValue.toDouble() * rightValue.toDouble()
                }
            }
            OperatorType.DIVISION -> {
                if (leftValue is Int && rightValue is Int) {
                    leftValue / rightValue
                } else {
                    leftValue.toDouble() / rightValue.toDouble()
                }
            }
            else -> throw Exception("Unsupported binary operation")
        }
    }

    private fun isStringConcatenation(
        left: Value,
        right: Value,
    ): Boolean {
        return left is StringValue && right is StringValue
    }

    private fun isNumericOperation(
        left: Value,
        right: Value,
    ): Boolean {
        return left is NumberValue && right is NumberValue
    }
}
