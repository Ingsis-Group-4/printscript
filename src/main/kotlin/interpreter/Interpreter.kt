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
        environment.createVariable(node.identifier.variableName, value, node.identifier.variableType)
    }

    override fun visit(node: DivisionNode): Int {
        val left = node.left.accept(this)
        val right = node.right.accept(this)

        if (left is Int && right is Int) {
            return left / right
        }
        throw RuntimeException("Invalid division")
    }

    override fun visit(node: IdentifierNode): Pair<String, VariableType?> {
        return Pair(node.variableName, node.variableType)
    }

    override fun <K> visit(node: LiteralNode<K>): Any {
        return node.value!!
    }


    override fun visit(node: PrintLnNode) {
        TODO("Not yet implemented")
    }

    override fun visit(node: ProductNode): Int {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        if (left is Int && right is Int) {
            return left * right
        }
        throw RuntimeException("Invalid product")
    }

    override fun visit(node: SubtractionNode): Int {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        if (left is Int && right is Int) {
            return left - right
        }
        throw RuntimeException("Invalid subtraction")
    }

    override fun visit(node: SumNode): Int {
        val left = node.left.accept(this)
        val right = node.right.accept(this)
        if (left is Int && right is Int) {
            return left + right
        }
        throw RuntimeException("Invalid sum")
    }

    override fun visit(node: VariableDeclarationNode) {
        TODO("Not yet implemented")
    }

}
