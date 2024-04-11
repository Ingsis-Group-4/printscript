package parser

import ast.AssignationNode
import ast.DivisionNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.ProductNode
import ast.ProgramNode
import ast.StatementNode
import ast.SubtractionNode
import ast.SumNode
import ast.VariableDeclarationNode
import parser.factory.*
import parser.result.SuccessResult
import position.Position
import token.Token
import token.TokenType
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ParserTest {
    @Test
    fun testAssignationWithOneStatement() {
        val parser = ProgramParserFactory.create()
        val input =
            listOf(
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 1),
                    Position(1, 1),
                    "a",
                ),
                Token(
                    TokenType.ASSIGNATION,
                    Position(1, 2),
                    Position(1, 2),
                    "=",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "1",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(1, 4),
                    Position(1, 4),
                    ";",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<ProgramNode>(result.value)
        assertIs<StatementNode>((result.value as ProgramNode).statements[0])
    }

    @Test
    fun testProgramParserWithTwoStatements() {
        val parser = ProgramParserFactory.create()
        val input =
            listOf(
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 1),
                    Position(1, 1),
                    "a",
                ),
                Token(
                    TokenType.ASSIGNATION,
                    Position(1, 2),
                    Position(1, 2),
                    "=",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "1",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(1, 4),
                    Position(1, 4),
                    ";",
                ),
                Token(
                    TokenType.IDENTIFIER,
                    Position(2, 1),
                    Position(2, 1),
                    "b",
                ),
                Token(
                    TokenType.ASSIGNATION,
                    Position(2, 2),
                    Position(2, 2),
                    "=",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(2, 3),
                    Position(2, 3),
                    "2",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(2, 4),
                    Position(2, 4),
                    ";",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<ProgramNode>(result.value)
        assertEquals(2, (result.value as ProgramNode).statements.size)
        assertIs<StatementNode>((result.value as ProgramNode).statements[0])
        assertIs<StatementNode>((result.value as ProgramNode).statements[1])
    }

    @Test
    fun testVariableAssignationParser() {
        val parser = AssignationParserFactory.create()
        val input =
            listOf(
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 1),
                    Position(1, 1),
                    "a",
                ),
                Token(
                    TokenType.ASSIGNATION,
                    Position(1, 2),
                    Position(1, 2),
                    "=",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "1",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(1, 4),
                    Position(1, 4),
                    ";",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<AssignationNode>(result.value)
        assertEquals("a", (result.value as AssignationNode).identifier.variableName)
        assertIs<LiteralNode<Number>>((result.value as AssignationNode).expression)
    }

    @Test
    fun testVariableDeclarationParserWithoutAssignation() {
        val parser = VariableDeclarationParserFactory.create()
        val input =
            listOf( // let a: Number;
                Token(
                    TokenType.LET,
                    Position(1, 1),
                    Position(1, 1),
                    "let",
                ),
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 5),
                    Position(1, 5),
                    "a",
                ),
                Token(
                    TokenType.COLON,
                    Position(1, 6),
                    Position(1, 6),
                    ":",
                ),
                Token(
                    TokenType.NUMBERTYPE,
                    Position(1, 7),
                    Position(1, 7),
                    "Number",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(1, 8),
                    Position(1, 8),
                    ";",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<VariableDeclarationNode>(result.value)
        assertEquals("a", (result.value as VariableDeclarationNode).identifier.variableName)
    }

    @Test
    fun testVariableDeclarationParserWithAssignation() {
        val parser = VariableDeclarationParserFactory.create()
        val input =
            listOf( // let a: Number = 1;
                Token(
                    TokenType.LET,
                    Position(1, 1),
                    Position(1, 1),
                    "let",
                ),
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 5),
                    Position(1, 5),
                    "a",
                ),
                Token(
                    TokenType.COLON,
                    Position(1, 6),
                    Position(1, 6),
                    ":",
                ),
                Token(
                    TokenType.NUMBERTYPE,
                    Position(1, 7),
                    Position(1, 7),
                    "number",
                ),
                Token(
                    TokenType.ASSIGNATION,
                    Position(1, 9),
                    Position(1, 9),
                    "=",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 11),
                    Position(1, 11),
                    "1",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(1, 12),
                    Position(1, 12),
                    ";",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<VariableDeclarationNode>(result.value)
        assertEquals("a", (result.value as VariableDeclarationNode).identifier.variableName)
        assertIs<LiteralNode<Number>>((result.value as VariableDeclarationNode).expression)
    }

    @Test
    fun testPrintLnParser() {
        val parser = PrintLnParserFactory.create()
        val input =
            listOf(
                Token(
                    TokenType.PRINTLN,
                    Position(1, 1),
                    Position(1, 1),
                    "println",
                ),
                Token(
                    TokenType.OPENPARENTHESIS,
                    Position(1, 8),
                    Position(1, 8),
                    "(",
                ),
                Token(
                    TokenType.STRING,
                    Position(1, 9),
                    Position(1, 9),
                    "Hello, World!",
                ),
                Token(
                    TokenType.CLOSEPARENTHESIS,
                    Position(1, 21),
                    Position(1, 21),
                    ")",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(1, 22),
                    Position(1, 22),
                    ";",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<PrintLnNode>(result.value)
        assertIs<LiteralNode<String>>((result.value as PrintLnNode).expression)
        assertEquals("Hello, World!", ((result.value as PrintLnNode).expression as LiteralNode<*>).value)
    }

    @Test
    fun testExpressionParserWithNumberLiteral() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 1),
                    Position(1, 1),
                    "1",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<LiteralNode<Double>>(result.value)
        assertEquals(1.0, (result.value as LiteralNode<*>).value)
    }

    @Test
    fun testExpressionParserWithStringLiteral() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.STRING,
                    Position(1, 1),
                    Position(1, 1),
                    "Hello, World!",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<LiteralNode<String>>(result.value)
        assertEquals("Hello, World!", (result.value as LiteralNode<*>).value)
    }

    @Test
    fun testExpressionParserWithIdentifier() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 1),
                    Position(1, 1),
                    "a",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<IdentifierNode>(result.value)
        assertEquals("a", (result.value as IdentifierNode).variableName)
    }

    @Test
    fun testExpressionParserWithSum() {
        val parser = ExpressionParserV2Factory.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 1),
                    Position(1, 1),
                    "1",
                ),
                Token(
                    TokenType.SUM,
                    Position(1, 2),
                    Position(1, 2),
                    "+",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "2",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)
        assertIs<LiteralNode<Double>>((result.value as SumNode).left)
        assertIs<LiteralNode<Double>>((result.value as SumNode).right)
    }

    @Test
    fun testExpressionParserWithSubtraction() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 1),
                    Position(1, 1),
                    "1",
                ),
                Token(
                    TokenType.SUBTRACTION,
                    Position(1, 2),
                    Position(1, 2),
                    "-",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "2",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SubtractionNode>(result.value)
        assertIs<LiteralNode<Double>>((result.value as SubtractionNode).left)
        assertIs<LiteralNode<Double>>((result.value as SubtractionNode).right)
    }

    @Test
    fun testExpressionParserWithMultiplication() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 1),
                    Position(1, 1),
                    "1",
                ),
                Token(
                    TokenType.MULTIPLICATION,
                    Position(1, 2),
                    Position(1, 2),
                    "*",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "2",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<ProductNode>(result.value)
        assertIs<LiteralNode<Double>>((result.value as ProductNode).left)
        assertIs<LiteralNode<Double>>((result.value as ProductNode).right)
    }

    @Test
    fun testExpressionParserWithDivision() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 1),
                    Position(1, 1),
                    "1",
                ),
                Token(
                    TokenType.DIVISION,
                    Position(1, 2),
                    Position(1, 2),
                    "/",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "2",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<DivisionNode>(result.value)
        assertIs<LiteralNode<Double>>((result.value as DivisionNode).left)
        assertIs<LiteralNode<Double>>((result.value as DivisionNode).right)
    }

    @Test
    fun testExpressionParserWithSumBetweenNumberAndIdentifier() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 1),
                    Position(1, 1),
                    "1",
                ),
                Token(
                    TokenType.SUM,
                    Position(1, 2),
                    Position(1, 2),
                    "+",
                ),
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 3),
                    Position(1, 3),
                    "a",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)
        assertIs<LiteralNode<Double>>((result.value as SumNode).left)
        assertIs<IdentifierNode>((result.value as SumNode).right)
    }

    @Test
    fun testExpressionParserWithTwoSumsInOneStatement() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 1),
                    Position(1, 1),
                    "1",
                ),
                Token(
                    TokenType.SUM,
                    Position(1, 2),
                    Position(1, 2),
                    "+",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 3),
                    Position(1, 3),
                    "2",
                ),
                Token(
                    TokenType.SUM,
                    Position(1, 4),
                    Position(1, 4),
                    "+",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 5),
                    Position(1, 5),
                    "3",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<SumNode>(result.value)
        assertIs<SumNode>((result.value as SumNode).left)
        assertIs<LiteralNode<Double>>(((result.value as SumNode).left as SumNode).left)
        assertIs<LiteralNode<Double>>(((result.value as SumNode).left as SumNode).right)
        assertIs<LiteralNode<Double>>((result.value as SumNode).right)
    }

    @Ignore
    @Test
    fun testExpressionParserWithMultiplicationAndParenthesis() {
        val parser = ExpressionParserFactoryV3.create()
        val input =
            listOf(
                Token(
                    TokenType.NUMBER,
                    Position(1, 7),
                    Position(1, 7),
                    "3",
                ),
                Token(
                    TokenType.MULTIPLICATION,
                    Position(1, 6),
                    Position(1, 6),
                    "*",
                ),
                Token(
                    TokenType.OPENPARENTHESIS,
                    Position(1, 1),
                    Position(1, 1),
                    "(",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 2),
                    Position(1, 2),
                    "1",
                ),
                Token(
                    TokenType.SUM,
                    Position(1, 3),
                    Position(1, 3),
                    "+",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 4),
                    Position(1, 4),
                    "2",
                ),
                Token(
                    TokenType.CLOSEPARENTHESIS,
                    Position(1, 5),
                    Position(1, 5),
                    ")",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<ProductNode>(result.value)
        assertIs<SumNode>((result.value as ProductNode).left)
        assertIs<LiteralNode<Double>>(((result.value as ProductNode).left as SumNode).left)
        assertIs<LiteralNode<Double>>(((result.value as ProductNode).left as SumNode).right)
        assertIs<LiteralNode<Double>>((result.value as ProductNode).right)
    }

    @Test
    fun testJuanse() {
        val parser = VariableDeclarationParserFactory.create()
        val input =
            listOf( // let a: Number = 1;
                Token(
                    TokenType.LET,
                    Position(1, 1),
                    Position(1, 1),
                    "let",
                ),
                Token(
                    TokenType.IDENTIFIER,
                    Position(1, 5),
                    Position(1, 5),
                    "a",
                ),
                Token(
                    TokenType.COLON,
                    Position(1, 6),
                    Position(1, 6),
                    ":",
                ),
                Token(
                    TokenType.NUMBERTYPE,
                    Position(1, 7),
                    Position(1, 7),
                    "number",
                ),
                Token(
                    TokenType.ASSIGNATION,
                    Position(1, 9),
                    Position(1, 9),
                    "=",
                ),
                Token(
                    TokenType.NUMBER,
                    Position(1, 11),
                    Position(1, 11),
                    "1",
                ),
                Token(
                    TokenType.SEMICOLON,
                    Position(1, 12),
                    Position(1, 12),
                    ";",
                ),
            )

        val result = parser.parse(input, 0)

        assertIs<SuccessResult>(result)
        assertIs<VariableDeclarationNode>(result.value)
        assertEquals("a", (result.value as VariableDeclarationNode).identifier.variableName)
        assertIs<LiteralNode<Number>>((result.value as VariableDeclarationNode).expression)
    }
}