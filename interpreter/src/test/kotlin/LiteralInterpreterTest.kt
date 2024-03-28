package interpreter

import ast.LiteralNode
import kotlin.test.Test
import kotlin.test.assertEquals

class LiteralInterpreterTest {
    @Test
    fun testNumberLiteral() {
        val literal = LiteralNode(5.0)
        val interpreter = LiteralInterpreter(literal, Environment())

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(5.0, (result as NumberValue).value)
    }

    @Test
    fun testStringLiteral() {
        val literal = LiteralNode("hello")
        val interpreter = LiteralInterpreter(literal, Environment())

        val result = interpreter.interpret()

        assert(result is StringValue)
        assertEquals("hello", (result as StringValue).value)
    }
}
