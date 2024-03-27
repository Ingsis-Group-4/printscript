package interpreter

import ast.AssignationNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.SumNode
import ast.VariableDeclarationNode
import ast.VariableType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VariableStatementInterpreterTest {
    @Test
    fun testVariableAssignationWithLiteral() {
        val input = AssignationNode(IdentifierNode("x", VariableType.STRING), LiteralNode("a"))
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
        val input = AssignationNode(IdentifierNode("x", VariableType.STRING), LiteralNode("a"))
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
        val input = VariableDeclarationNode(IdentifierNode("x", VariableType.STRING))
        val environment = Environment()

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is NullValue)
    }

    @Test
    fun testVariableDeclarationWithValue() {
        val input = VariableDeclarationNode(IdentifierNode("x", VariableType.NUMBER), LiteralNode(10.0))
        val environment = Environment()

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is NumberValue)
        assertEquals(10.0, (environment.getVariable("x") as NumberValue).value)
    }

    @Test
    fun testVariableDeclarationWithSum() {
        val input = VariableDeclarationNode(IdentifierNode("x", VariableType.NUMBER), SumNode(LiteralNode(10.0), LiteralNode(20.0)))
        val environment = Environment()

        val interpreter = VariableStatementInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is VoidValue)
        assert(environment.getVariable("x") is NumberValue)
        assertEquals(30.0, (environment.getVariable("x") as NumberValue).value)
    }
}
