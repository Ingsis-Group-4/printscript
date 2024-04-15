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
import ast.ReadInputNode
import ast.SumNode
import ast.VariableType
import ast.VariableTypeNode
import interpreter.readInputFunction.BooleanInputFunction
import interpreter.readInputFunction.NumberInputFunction
import interpreter.readInputFunction.StringInputFunction
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is NullValue)
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

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is NumberValue)
        assertEquals(10.0, (result.environment.getVariable("x") as NumberValue).value)
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

        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment)

        assertEquals(0, result.logs.size)
        assert(result.environment.getVariable("x") is NumberValue)
        assertEquals(30.0, (result.environment.getVariable("x") as NumberValue).value)
    }

    @Test
    fun testVariableReAssignationWithConstVariable() {
        var environment = Environment()
        environment = environment.createVariable("x", NumberValue(10.0), VariableType.NUMBER, false)
        val input =
            AssignationNode(
                IdentifierNode("x", VariableType.NUMBER, Position(1, 1), Position(1, 2)),
                LiteralNode(8.0, Position(1, 1), Position(1, 2)),
                EqualsNode(Position(1, 1), Position(1, 2)),
                Position(1, 1),
                Position(1, 2),
            )
        val interpreter = VariableStatementInterpreter()
        val exception = assertThrows<Exception> { interpreter.interpret(input, environment) }
        assert(exception.message == "Variable x is not modifiable")
    }

    @Test
    fun testMockedStringReadInputAsExpression() {
        var environment = Environment()
        environment = environment.createVariable("a", StringValue("Hello"), VariableType.STRING)

        val input =
            AssignationNode(
                IdentifierNode("a", null, Position(1, 1), Position(1, 2)),
                ReadInputNode(
                    Position(1, 1),
                    Position(1, 2),
                    LiteralNode("Enter text: ", Position(1, 1), Position(1, 2)),
                ),
                EqualsNode(Position(1, 1), Position(1, 2)),
                Position(1, 1),
                Position(1, 2),
            )
        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment, StringInputFunction())

        assert(result.logs.size == 1)
        assert(result.logs[0] == "Enter text: ")
        assert(result.environment.getVariable("a") is StringValue)
        assert((result.environment.getVariable("a") as StringValue).value == "String")
    }

    @Test
    fun testMockedNumberReadInputAsExpression() {
        var environment = Environment()
        environment = environment.createVariable("a", NumberValue(10.0), VariableType.NUMBER)

        val input =
            AssignationNode(
                IdentifierNode("a", null, Position(1, 1), Position(1, 2)),
                ReadInputNode(
                    Position(1, 1),
                    Position(1, 2),
                    LiteralNode("Enter number: ", Position(1, 1), Position(1, 2)),
                ),
                EqualsNode(Position(1, 1), Position(1, 2)),
                Position(1, 1),
                Position(1, 2),
            )
        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment, NumberInputFunction())

        assert(result.logs.size == 1)
        assert(result.logs[0] == "Enter number: ")
        assert(result.environment.getVariable("a") is NumberValue)
        assert((result.environment.getVariable("a") as NumberValue).value == 1.0)
    }

    @Test
    fun testMockedBooleanReadInputAsExpression() {
        var environment = Environment()
        environment = environment.createVariable("a", BooleanValue(false), VariableType.BOOLEAN)

        val input =
            AssignationNode(
                IdentifierNode("a", null, Position(1, 1), Position(1, 2)),
                ReadInputNode(
                    Position(1, 1),
                    Position(1, 2),
                    LiteralNode("Enter value: ", Position(1, 1), Position(1, 2)),
                ),
                EqualsNode(Position(1, 1), Position(1, 2)),
                Position(1, 1),
                Position(1, 2),
            )
        val interpreter = VariableStatementInterpreter()

        val result = interpreter.interpret(input, environment, BooleanInputFunction())

        assert(result.logs.size == 1)
        assert(result.logs[0] == "Enter value: ")
        assert(result.environment.getVariable("a") is BooleanValue)
        assert((result.environment.getVariable("a") as BooleanValue).value)
    }
}
