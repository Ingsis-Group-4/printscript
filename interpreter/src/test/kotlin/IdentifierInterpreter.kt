package interpreter

import ast.IdentifierNode
import ast.VariableType
import org.example.interpreter.Environment
import org.example.interpreter.IdentifierInterpreter
import org.example.interpreter.StringValue
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class IdentifierInterpreter {
    @Test
    fun testIdentifierInterpreter() {
        val input = IdentifierNode("x", VariableType.STRING)

        val environment = Environment()
        environment.createVariable("x", StringValue("a"), VariableType.STRING)

        val interpreter = IdentifierInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is StringValue)
        assertEquals("a", (result as StringValue).value)
    }

    @Test
    fun testIdentifierInterpreterWithNonExistingVariable() {
        val input = IdentifierNode("x", VariableType.STRING)

        val interpreter = IdentifierInterpreter(input, Environment())

        val exception =
            assertThrows<Exception> {
                interpreter.interpret()
            }

        assertEquals("Variable x does not exist", exception.message)
    }
}
