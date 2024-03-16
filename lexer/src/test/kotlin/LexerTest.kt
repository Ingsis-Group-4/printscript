package lexer

import org.example.lexer.Lexer
import org.example.lexer.getTokenMap
import position.Position
import token.Token
import token.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTest {
    private val lexer = Lexer(getTokenMap())

    private fun assertTokenListEquals(expected: List<Token>, actual: List<Token>){
        assertEquals(expected.size, actual.size)
        for (i in expected.indices){
            assertEquals(expected[i].type, actual[i].type)
            assertEquals(expected[i].start.line, actual[i].start.line)
            assertEquals(expected[i].start.column, actual[i].start.column)
            assertEquals(expected[i].end.line, actual[i].end.line)
            assertEquals(expected[i].end.column, actual[i].end.column)
            assertEquals(expected[i].value, actual[i].value)
        }
    }

    @Test
    fun testCreateLexer(){
        // Give me that 100% coverage
        val testLexer = Lexer(getTokenMap())
        assertEquals(testLexer.tokenMap.size, getTokenMap().size)
    }

    @Test
    fun testLet(){
        val input = "let"
        val expected = listOf(
            Token(TokenType.LET, Position(1, 1), Position(1, 3), "let")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testPrintln(){
        val input = "println"
        val expected = listOf(
            Token(TokenType.PRINTLN, Position(1, 1), Position(1, 7), "println")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNumberType(){
        val input = "Number"
        val expected = listOf(
            Token(TokenType.NUMBERTYPE, Position(1, 1), Position(1, 6), "Number")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testStringType(){
        val input = "String"
        val expected = listOf(
            Token(TokenType.STRINGTYPE, Position(1, 1), Position(1, 6), "String")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testStringLiteral(){
        val input = "\"Hello, World!\""
        val expected = listOf(
            Token(
                TokenType.STRING, Position(1, 1), Position(1, 15), "\"Hello, World!\""
            )
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNumberLiteral(){
        val input = "123"
        val expected = listOf(
            Token(TokenType.NUMBER, Position(1, 1), Position(1, 3), "123")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testColon(){
        val input = ":"
        val expected = listOf(
            Token(TokenType.COLON, Position(1, 1), Position(1, 1), ":")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testAssignment(){
        val input = "="
        val expected = listOf(
            Token(TokenType.ASSIGNATION, Position(1, 1), Position(1, 1), "=")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testCloseParenthesis(){
        val input = ")"
        val expected = listOf(
            Token(TokenType.CLOSEPARENTHESIS, Position(1, 1), Position(1, 1), ")")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testDivision(){
        val input = "/"
        val expected = listOf(
            Token(TokenType.DIVISION, Position(1, 1), Position(1, 1), "/")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testMultiplication(){
        val input = "*"
        val expected = listOf(
            Token(TokenType.MULTIPLICATION, Position(1, 1), Position(1, 1), "*")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testOpenParenthesis(){
        val input = "("
        val expected = listOf(
            Token(TokenType.OPENPARENTHESIS, Position(1, 1), Position(1, 1), "(")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testSemicolon(){
        val input = ";"
        val expected = listOf(
            Token(TokenType.SEMICOLON, Position(1, 1), Position(1, 1), ";")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testSubtraction(){
        val input = "-"
        val expected = listOf(
            Token(TokenType.SUBTRACTION, Position(1, 1), Position(1, 1), "-")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testSum(){
        val input = "+"
        val expected = listOf(
            Token(TokenType.SUM, Position(1, 1), Position(1, 1), "+")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testIdentifier() {
        val input = "myVariable"
        val expected = listOf(
            Token(TokenType.IDENTIFIER, Position(1, 1), Position(1, 10), "myVariable")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNumberDeclarationStatement() {
        val input = "let myVariable: Number = 123;"
        val expected = listOf(
            Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
            Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
            Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
            Token(TokenType.NUMBERTYPE, Position(1, 17), Position(1, 22), "Number"),
            Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
            Token(TokenType.NUMBER, Position(1, 26), Position(1, 28), "123"),
            Token(TokenType.SEMICOLON, Position(1, 29), Position(1, 29), ";")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testStringDeclarationStatement() {
        val input = "let myVariable: String = \"Hello, World!\";"
        val expected = listOf(
            Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
            Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
            Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
            Token(TokenType.STRINGTYPE, Position(1, 17), Position(1, 22), "String"),
            Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
            Token(TokenType.STRING, Position(1, 26), Position(1, 40), "\"Hello, World!\""),
            Token(TokenType.SEMICOLON, Position(1, 41), Position(1, 41), ";")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testAssignationStatement() {
        val input = "myVariable = 123;"
        val expected = listOf(
            Token(TokenType.IDENTIFIER, Position(1, 1), Position(1, 10), "myVariable"),
            Token(TokenType.ASSIGNATION, Position(1, 12), Position(1, 12), "="),
            Token(TokenType.NUMBER, Position(1, 14), Position(1, 16), "123"),
            Token(TokenType.SEMICOLON, Position(1, 17), Position(1, 17), ";")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testPrintlnStatement() {
        val input = "println(\"Hello, World!\");"
        val expected = listOf(
            Token(TokenType.PRINTLN, Position(1, 1), Position(1, 7), "println"),
            Token(TokenType.OPENPARENTHESIS, Position(1, 8), Position(1, 8), "("),
            Token(TokenType.STRING, Position(1, 9), Position(1, 23), "\"Hello, World!\""),
            Token(TokenType.CLOSEPARENTHESIS, Position(1, 24), Position(1, 24), ")"),
            Token(TokenType.SEMICOLON, Position(1, 25), Position(1, 25), ";")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testMultipleStatements() {
        val input = "let myVariable: Number = 123;\nprintln(\"Hello, World!\");"
        val expected = listOf(
            Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
            Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
            Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
            Token(TokenType.NUMBERTYPE, Position(1, 17), Position(1, 22), "Number"),
            Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
            Token(TokenType.NUMBER, Position(1, 26), Position(1, 28), "123"),
            Token(TokenType.SEMICOLON, Position(1, 29), Position(1, 29), ";"),
            Token(TokenType.PRINTLN, Position(2, 1), Position(2, 7), "println"),
            Token(TokenType.OPENPARENTHESIS, Position(2, 8), Position(2, 8), "("),
            Token(TokenType.STRING, Position(2, 9), Position(2, 23), "\"Hello, World!\""),
            Token(TokenType.CLOSEPARENTHESIS, Position(2, 24), Position(2, 24), ")"),
            Token(TokenType.SEMICOLON, Position(2, 25), Position(2, 25), ";")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testAssignationAddition(){
        val input = "sum = 123 + 456;"
        val expected = listOf(
            Token(TokenType.IDENTIFIER, Position(1, 1), Position(1, 3), "sum"),
            Token(TokenType.ASSIGNATION, Position(1, 5), Position(1, 5), "="),
            Token(TokenType.NUMBER, Position(1, 7), Position(1, 9), "123"),
            Token(TokenType.SUM, Position(1, 11), Position(1, 11), "+"),
            Token(TokenType.NUMBER, Position(1, 13), Position(1, 15), "456"),
            Token(TokenType.SEMICOLON, Position(1, 16), Position(1, 16), ";")
        )

        val result = lexer.lex(input)
        assertTokenListEquals(expected, result)
    }
}
