package parser

import ast.AssignationNode
import ast.PrintLnNode
import org.example.parser.AssignationParser
import org.example.parser.factory.AssignationParserFactory
import org.example.parser.factory.PrintLnParserFactory
import org.example.parser.factory.ProgramParserFactory
import org.example.parser.factory.VariableDeclarationParserFactory
import org.example.parser.result.SuccessResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import position.Position
import token.Token
import token.TokenType
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class PositionTest {

    @Test
    fun testAssignationParserPosition(){
        val parser = AssignationParserFactory.create()
        val tokens = listOf(
            Token(TokenType.IDENTIFIER, Position(1, 1), Position(1, 1), "a"),
            Token(TokenType.ASSIGNATION, Position(1, 3), Position(1, 3), "="),
            Token(TokenType.NUMBER, Position(1, 5), Position(1, 5), "1"),
            Token(TokenType.SEMICOLON, Position(1, 7), Position(1, 7), ";")
        )

        val result = parser.parse(tokens, 0)

        assertIs<SuccessResult>(result)
        val ast = result.value
        assertEquals(Position(1, 1), ast.getStart())
        assertEquals(Position(1, 7), ast.getEnd())

        assertIs<AssignationNode>(ast)
        val identifierNode = ast.identifier
        val expressionNode = ast.expression

        assertEquals(Position(1, 1), identifierNode.getStart())
        assertEquals(Position(1, 1), identifierNode.getEnd())
        assertEquals(Position(1, 5), expressionNode.getStart())
        assertEquals(Position(1, 5), expressionNode.getEnd())
    }

    @Test
    fun testVariableDeclarationWithExpressionPosition(){
        val parser = VariableDeclarationParserFactory.create()
        val tokens = listOf(
            Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
            Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 5), "a"),
            Token(TokenType.COLON, Position(1, 7), Position(1, 7), ":"),
            Token(TokenType.NUMBERTYPE, Position(1, 9), Position(1, 15), "number"),
            Token(TokenType.ASSIGNATION, Position(1, 17), Position(1, 17), "="),
            Token(TokenType.NUMBER, Position(1, 19), Position(1, 19), "1"),
            Token(TokenType.SEMICOLON, Position(1, 21), Position(1, 21), ";")
        )

        val result = parser.parse(tokens, 0)

        assertIs<SuccessResult>(result)
        val ast = result.value
        assertEquals(Position(1, 1), ast.getStart())
        assertEquals(Position(1, 21), ast.getEnd())

        assertIs<ast.VariableDeclarationNode>(ast)
        val identifierNode = ast.identifier

        assertNotNull(ast.expression)
        val expressionNode = ast.expression!!

        assertEquals(Position(1, 5), identifierNode.getStart())
        assertEquals(Position(1, 15), identifierNode.getEnd())
        assertEquals(Position(1, 19), expressionNode.getStart())
        assertEquals(Position(1, 19), expressionNode.getEnd())
    }

    @Test
    fun testVariableDeclarationWithoutExpressionPosition(){
        val parser = VariableDeclarationParserFactory.create()
        val tokens = listOf(
            Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
            Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 5), "a"),
            Token(TokenType.COLON, Position(1, 7), Position(1, 7), ":"),
            Token(TokenType.STRINGTYPE, Position(1, 9), Position(1, 15), "string"),
            Token(TokenType.SEMICOLON, Position(1, 16), Position(1, 16), ";")
        )

        val result = parser.parse(tokens, 0)

        assertIs<SuccessResult>(result)
        val ast = result.value
        assertEquals(Position(1, 1), ast.getStart())
        assertEquals(Position(1, 16), ast.getEnd())
    }

    @Test
    fun testPrintlnParserPosition(){
        val parser = PrintLnParserFactory.create()
        val tokens = listOf(
            Token(TokenType.PRINTLN, Position(1, 1), Position(1, 7), "println"),
            Token(TokenType.OPENPARENTHESIS, Position(1, 8), Position(1, 8), "("),
            Token(TokenType.NUMBER, Position(1, 9), Position(1, 9), "1"),
            Token(TokenType.CLOSEPARENTHESIS, Position(1, 10), Position(1, 10), ")"),
            Token(TokenType.SEMICOLON, Position(1, 11), Position(1, 11), ";")
        )

        val result = parser.parse(tokens, 0)

        assertIs<SuccessResult>(result)
        val ast = result.value
        assertEquals(Position(1, 1), ast.getStart())
        assertEquals(Position(1, 11), ast.getEnd())

        assertIs<PrintLnNode>(ast)

        assertNotNull(ast.expression)
        val expressionNode = ast.expression

        assertEquals(Position(1, 9), expressionNode.getStart())
        assertEquals(Position(1, 9), expressionNode.getEnd())

    }

    @Test
    fun testProgramParserPosition(){
        val parser = ProgramParserFactory.create()
        val tokens = listOf(
            Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
            Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 5), "a"),
            Token(TokenType.COLON, Position(1, 7), Position(1, 7), ":"),
            Token(TokenType.NUMBERTYPE, Position(1, 9), Position(1, 9), "number"),
            Token(TokenType.SEMICOLON, Position(1, 15), Position(1, 15), ";"),
            Token(TokenType.PRINTLN, Position(2, 1), Position(2, 7), "println"),
            Token(TokenType.OPENPARENTHESIS, Position(2, 8), Position(2, 8), "("),
            Token(TokenType.NUMBER, Position(2, 9), Position(2, 9), "1"),
            Token(TokenType.CLOSEPARENTHESIS, Position(2, 10), Position(2, 10), ")"),
            Token(TokenType.SEMICOLON, Position(2, 11), Position(2, 11), ";")
        )

        val result = parser.parse(tokens, 0)

        assertIs<SuccessResult>(result)
        val ast = result.value
        assertEquals(Position(1, 1), ast.getStart())
        assertEquals(Position(2, 11), ast.getEnd())
    }
}