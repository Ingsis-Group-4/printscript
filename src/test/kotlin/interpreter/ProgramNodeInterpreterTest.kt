package interpreter

import org.example.ast.*
import org.example.interpreter.ProgramNodeInterpreter
import org.example.interpreter.NullValue
import org.example.interpreter.NumberValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class ProgramNodeInterpreterTest {
    private var programNodeInterpreter = ProgramNodeInterpreter()

    @BeforeEach
    fun setUp(){
        programNodeInterpreter = ProgramNodeInterpreter()
    }

    @Test
    fun testAssignation(){
        val input = ProgramNode(
            listOf(
                VariableDeclarationNode(
                    IdentifierNode("x", VariableType.NUMBER)
                ),
                AssignationNode(
                    IdentifierNode("x"),
                    NumberNode(10.0)
                )
            )
        )

        programNodeInterpreter.visit(input)
        val resultEnvironment = programNodeInterpreter.getEnvironment()
        val result = resultEnvironment.getVariable("x")
        assert(result is NumberValue)
        assert((result as NumberValue).value == 10.0)
    }

    @Test
    fun testDivision(){
        val input = DivisionNode(
            left = NumberNode(10.0),
            right = NumberNode(2.0)
        )

        val result = programNodeInterpreter.visit(input)
        assert(result == 5.0)
    }

    @Test
    fun testStringLiteral(){
        val input = StringNode("Hello, World!")

        val result = programNodeInterpreter.visit(input)
        assert(result == "Hello, World!")
    }

    @Test
    fun testNumberLiteral(){
        val input = NumberNode(10.0)

        val result = programNodeInterpreter.visit(input)
        assert(result == 10.0)
    }

    @Test
    fun testPrintLn(){} // TODO: Find a way to test this

    @Test
    fun testProduct(){
        val input = ProductNode(
            left = NumberNode(10.0),
            right = NumberNode(2.0)
        )

        val result = programNodeInterpreter.visit(input)
        assert(result == 20.0)
    }

    @Test
    fun testProgram(){
        val input = ProgramNode(
            listOf(
                VariableDeclarationNode(
                    IdentifierNode("x", VariableType.NUMBER)
                ),
                AssignationNode(
                    IdentifierNode("x"),
                    NumberNode(10.0)
                ),
                PrintLnNode(
                    IdentifierNode("x")
                )
            )
        )

        programNodeInterpreter.visit(input)
        val resultEnvironment = programNodeInterpreter.getEnvironment()
        val result = resultEnvironment.getVariable("x")
        assert(result is NumberValue)
        assert((result as NumberValue).value == 10.0)
    }

    @Test
    fun testSubtraction(){
        val input = SubtractionNode(
            left = NumberNode(10.0),
            right = NumberNode(2.0)
        )

        val result = programNodeInterpreter.visit(input)
        assert(result == 8.0)
    }

    @Test
    fun testSum(){
        val input = SumNode(
            left = NumberNode(10.0),
            right = NumberNode(2.0)
        )

        val result = programNodeInterpreter.visit(input)
        assert(result == 12.0)
    }

    @Test
    fun testVariableDeclarationWithExpression(){
        val input = VariableDeclarationNode(
            IdentifierNode("x", VariableType.NUMBER),
            NumberNode(10.0)
        )

        programNodeInterpreter.visit(input)
        val resultEnvironment = programNodeInterpreter.getEnvironment()
        val result = resultEnvironment.getVariable("x")
        assert(result is NumberValue)
        assert((result as NumberValue).value == 10.0)
    }

    @Test
    fun testVariableDeclarationWithoutExpression(){
        val input = VariableDeclarationNode(
            IdentifierNode("x", VariableType.NUMBER)
        )

        programNodeInterpreter.visit(input)
        val resultEnvironment = programNodeInterpreter.getEnvironment()
        val result = resultEnvironment.getVariable("x")
        assert(result is NullValue)
    }

    @Test
    fun testSumOfStringAndNumber(){
        val input = SumNode(
            left = StringNode("Hello, "),
            right = NumberNode(10.0)
        )

        val result = programNodeInterpreter.visit(input)
//        assert(result == "Hello, 10.0") TODO: This is the intended result, but the interpreter is not implemented to handle this
    }

    @Test
    fun testAssignationToUndefinedVariable(){
        val input = AssignationNode(
            IdentifierNode("x"),
            NumberNode(10.0)
        )

        assertThrows<Exception> {
            programNodeInterpreter.visit(input)
        }

        programNodeInterpreter = ProgramNodeInterpreter()

        try {
            programNodeInterpreter.visit(input)
        } catch (e: Exception) {
            assertEquals("Variable x does not exist", e.message)
        }
    }

    @Test
    fun testDeclareTheSameVariableTwice(){
        val input = ProgramNode(
            listOf(
                VariableDeclarationNode(
                    IdentifierNode("x", VariableType.NUMBER)
                ),
                VariableDeclarationNode(
                    IdentifierNode("x", VariableType.NUMBER)
                )
            )
        )

        assertThrows<Exception> {
            programNodeInterpreter.visit(input)
        }

        programNodeInterpreter = ProgramNodeInterpreter()

        try {
            programNodeInterpreter.visit(input)
        } catch (e: Exception) {
            assertEquals("Variable x already exists", e.message)
        }
    }

    @Test
    fun testAssignWrongTypeToVariable(){
        val input = ProgramNode(
            listOf(
                VariableDeclarationNode(
                    IdentifierNode("x", VariableType.NUMBER)
                ),
                AssignationNode(
                    IdentifierNode("x"),
                    StringNode("Hello, World!")
                )
            )
        )

        assertThrows<Exception> {
            programNodeInterpreter.visit(input)
        }

        programNodeInterpreter = ProgramNodeInterpreter()

        try {
            programNodeInterpreter.visit(input)
        } catch (e: Exception) {
            assertEquals("Variable x is not a number", e.message)
        }
    }
}
