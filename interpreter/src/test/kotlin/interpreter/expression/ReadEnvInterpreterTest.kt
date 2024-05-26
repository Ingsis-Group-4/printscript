package interpreter.expression

import ast.LiteralNode
import ast.ReadEnvNode
import interpreter.Environment
import interpreter.InputValue
import interpreter.NullValue
import interpreter.readEnvFunction.EnvMapFunction
import interpreter.readInputFunction.StringInputFunction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import position.Position

class ReadEnvInterpreterTest {
    private val readEnvInterpreter = ReadEnvInterpreter()
    private val environment = Environment()
    private val inputHandler = StringInputFunction()

    @Test
    fun testShouldReturnValueFromEnvVariable() {
        val readEnvNode =
            ReadEnvNode(
                Position(1, 1),
                Position(1, 1),
                LiteralNode("TEST_ENV_VAR", Position(1, 1), Position(1, 1)),
            )
        val result =
            readEnvInterpreter.interpret(
                readEnvNode,
                environment,
                inputHandler,
                EnvMapFunction(mapOf("TEST_ENV_VAR" to "test_value")),
            )
        assert(result is InputValue)
        assert((result as InputValue).toString() == "test_value")
    }

    @Test
    fun testShouldReturnNullValue() {
        val readEnvNode =
            ReadEnvNode(
                Position(1, 1),
                Position(1, 1),
                LiteralNode("NOT_ENV_VAR", Position(1, 1), Position(1, 1)),
            )
        val result = readEnvInterpreter.interpret(readEnvNode, environment, inputHandler, EnvMapFunction(mapOf()))
        assert(result is NullValue)
    }

    @Test
    fun testShouldThrowWhenParamIsNotAString() {
        val readEnvNode =
            ReadEnvNode(
                Position(1, 1),
                Position(1, 1),
                LiteralNode(10.0, Position(1, 1), Position(1, 1)),
            )
        val exception =
            assertThrows<Exception> {
                readEnvInterpreter.interpret(
                    readEnvNode,
                    environment,
                    inputHandler,
                    EnvMapFunction(
                        mapOf(),
                    ),
                )
            }
        assert(exception.message == "ReadEnvInterpreter: param is not a string")
    }
}
