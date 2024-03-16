package interpreter

import org.example.ast.*
import org.example.interpreter.Environment
import org.example.interpreter.ExpressionInterpreter
import org.example.interpreter.NumberValue
import org.example.interpreter.StringValue
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class ExpressionInterpreterTest {

    @Test
    fun testExpressionWithLiteral(){
        val input = LiteralNode(10.0)
        val interpreter = ExpressionInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(10.0, (result as NumberValue).value)
    }

    @Test
    fun testExpressionWithVariable(){
        val input = IdentifierNode("x", VariableType.STRING)

        val environment = Environment()
        environment.createVariable("x", StringValue("a"), VariableType.STRING)

        val interpreter = ExpressionInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is StringValue)
        assertEquals("a", (result as StringValue).value)
    }

    @Test
    fun testExpressionWithSimpleSum(){
        val input = SumNode(LiteralNode(10.0), LiteralNode(20.0))
        val interpreter = ExpressionInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(30.0, (result as NumberValue).value)
    }

    @Test
    fun testExpressionWithSimpleSumOfVariables(){
        val input = SumNode(IdentifierNode("x", VariableType.NUMBER), IdentifierNode("y", VariableType.NUMBER))

        val environment = Environment()
        environment.createVariable("x", NumberValue(10.0), VariableType.NUMBER)
        environment.createVariable("y", NumberValue(20.0), VariableType.NUMBER)

        val interpreter = ExpressionInterpreter(input, environment)

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(30.0, (result as NumberValue).value)
    }

    @Test
    fun testExpressionWithConcatOfStrings(){
        val input = SumNode(LiteralNode("Hello"), LiteralNode("World"))
        val interpreter = ExpressionInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is StringValue)
        assertEquals("HelloWorld", (result as StringValue).value)
    }

    @Test
    fun testExpressionWithSimpleSubtraction(){
        val input = SubtractionNode(LiteralNode(10.0), LiteralNode(20.0))
        val interpreter = ExpressionInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(-10.0, (result as NumberValue).value)
    }

    @Test
    fun testExpressionWithSimpleDivision(){
        val input = DivisionNode(LiteralNode(20.0), LiteralNode(20.0))
        val interpreter = ExpressionInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(1.0, (result as NumberValue).value)
    }

    @Test
    fun testExpressionWithSimpleProduct(){
        val input = ProductNode(LiteralNode(20.0), LiteralNode(20.0))
        val interpreter = ExpressionInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is NumberValue)
        assertEquals(400.0, (result as NumberValue).value)
    }

    @Ignore // We should support this case in the future
    @Test
    fun testExpressionWithSumOfStringsAndNumbers(){
        val input = SumNode(LiteralNode("Hello"), LiteralNode(20.0))
        val interpreter = ExpressionInterpreter(input, Environment())

        val result = interpreter.interpret()

        assert(result is StringValue)
        assertEquals("Hello20.0", (result as StringValue).value)
    }
}