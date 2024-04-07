package interpreter

import ast.AssignationNode
import ast.ColonNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.SumNode
import ast.VariableDeclarationNode
import ast.VariableType
import ast.VariableTypeNode
import org.junit.jupiter.api.Test
import position.Position
import kotlin.test.assertEquals

class VariableStatementInterpreterTest {
    @Test
    fun testVariableAssignationWithLiteral() {
        val input =
            AssignationNode(
                IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2)),
                LiteralNode("a", Position(1, 1), Position(1, 2)),
                EqualsNode(Position(1, 1), Position(1, 2)),
                Position(1, 1),
                Position(1, 2),
            )

        val environment = Environment().createVariable("x", NullValue, VariableType.STRING)

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is StringValue)
        assertEquals("a", (result.environment.getVariable("x") as StringValue).value)
    }

    @Test
    fun testVariableReAssignationWithLiteral() {
        val input =
            AssignationNode(
                IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2)),
                LiteralNode("a", Position(1, 1), Position(1, 2)),
                EqualsNode(Position(1, 1), Position(1, 2)),
                Position(1, 1),
                Position(1, 2),
            )
        val environment = Environment().createVariable("x", StringValue("b"), VariableType.STRING)

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is StringValue)
        assertEquals("a", (result.environment.getVariable("x") as StringValue).value)
    }

    @Test
    fun testVariableDeclarationWithoutValue() {
        val input =
            VariableDeclarationNode(
                IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2)),
                null,
                LetNode(Position(1, 1), Position(1, 1)),
                ColonNode(Position(1, 1), Position(1, 1)),
                VariableTypeNode(VariableType.STRING, Position(1, 1), Position(1, 1)),
                null,
                Position(1, 1),
                Position(1, 2),
            )
        val environment = Environment()

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is NullValue)
    }

    @Test
    fun testVariableDeclarationWithValue() {
        val input =
            VariableDeclarationNode(
                IdentifierNode("x", VariableType.NUMBER, Position(1, 1), Position(1, 2)),
                LiteralNode(10.0, Position(1, 1), Position(1, 2)),
                LetNode(Position(1, 1), Position(1, 1)),
                ColonNode(Position(1, 1), Position(1, 1)),
                VariableTypeNode(VariableType.STRING, Position(1, 1), Position(1, 1)),
                null,
                Position(1, 1),
                Position(1, 2),
            )
        val environment = Environment()

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is NumberValue)
        assertEquals(10.0, (result.environment.getVariable("x") as NumberValue).value)
    }

    @Test
    fun testVariableDeclarationWithSum() {
        val input =
            VariableDeclarationNode(
                IdentifierNode("x", VariableType.NUMBER, Position(1, 1), Position(1, 2)),
                SumNode(
                    LiteralNode(10.0, Position(1, 1), Position(1, 2)),
                    LiteralNode(20.0, Position(1, 1), Position(1, 2)),
                    OperatorNode(Position(1, 1), Position(1, 2), OperatorType.SUM),
                    Position(1, 1),
                    Position(1, 1),
                ),
                LetNode(Position(1, 1), Position(1, 1)),
                ColonNode(Position(1, 1), Position(1, 1)),
                VariableTypeNode(VariableType.STRING, Position(1, 1), Position(1, 1)),
                null,
                Position(1, 1),
                Position(1, 2),
            )
        val environment = Environment()

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is NumberValue)
        assertEquals(30.0, (result.environment.getVariable("x") as NumberValue).value)
    }
}
