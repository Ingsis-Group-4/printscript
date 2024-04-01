package interpreter

import ast.LiteralNode
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals

class LiteralInterpreterTest {
    @Test
    fun testNumberLiteral() {
        val literal = LiteralNode(5.0, Position(1, 1), Position(1, 2))
        val interpreter = LiteralInterpreter(literal, Environment())

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(5.0, (result as NumberValue).value)
    }

    @Test
    fun testStringLiteral() {
        val literal = LiteralNode("hello", Position(1, 1), Position(1, 2))
        val interpreter = LiteralInterpreter(literal, Environment())

        val result = interpreter.interpret()

        assert(result is StringValue)
        assertEquals("hello", (result as StringValue).value)
    }
}
