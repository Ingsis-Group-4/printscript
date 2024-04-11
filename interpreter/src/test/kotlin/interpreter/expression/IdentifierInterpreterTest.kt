package interpreter.expression

import ast.IdentifierNode
import ast.VariableType
import interpreter.Environment
import interpreter.StringValue
import org.junit.jupiter.api.assertThrows
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals

class IdentifierInterpreterTest {
    @Test
    fun testIdentifierInterpreter() {
        val input = IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2))

        val environment = Environment().createVariable("x", StringValue("a"), VariableType.STRING)

        val interpreter = IdentifierInterpreter()

        val result = interpreter.interpret(input, environment)

        assert(result is StringValue)
        assertEquals("a", (result as StringValue).value)
    }

    @Test
    fun testIdentifierInterpreterWithNonExistingVariable() {
        val input = IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2))

        val interpreter = IdentifierInterpreter()

        val exception =
            assertThrows<Exception> {
                interpreter.interpret(input, Environment())
            }

        assertEquals("Variable x does not exist", exception.message)
    }
}
