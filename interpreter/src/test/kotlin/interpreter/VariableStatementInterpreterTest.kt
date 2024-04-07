package interpreter

import ast.AssignationNode
import ast.ColonNode
import ast.DeclarationNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.SumNode
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
        val environment = Environment()
        environment.createVariable("x", VoidValue(), VariableType.STRING)

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is StringValue)
        assertEquals("a", (environment.getVariable("x") as StringValue).value)
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
        val environment = Environment()
        environment.createVariable("x", StringValue("b"), VariableType.STRING)

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is StringValue)
        assertEquals("a", (environment.getVariable("x") as StringValue).value)
    }

    @Test
    fun testVariableDeclarationWithoutValue() {
        val input =
            DeclarationNode(
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

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is NullValue)
    }

    @Test
    fun testVariableDeclarationWithValue() {
        val input =
            DeclarationNode(
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

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is NumberValue)
        assertEquals(10.0, (environment.getVariable("x") as NumberValue).value)
    }

    @Test
    fun testVariableDeclarationWithSum() {
        val input =
            DeclarationNode(
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

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is NumberValue)
        assertEquals(30.0, (environment.getVariable("x") as NumberValue).value)
    }
}
