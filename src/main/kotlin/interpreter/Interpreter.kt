package org.example.interpreter

import org.example.ast.*


class Interpreter : Visitor<Any> {
    private val environment: Environment = Environment()


    override fun visit(node: ProgramNode) {
        for (statement in node.statements) {
            statement.accept(this)
        }
    }

    override fun visit(node: AssignationNode) {
        val value = node.expression.accept(this)
        environment.updateVariable(node.identifier.variableName, createEnvironmentValue(value))
    }

    override fun visit(node: DivisionNode): Double {
        val left = node.left.accept(this)
        val right = node.right.accept(this)

        if (left is Double && right is Double) {
            return left / right
        }
        throw RuntimeException("Invalid division")
    }

    override fun visit(node: IdentifierNode): EnvironmentValue {
        return environment.getVariable(node.variableName)
    }

    override fun visit(node: StringNode): String {
        return node.value
    }

    override fun visit(node: NumberNode): Double {
        return node.value
    }

    override fun visit(node: PrintLnNode) {
        print(node.expression.accept(this))
    }

    override fun visit(node: ProductNode): Double {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        if (left is Double && right is Double) {
            return left * right
        }
        throw RuntimeException("Invalid product")
    }

    override fun visit(node: SubtractionNode): Double {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        if (left is Double && right is Double) {
            return left - right
        }
        throw RuntimeException("Invalid subtraction")
    }

    override fun visit(node: SumNode): Double {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        if (left is Double && right is Double) {
            return left + right
        }
        throw RuntimeException("Invalid sum")
    }

    override fun visit(node: VariableDeclarationNode) {
        when (node.expression) {
            null -> environment.createVariable(node.identifier.variableName, NullValue(), node.identifier.variableType)
            else -> {
                val value = node.expression.accept(this)
                environment.createVariable(
                    node.identifier.variableName,
                    createEnvironmentValue(value),
                    node.identifier.variableType
                )

            }
        }
    }

    private fun <T> createEnvironmentValue(value: T): EnvironmentValue {
        return when (value) {
            is Double -> NumberValue(value)
            is String -> StringValue(value)
            else -> NullValue()
        }
    }

}
