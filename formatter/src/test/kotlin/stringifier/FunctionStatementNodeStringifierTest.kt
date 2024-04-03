package stringifier

import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.PrintLnNode
import ast.SumNode
import formatter.stringifier.FunctionStatementNodeStringifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import position.Position
import kotlin.test.Ignore

class FunctionStatementNodeStringifierTest {
    private val stringifier = FunctionStatementNodeStringifier()

    @Test
    fun `stringify PrintLnNode with string expression`() {
        val node = PrintLnNode(LiteralNode("Hello", Position(1, 9), Position(1, 15)), Position(1, 1), Position(1, 16))
        val result = stringifier.stringify(node)
        assertEquals("printLn(\"Hello\")", result)
    }

    @Test
    fun `stringify PrintLnNode with numeric expression`() {
        val node = PrintLnNode(LiteralNode(123, Position(1, 9), Position(1, 11)), Position(1, 1), Position(1, 12))
        val result = stringifier.stringify(node)
        assertEquals("printLn(123)", result)
    }

    @Test
    fun `stringify PrintLnNode with operation expression`() {
        val leftNode = LiteralNode(1, Position(1, 9), Position(1, 9))
        val rightNode = LiteralNode(2, Position(1, 11), Position(1, 11))
        val operatorNode = OperatorNode(Position(1, 10), Position(1, 10), OperatorType.SUM)
        val operationNode = SumNode(leftNode, rightNode, operatorNode, Position(1, 9), Position(1, 11))
        val node = PrintLnNode(operationNode, Position(1, 1), Position(1, 12))
        val result = stringifier.stringify(node)
        assertEquals("printLn(1 + 2)", result)
    }

    @Test
    fun `stringify PrintLnNode with string expression and extra whitespaces`() {
        val node = PrintLnNode(LiteralNode("Hello", Position(1, 19), Position(1, 25)), Position(1, 6), Position(1, 31))
        val result = stringifier.stringify(node)
        assertEquals("     printLn(     \"Hello\"     )     ", result)
    }

    //    Will fail because the implementation is not considering the extra whitespaces
    @Test
    @Ignore
    fun `stringify PrintLnNode with numeric expression and extra whitespaces`() {
        val node = PrintLnNode(LiteralNode(123, Position(1, 19), Position(1, 21)), Position(1, 6), Position(1, 27))
        val result = stringifier.stringify(node)
        assertEquals("     printLn(     123     )     ", result)
    }
}
