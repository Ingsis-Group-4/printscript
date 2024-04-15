package interpreter
import ast.AssignationNode
import ast.BlockNode
import ast.EqualsNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.IfStatement
import ast.LiteralNode
import ast.PrintLnNode
import ast.VariableType
import org.junit.jupiter.api.assertThrows
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class IfStatementInterpreterTest {
    @Test
    fun testNonBooleanConditionShouldThrow() {
        val interpreter = IfStatementInterpreter()

        var environment = Environment()
        environment = environment.createVariable("a", NumberValue(1.0), VariableType.NUMBER)

        val input =
            IfStatement(
                Position(1, 1),
                Position(1, 2),
                IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 2)),
                BlockNode(
                    Position(1, 1),
                    Position(1, 2),
                    listOf(
                        AssignationNode(
                            IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 2)),
                            LiteralNode(2.0, Position(1, 1), Position(1, 2)),
                            EqualsNode(Position(1, 1), Position(1, 2)),
                            Position(1, 1),
                            Position(1, 2),
                        ),
                    ),
                ),
                null,
            )
        val exception = assertThrows<Exception> { interpreter.interpret(input, environment) }
        assert(exception.message == "Condition must be a boolean value")
    }

    @Test
    fun testIfStatementInterpreterShouldPrint() {
        var environment = Environment()
        environment = environment.createVariable("a", BooleanValue(true), VariableType.BOOLEAN)
        val input =
            IfStatement(
                Position(1, 1),
                Position(1, 2),
                IdentifierNode("a", VariableType.BOOLEAN, Position(1, 1), Position(1, 2)),
                BlockNode(
                    Position(1, 1),
                    Position(1, 2),
                    listOf(
                        FunctionStatementNode(
                            Position(2, 1),
                            Position(2, 17),
                            PrintLnNode(
                                LiteralNode("Hello World!", Position(2, 8), Position(2, 16)),
                                Position(2, 1),
                                Position(2, 17),
                            ),
                        ),
                    ),
                ),
                null,
            )
        val interpreter = IfStatementInterpreter()
        val result = interpreter.interpret(input, environment)
        val logs = result.logs
        assert(logs.size == 1)
        assert(logs[0] == "Hello World!")
    }

    @Test
    fun testIfStatementInterpreterShouldChangeVariableValue() {
        var environment = Environment()
        environment = environment.createVariable("a", BooleanValue(true), VariableType.BOOLEAN)
        environment = environment.createVariable("b", StringValue("Hello"), VariableType.STRING)
        val input =
            IfStatement(
                Position(1, 1),
                Position(1, 2),
                IdentifierNode("a", VariableType.BOOLEAN, Position(1, 1), Position(1, 2)),
                BlockNode(
                    Position(1, 1),
                    Position(1, 2),
                    listOf(
                        AssignationNode(
                            IdentifierNode("b", VariableType.STRING, Position(1, 1), Position(1, 2)),
                            LiteralNode("Hola!", Position(1, 1), Position(1, 2)),
                            EqualsNode(Position(1, 1), Position(1, 2)),
                            Position(1, 1),
                            Position(1, 2),
                        ),
                    ),
                ),
                null,
            )
        val interpreter = IfStatementInterpreter()
        val result = interpreter.interpret(input, environment)
        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("b") is StringValue)
        assertEquals("Hola!", (result.environment.getVariable("b")as StringValue).toString())
    }

    @Test
    fun testIfStatementInterpreterShouldExecuteElseBlock() {
        var environment = Environment()
        environment = environment.createVariable("a", BooleanValue(false), VariableType.BOOLEAN)
        environment = environment.createVariable("b", StringValue("Not modified"), VariableType.STRING)

        val input =
            IfStatement(
                Position(1, 1),
                Position(1, 2),
                IdentifierNode("a", VariableType.BOOLEAN, Position(1, 1), Position(1, 2)),
                BlockNode(
                    Position(1, 1),
                    Position(1, 2),
                    listOf(
                        AssignationNode(
                            IdentifierNode("b", VariableType.STRING, Position(1, 1), Position(1, 2)),
                            LiteralNode("Went into the Then block!", Position(1, 1), Position(1, 2)),
                            EqualsNode(Position(1, 1), Position(1, 2)),
                            Position(1, 1),
                            Position(1, 2),
                        ),
                    ),
                ),
                BlockNode(
                    Position(1, 1),
                    Position(1, 2),
                    listOf(
                        AssignationNode(
                            IdentifierNode("b", VariableType.STRING, Position(1, 1), Position(1, 2)),
                            LiteralNode("Went into the Else block!", Position(1, 1), Position(1, 2)),
                            EqualsNode(Position(1, 1), Position(1, 2)),
                            Position(1, 1),
                            Position(1, 2),
                        ),
                    ),
                ),
            )
        val interpreter = IfStatementInterpreter()
        val result = interpreter.interpret(input, environment)
        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("b") is StringValue)
        assertNotEquals("Not modified", (result.environment.getVariable("b")as StringValue).toString())
        assertEquals("Went into the Else block!", (result.environment.getVariable("b")as StringValue).toString())
    }
}
