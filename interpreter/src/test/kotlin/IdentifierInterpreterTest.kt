package interpreter

import ast.IdentifierNode
import ast.VariableType
import org.junit.jupiter.api.assertThrows
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals

class IdentifierInterpreterTest {
    @Test
    fun testIdentifierInterpreter() {
        val input = IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2))

        val environment = Environment()
        environment.createVariable("x", StringValue("a"), VariableType.STRING)

        val interpreter = IdentifierInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is StringValue)
        assertEquals("a", (result as StringValue).value)
    }

    @Test
    fun testIdentifierInterpreterWithNonExistingVariable() {
        val input = IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2))

        val interpreter = IdentifierInterpreter(input, Environment())

        val exception =
            assertThrows<Exception> {
                interpreter.interpret()
            }

        assertEquals("Variable x does not exist", exception.message)
    }
}
