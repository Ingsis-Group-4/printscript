package interpreter

import ast.AssignationNode
import ast.PrintLnNode
import ast.VariableDeclarationNode
import ast.VariableType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import util.getAstFromString
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StatementInterpreterTest {
    @Test
    fun `test_001 empty statement interpreter should throw`() {
        val interpreter = StatementInterpreter(mapOf())

        val input = "let a: String;"
        val ast = getAstFromString(input)

        val exception = assertThrows<Exception> { interpreter.interpret(ast, Environment()) }
        assertEquals("Unknown statement at (line: 1 column: 1)", exception.message)
    }

    @Test
    fun `test_002 declaration should add variable with null value to environment`() {
        val interpreter =
            StatementInterpreter(
                mapOf(
                    PrintLnNode::class to FunctionStatementInterpreter(),
                    VariableDeclarationNode::class to VariableStatementInterpreter(),
                    AssignationNode::class to VariableStatementInterpreter(),
                ),
            )

        val variableName = "a"

        val input = "let $variableName: String;"
        val ast = getAstFromString(input)

        val output = interpreter.interpret(ast, Environment())

        assertIs<NullValue>(output.environment.getVariable(variableName))
    }

    @Test
    fun `test_003 declaration with number should add variable with number value to environment`() {
        val interpreter =
            StatementInterpreter(
                mapOf(
                    PrintLnNode::class to FunctionStatementInterpreter(),
                    VariableDeclarationNode::class to VariableStatementInterpreter(),
                    AssignationNode::class to VariableStatementInterpreter(),
                ),
            )

        val variableName = "a"
        val variableValue = 1

        val input = "let $variableName: Number = $variableValue ;"
        val ast = getAstFromString(input)

        val output = interpreter.interpret(ast, Environment())

        val value = output.environment.getVariable(variableName)

        assertIs<NumberValue>(value)
        assertEquals(variableValue.toDouble(), value.value)
    }

    @Test
    fun `test_004 assignation with number should update variable with from environment`() {
        val interpreter =
            StatementInterpreter(
                mapOf(
                    PrintLnNode::class to FunctionStatementInterpreter(),
                    VariableDeclarationNode::class to VariableStatementInterpreter(),
                    AssignationNode::class to VariableStatementInterpreter(),
                ),
            )

        val variableName = "a"
        val oldVariableValue = 1
        val environment =
            Environment().createVariable(
                variableName,
                NumberValue(oldVariableValue.toDouble()),
                VariableType.NUMBER,
            )

        val newVariableValue = 3

        val input = "$variableName = $newVariableValue;"
        val ast = getAstFromString(input)

        val output = interpreter.interpret(ast, environment)

        val value = output.environment.getVariable(variableName)

        assertIs<NumberValue>(value)
        assertEquals(newVariableValue.toDouble(), value.value)
    }

    @Test
    fun `test_005 println should return log in output`() {
        val interpreter =
            StatementInterpreter(
                mapOf(
                    PrintLnNode::class to FunctionStatementInterpreter(),
                    VariableDeclarationNode::class to VariableStatementInterpreter(),
                    AssignationNode::class to VariableStatementInterpreter(),
                ),
            )

        val expectedLog = "This is a test"

        val input = "println(\"$expectedLog\");"
        val ast = getAstFromString(input)

        val output = interpreter.interpret(ast, Environment())

        assertEquals(1, output.logs.size)
        assertEquals("\"$expectedLog\"", output.logs[0])
    }
}
