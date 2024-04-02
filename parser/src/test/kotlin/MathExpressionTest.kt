package parser

import ast.LiteralNode
import ast.ProductNode
import ast.SubtractionNode
import ast.SumNode
import org.junit.jupiter.api.Test
import parser.result.SuccessResult
import position.Position
import token.Token
import token.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertIs

class MathExpressionTest {
    private val expressionParser = ExpressionParser()

    @Test
    fun test_2() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "1"),
                Token(TokenType.SEMICOLON, Position(1, 2), Position(1, 2), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<LiteralNode<Number>>(result.value)

        assertEquals(1.0, (result.value as LiteralNode<*>).value)
    }

    @Test
    fun `test_3+2`() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "3"),
                Token(TokenType.SUM, Position(1, 2), Position(1, 2), "+"),
                Token(TokenType.NUMBER, Position(1, 3), Position(1, 3), "2"),
                Token(TokenType.SEMICOLON, Position(1, 4), Position(1, 4), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)

        val sumNode = result.value as SumNode
        assertIs<LiteralNode<Number>>(sumNode.getLeft())
        assertIs<LiteralNode<Number>>(sumNode.getRight())

        assertEquals(3.0, (sumNode.getLeft() as LiteralNode<*>).value)
        assertEquals(2.0, (sumNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun `test_4-8`() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "4"),
                Token(TokenType.SUBTRACTION, Position(1, 2), Position(1, 2), "-"),
                Token(TokenType.NUMBER, Position(1, 3), Position(1, 3), "8"),
                Token(TokenType.SEMICOLON, Position(1, 4), Position(1, 4), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SubtractionNode>(result.value)

        val subtractionNode = result.value as SubtractionNode
        assertIs<LiteralNode<Number>>(subtractionNode.getLeft())
        assertIs<LiteralNode<Number>>(subtractionNode.getRight())

        assertEquals(4.0, (subtractionNode.getLeft() as LiteralNode<*>).value)
        assertEquals(8.0, (subtractionNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun `test_3+2+1`() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "3"),
                Token(TokenType.SUM, Position(1, 2), Position(1, 2), "+"),
                Token(TokenType.NUMBER, Position(1, 3), Position(1, 3), "2"),
                Token(TokenType.SUM, Position(1, 4), Position(1, 4), "+"),
                Token(TokenType.NUMBER, Position(1, 5), Position(1, 5), "1"),
                Token(TokenType.SEMICOLON, Position(1, 6), Position(1, 6), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)

        val sumNode = result.value as SumNode
        assertIs<SumNode>(sumNode.getLeft())
        assertIs<LiteralNode<Number>>(sumNode.getRight())

        assertEquals(1.0, (sumNode.getRight() as LiteralNode<*>).value)

        val leftSumNode = sumNode.getLeft() as SumNode

        assertIs<LiteralNode<Number>>(leftSumNode.getLeft())
        assertIs<LiteralNode<Number>>(leftSumNode.getRight())

        assertEquals(3.0, (leftSumNode.getLeft() as LiteralNode<*>).value)
        assertEquals(2.0, (leftSumNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun `test_3-2+1`() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "3"),
                Token(TokenType.SUBTRACTION, Position(1, 2), Position(1, 2), "-"),
                Token(TokenType.NUMBER, Position(1, 3), Position(1, 3), "2"),
                Token(TokenType.SUM, Position(1, 4), Position(1, 4), "+"),
                Token(TokenType.NUMBER, Position(1, 5), Position(1, 5), "1"),
                Token(TokenType.SEMICOLON, Position(1, 6), Position(1, 6), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)

        val sumNode = result.value as SumNode
        assertIs<SubtractionNode>(sumNode.getLeft())
        assertIs<LiteralNode<Number>>(sumNode.getRight())

        assertEquals(1.0, (sumNode.getRight() as LiteralNode<*>).value)

        val leftSubtractionNode = sumNode.getLeft() as SubtractionNode

        assertIs<LiteralNode<Number>>(leftSubtractionNode.getLeft())
        assertIs<LiteralNode<Number>>(leftSubtractionNode.getRight())

        assertEquals(3.0, (leftSubtractionNode.getLeft() as LiteralNode<*>).value)
        assertEquals(2.0, (leftSubtractionNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun `test_4+3x2`() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "4"),
                Token(TokenType.SUM, Position(1, 2), Position(1, 2), "+"),
                Token(TokenType.NUMBER, Position(1, 3), Position(1, 3), "3"),
                Token(TokenType.MULTIPLICATION, Position(1, 4), Position(1, 4), "*"),
                Token(TokenType.NUMBER, Position(1, 5), Position(1, 5), "2"),
                Token(TokenType.SEMICOLON, Position(1, 6), Position(1, 6), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)

        val sumNode = result.value as SumNode
        assertIs<LiteralNode<Number>>(sumNode.getLeft())
        assertIs<ProductNode>(sumNode.getRight())

        assertEquals(4.0, (sumNode.getLeft() as LiteralNode<*>).value)

        val productNode = sumNode.getRight() as ProductNode

        assertIs<LiteralNode<Number>>(productNode.getLeft())
        assertIs<LiteralNode<Number>>(productNode.getRight())

        assertEquals(3.0, (productNode.getLeft() as LiteralNode<*>).value)
        assertEquals(2.0, (productNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun `test_4x3+2`() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "4"),
                Token(TokenType.MULTIPLICATION, Position(1, 2), Position(1, 2), "*"),
                Token(TokenType.NUMBER, Position(1, 3), Position(1, 3), "3"),
                Token(TokenType.SUM, Position(1, 4), Position(1, 4), "+"),
                Token(TokenType.NUMBER, Position(1, 5), Position(1, 5), "2"),
                Token(TokenType.SEMICOLON, Position(1, 6), Position(1, 6), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)

        val sumNode = result.value as SumNode
        assertIs<ProductNode>(sumNode.getLeft())
        assertIs<LiteralNode<Number>>(sumNode.getRight())

        assertEquals(2.0, (sumNode.getRight() as LiteralNode<*>).value)

        val productNode = sumNode.getLeft() as ProductNode

        assertIs<LiteralNode<Number>>(productNode.getLeft())
        assertIs<LiteralNode<Number>>(productNode.getRight())

        assertEquals(4.0, (productNode.getLeft() as LiteralNode<*>).value)
        assertEquals(3.0, (productNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun test_2x3x4() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "2"),
                Token(TokenType.MULTIPLICATION, Position(1, 2), Position(1, 2), "*"),
                Token(TokenType.NUMBER, Position(1, 3), Position(1, 3), "3"),
                Token(TokenType.MULTIPLICATION, Position(1, 4), Position(1, 4), "*"),
                Token(TokenType.NUMBER, Position(1, 5), Position(1, 5), "4"),
                Token(TokenType.SEMICOLON, Position(1, 6), Position(1, 6), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<ProductNode>(result.value)

        val productNode = result.value as ProductNode

        assertIs<ProductNode>(productNode.getLeft())
        assertIs<LiteralNode<Number>>(productNode.getRight())

        assertEquals(4.0, (productNode.getRight() as LiteralNode<*>).value)

        val leftProductNode = productNode.getLeft() as ProductNode

        assertIs<LiteralNode<Number>>(leftProductNode.getLeft())
        assertIs<LiteralNode<Number>>(leftProductNode.getRight())

        assertEquals(2.0, (leftProductNode.getLeft() as LiteralNode<*>).value)
        assertEquals(3.0, (leftProductNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun `test_(2+3)x4`() {
        val input =
            listOf(
                Token(TokenType.OPENPARENTHESIS, Position(1, 1), Position(1, 1), "("),
                Token(TokenType.NUMBER, Position(1, 2), Position(1, 2), "2"),
                Token(TokenType.SUM, Position(1, 3), Position(1, 3), "+"),
                Token(TokenType.NUMBER, Position(1, 4), Position(1, 4), "3"),
                Token(TokenType.CLOSEPARENTHESIS, Position(1, 5), Position(1, 5), ")"),
                Token(TokenType.MULTIPLICATION, Position(1, 6), Position(1, 6), "*"),
                Token(TokenType.NUMBER, Position(1, 7), Position(1, 7), "4"),
                Token(TokenType.SEMICOLON, Position(1, 8), Position(1, 8), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<ProductNode>(result.value)

        val productNode = result.value as ProductNode

        assertIs<SumNode>(productNode.getLeft())
        assertIs<LiteralNode<Number>>(productNode.getRight())

        assertEquals(4.0, (productNode.getRight() as LiteralNode<*>).value)

        val sumNode = productNode.getLeft() as SumNode

        assertIs<LiteralNode<Number>>(sumNode.getLeft())
        assertIs<LiteralNode<Number>>(sumNode.getRight())

        assertEquals(2.0, (sumNode.getLeft() as LiteralNode<*>).value)
        assertEquals(3.0, (sumNode.getRight() as LiteralNode<*>).value)
    }

    @Test
    fun `test_2x(3+4)`() {
        val input =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 1), "2"),
                Token(TokenType.MULTIPLICATION, Position(1, 2), Position(1, 2), "*"),
                Token(TokenType.OPENPARENTHESIS, Position(1, 3), Position(1, 3), "("),
                Token(TokenType.NUMBER, Position(1, 4), Position(1, 4), "3"),
                Token(TokenType.SUM, Position(1, 5), Position(1, 5), "+"),
                Token(TokenType.NUMBER, Position(1, 6), Position(1, 6), "4"),
                Token(TokenType.CLOSEPARENTHESIS, Position(1, 7), Position(1, 7), ")"),
                Token(TokenType.SEMICOLON, Position(1, 8), Position(1, 8), ";"),
            )

        val result = expressionParser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<ProductNode>(result.value)

        val productNode = result.value as ProductNode

        assertIs<LiteralNode<Number>>(productNode.getLeft())
        assertIs<SumNode>(productNode.getRight())

        assertEquals(2.0, (productNode.getLeft() as LiteralNode<*>).value)

        val sumNode = productNode.getRight() as SumNode

        assertIs<LiteralNode<Number>>(sumNode.getLeft())
        assertIs<LiteralNode<Number>>(sumNode.getRight())

        assertEquals(3.0, (sumNode.getLeft() as LiteralNode<*>).value)
        assertEquals(4.0, (sumNode.getRight() as LiteralNode<*>).value)
    }
}
