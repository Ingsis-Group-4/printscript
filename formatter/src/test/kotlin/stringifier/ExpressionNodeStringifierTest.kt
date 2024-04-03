package stringifier

import ast.DivisionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import formatter.stringifier.ExpressionNodeStringifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import position.Position

class ExpressionNodeStringifierTest {
    private val stringifier = ExpressionNodeStringifier()

    @Test
    fun `stringify literal node with string value`() {
        val node = LiteralNode("Hello", Position(1, 1), Position(1, 7))
        val result = stringifier.stringify(node)
        assertEquals("\"Hello\"", result)
    }

    @Test
    fun `stringify literal node with numeric value`() {
        val node = LiteralNode(123, Position(1, 1), Position(1, 3))
        val result = stringifier.stringify(node)
        assertEquals("123", result)
    }

    @Test
    fun `stringify operation node with sum operator`() {
        val leftNode = LiteralNode(1, Position(1, 1), Position(1, 1))
        val rightNode = LiteralNode(2, Position(1, 3), Position(1, 3))
        val operatorNode = OperatorNode(Position(1, 2), Position(1, 2), OperatorType.SUM)
        val node = SumNode(leftNode, rightNode, operatorNode, Position(1, 1), Position(1, 3))
        val result = stringifier.stringify(node)
        assertEquals("1 + 2", result)
    }

    @Test
    fun `stringify operation node with subtraction operator`() {
        val leftNode = LiteralNode(1, Position(1, 1), Position(1, 1))
        val rightNode = LiteralNode(2, Position(1, 3), Position(1, 3))
        val operatorNode = OperatorNode(Position(1, 2), Position(1, 2), OperatorType.SUB)
        val node = SubtractionNode(leftNode, rightNode, operatorNode, Position(1, 1), Position(1, 3))
        val result = stringifier.stringify(node)
        assertEquals("1 - 2", result)
    }

    @Test
    fun `stringify operation node with multiplication operator`() {
        val leftNode = LiteralNode(1, Position(1, 1), Position(1, 1))
        val rightNode = LiteralNode(2, Position(1, 3), Position(1, 3))
        val operatorNode = OperatorNode(Position(1, 2), Position(1, 2), OperatorType.MUL)
        val node = ProductNode(leftNode, rightNode, operatorNode, Position(1, 1), Position(1, 3))
        val result = stringifier.stringify(node)
        assertEquals("1 * 2", result)
    }

    @Test
    fun `stringify operation node with division operator`() {
        val leftNode = LiteralNode(1, Position(1, 1), Position(1, 1))
        val rightNode = LiteralNode(2, Position(1, 3), Position(1, 3))
        val operatorNode = OperatorNode(Position(1, 2), Position(1, 2), OperatorType.DIV)
        val node = DivisionNode(leftNode, rightNode, operatorNode, Position(1, 1), Position(1, 3))
        val result = stringifier.stringify(node)
        assertEquals("1 / 2", result)
    }

    @Test
    fun `stringify identifier node`() {
        val node = IdentifierNode("variableName", null, Position(1, 1), Position(1, 13))
        val result = stringifier.stringify(node)
        assertEquals("variableName", result)
    }

    @Test
    fun `stringify unsupported literal node throws exception`() {
        val node = LiteralNode(Boolean, Position(1, 1), Position(1, 1))
        assertThrows<IllegalArgumentException> {
            stringifier.stringify(node)
        }
    }
}
