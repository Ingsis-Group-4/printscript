package interpreter.expression

import ast.LiteralNode
import ast.ReadInputNode
import interpreter.Environment
import interpreter.InputValue
import interpreter.NullValue
import interpreter.readEnvFunction.SystemEnvFunction
import interpreter.readInputFunction.StandardInputFunction
import interpreter.readInputFunction.StringInputFunction
import org.junit.jupiter.api.Test
import position.Position

class ReadInputNodeInterpreterTest {
    @Test
    fun `test_001 string function input`() {
        val input =
            ReadInputNode(
                Position(1, 1),
                Position(1, 10),
                LiteralNode("Enter text: ", Position(1, 7), Position(1, 10)),
            )
        val environment = Environment()
        val readInputFunction = StringInputFunction()

        val interpreter = ReadInputNodeInterpreter()

        val result = interpreter.interpret(input, environment, readInputFunction, SystemEnvFunction())

        assert(result is InputValue)
    }

    @Test
    fun `test_002 standard input function with no input should return null`() {
        val input =
            ReadInputNode(
                Position(1, 1),
                Position(1, 10),
                LiteralNode("Enter text: ", Position(1, 7), Position(1, 10)),
            )
        val environment = Environment()
        val readInputFunction = StandardInputFunction()

        val interpreter = ReadInputNodeInterpreter()

        val result = interpreter.interpret(input, environment, readInputFunction, SystemEnvFunction())

        assert(result is NullValue)
    }
}
