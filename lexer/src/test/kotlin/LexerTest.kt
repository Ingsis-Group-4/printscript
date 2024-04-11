package lexer

import position.Position
import token.Token
import token.TokenType
import version.Version
import kotlin.test.Test
import kotlin.test.assertEquals

class LexerTest {
    private val lexerFirstVersion = Lexer(getTokenMap(Version.V1))
    private val lexerSecondVersion = Lexer(getTokenMap(Version.V2))

    private fun assertTokenListEquals(
        expected: List<Token>,
        actual: List<Token>,
    ) {
        assertEquals(expected.size, actual.size)
        for (i in expected.indices) {
            assertEquals(expected[i].type, actual[i].type)
            assertEquals(expected[i].start.line, actual[i].start.line)
            assertEquals(expected[i].start.column, actual[i].start.column)
            assertEquals(expected[i].end.line, actual[i].end.line)
            assertEquals(expected[i].end.column, actual[i].end.column)
            assertEquals(expected[i].value, actual[i].value)
        }
    }

    @Test
    fun testCreateLexer() {
        // Give me that 100% coverage
        val testLexer = Lexer(getTokenMap(Version.V1))
        assertEquals(testLexer.tokenMap.size, getTokenMap(Version.V1).size)
    }

    @Test
    fun testLet() {
        val input = "let"
        val expected =
            listOf(
                Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testPrintln() {
        val input = "println"
        val expected =
            listOf(
                Token(TokenType.PRINTLN, Position(1, 1), Position(1, 7), "println"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNumberType() {
        val input = "Number"
        val expected =
            listOf(
                Token(TokenType.NUMBERTYPE, Position(1, 1), Position(1, 6), "Number"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testStringType() {
        val input = "String"
        val expected =
            listOf(
                Token(TokenType.STRINGTYPE, Position(1, 1), Position(1, 6), "String"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testStringLiteral() {
        val input = "\"Hello, World!\""
        val expected =
            listOf(
                Token(
                    TokenType.STRING,
                    Position(1, 1),
                    Position(1, 15),
                    "\"Hello, World!\"",
                ),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNumberLiteral() {
        val input = "123"
        val expected =
            listOf(
                Token(TokenType.NUMBER, Position(1, 1), Position(1, 3), "123"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testColon() {
        val input = ":"
        val expected =
            listOf(
                Token(TokenType.COLON, Position(1, 1), Position(1, 1), ":"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testAssignment() {
        val input = "="
        val expected =
            listOf(
                Token(TokenType.ASSIGNATION, Position(1, 1), Position(1, 1), "="),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testCloseParenthesis() {
        val input = ")"
        val expected =
            listOf(
                Token(TokenType.CLOSEPARENTHESIS, Position(1, 1), Position(1, 1), ")"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testDivision() {
        val input = "/"
        val expected =
            listOf(
                Token(TokenType.DIVISION, Position(1, 1), Position(1, 1), "/"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testMultiplication() {
        val input = "*"
        val expected =
            listOf(
                Token(TokenType.MULTIPLICATION, Position(1, 1), Position(1, 1), "*"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testOpenParenthesis() {
        val input = "("
        val expected =
            listOf(
                Token(TokenType.OPENPARENTHESIS, Position(1, 1), Position(1, 1), "("),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testSemicolon() {
        val input = ";"
        val expected =
            listOf(
                Token(TokenType.SEMICOLON, Position(1, 1), Position(1, 1), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testSubtraction() {
        val input = "-"
        val expected =
            listOf(
                Token(TokenType.SUBTRACTION, Position(1, 1), Position(1, 1), "-"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testSum() {
        val input = "+"
        val expected =
            listOf(
                Token(TokenType.SUM, Position(1, 1), Position(1, 1), "+"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testIdentifier() {
        val input = "myVariable"
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, Position(1, 1), Position(1, 10), "myVariable"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNumberDeclarationStatement() {
        val input = "let myVariable: Number = 123;"
        val expected =
            listOf(
                Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
                Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
                Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
                Token(TokenType.NUMBERTYPE, Position(1, 17), Position(1, 22), "Number"),
                Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
                Token(TokenType.NUMBER, Position(1, 26), Position(1, 28), "123"),
                Token(TokenType.SEMICOLON, Position(1, 29), Position(1, 29), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testDecimalNumberDeclarationStatement() {
        val input = "let myVariable: Number = 12.3;"
        val expected =
            listOf(
                Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
                Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
                Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
                Token(TokenType.NUMBERTYPE, Position(1, 17), Position(1, 22), "Number"),
                Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
                Token(TokenType.NUMBER, Position(1, 26), Position(1, 29), "12.3"),
                Token(TokenType.SEMICOLON, Position(1, 30), Position(1, 30), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNegativeNumberDeclarationStatement() {
        val input = "let myVariable: Number = -22;"
        val expected =
            listOf(
                Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
                Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
                Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
                Token(TokenType.NUMBERTYPE, Position(1, 17), Position(1, 22), "Number"),
                Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
                Token(TokenType.NUMBER, Position(1, 26), Position(1, 28), "-22"),
                Token(TokenType.SEMICOLON, Position(1, 29), Position(1, 29), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testNegativeDecimalNumberDeclarationStatement() {
        val input = "let myVariable: Number = -2.2;"
        val expected =
            listOf(
                Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
                Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
                Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
                Token(TokenType.NUMBERTYPE, Position(1, 17), Position(1, 22), "Number"),
                Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
                Token(TokenType.NUMBER, Position(1, 26), Position(1, 29), "-2.2"),
                Token(TokenType.SEMICOLON, Position(1, 30), Position(1, 30), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testStringDeclarationStatement() {
        val input = "let myVariable: String = \"Hello, World!\";"
        val expected =
            listOf(
                Token(TokenType.LET, Position(1, 1), Position(1, 3), "let"),
                Token(TokenType.IDENTIFIER, Position(1, 5), Position(1, 14), "myVariable"),
                Token(TokenType.COLON, Position(1, 15), Position(1, 15), ":"),
                Token(TokenType.STRINGTYPE, Position(1, 17), Position(1, 22), "String"),
                Token(TokenType.ASSIGNATION, Position(1, 24), Position(1, 24), "="),
                Token(TokenType.STRING, Position(1, 26), Position(1, 40), "\"Hello, World!\""),
                Token(TokenType.SEMICOLON, Position(1, 41), Position(1, 41), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testAssignationStatement() {
        val input = "myVariable = 123;"
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, Position(1, 1), Position(1, 10), "myVariable"),
                Token(TokenType.ASSIGNATION, Position(1, 12), Position(1, 12), "="),
                Token(TokenType.NUMBER, Position(1, 14), Position(1, 16), "123"),
                Token(TokenType.SEMICOLON, Position(1, 17), Position(1, 17), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testPrintlnStatement() {
        val input = "println(\"Hello, World!\");"
        val expected =
            listOf(
                Token(TokenType.PRINTLN, Position(1, 1), Position(1, 7), "println"),
                Token(TokenType.OPENPARENTHESIS, Position(1, 8), Position(1, 8), "("),
                Token(TokenType.STRING, Position(1, 9), Position(1, 23), "\"Hello, World!\""),
                Token(TokenType.CLOSEPARENTHESIS, Position(1, 24), Position(1, 24), ")"),
                Token(TokenType.SEMICOLON, Position(1, 25), Position(1, 25), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testMultipleStatements() {
        val input = "let myVariable: Number = 123;\nprintln(\"Hello, World!\");"
        val expected =
            listOf(
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
                Token(TokenType.SEMICOLON, Position(2, 25), Position(2, 25), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testAssignationAddition() {
        val input = "sum = 123 + 456;"
        val expected =
            listOf(
                Token(TokenType.IDENTIFIER, Position(1, 1), Position(1, 3), "sum"),
                Token(TokenType.ASSIGNATION, Position(1, 5), Position(1, 5), "="),
                Token(TokenType.NUMBER, Position(1, 7), Position(1, 9), "123"),
                Token(TokenType.SUM, Position(1, 11), Position(1, 11), "+"),
                Token(TokenType.NUMBER, Position(1, 13), Position(1, 15), "456"),
                Token(TokenType.SEMICOLON, Position(1, 16), Position(1, 16), ";"),
            )

        val result = lexerFirstVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testConst() {
        val input = "const"
        val expected =
            listOf(
                Token(TokenType.CONST, Position(1, 1), Position(1, 5), "const"),
            )

        val result = lexerSecondVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testBooleanType() {
        val input = "Boolean"
        val expected =
            listOf(
                Token(TokenType.BOOLEANTYPE, Position(1, 1), Position(1, 7), "Boolean"),
            )

        val result = lexerSecondVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testBooleanLiteralTrue() {
        val input = "true"
        val expected =
            listOf(
                Token(TokenType.BOOLEAN, Position(1, 1), Position(1, 4), "true"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testBooleanLiteralFalse() {
        val input = "false"
        val expected =
            listOf(
                Token(TokenType.BOOLEAN, Position(1, 1), Position(1, 5), "false"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testIf() {
        val input = "if"
        val expected =
            listOf(
                Token(TokenType.IF, Position(1, 1), Position(1, 2), "if"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testElse() {
        val input = "else"
        val expected =
            listOf(
                Token(TokenType.ELSE, Position(1, 1), Position(1, 4), "else"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testReadInput() {
        val input = "readInput"
        val expected =
            listOf(
                Token(TokenType.READINPUT, Position(1, 1), Position(1, 9), "readInput"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testReadEnv() {
        val input = "readEnv"
        val expected =
            listOf(
                Token(TokenType.READENV, Position(1, 1), Position(1, 7), "readEnv"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testOpenCurlyBrackets() {
        val input = "{"
        val expected =
            listOf(
                Token(TokenType.OPENCURLY, Position(1, 1), Position(1, 1), "{"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testCloseCurlyBrackets() {
        val input = "}"
        val expected =
            listOf(
                Token(TokenType.CLOSECURLY, Position(1, 1), Position(1, 1), "}"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testDeclarationNodeWithConst() {
        val input = "const myVariable: String = \"Hello, World!\";"
        val expected =
            listOf(
                Token(TokenType.CONST, Position(1, 1), Position(1, 5), "const"),
                Token(TokenType.IDENTIFIER, Position(1, 7), Position(1, 16), "myVariable"),
                Token(TokenType.COLON, Position(1, 17), Position(1, 17), ":"),
                Token(TokenType.STRINGTYPE, Position(1, 19), Position(1, 24), "String"),
                Token(TokenType.ASSIGNATION, Position(1, 26), Position(1, 26), "="),
                Token(TokenType.STRING, Position(1, 28), Position(1, 42), "\"Hello, World!\""),
                Token(TokenType.SEMICOLON, Position(1, 43), Position(1, 43), ";"),
            )

        val result = lexerSecondVersion.lex(input)
        assertTokenListEquals(expected, result)
    }

    @Test
    fun testBooleanDeclaration() {
        val input = "const isOpen :Boolean = true;"
        //           1234567890123456789012345678901
        val expected =
            listOf(
                Token(TokenType.CONST, Position(1, 1), Position(1, 5), "const"),
                Token(TokenType.IDENTIFIER, Position(1, 7), Position(1, 12), "isOpen"),
                Token(TokenType.COLON, Position(1, 14), Position(1, 14), ":"),
                Token(TokenType.BOOLEANTYPE, Position(1, 15), Position(1, 21), "Boolean"),
                Token(TokenType.ASSIGNATION, Position(1, 23), Position(1, 23), "="),
                Token(TokenType.BOOLEAN, Position(1, 25), Position(1, 28), "true"),
                Token(TokenType.SEMICOLON, Position(1, 29), Position(1, 29), ";"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }

    @Test
    fun testIfSentence() {
        val input = "if(a){\nprintln(a)\n};"
        //           123456  1234567890  12
        val expected =
            listOf(
                Token(TokenType.IF, Position(1, 1), Position(1, 2), "if"),
                Token(TokenType.OPENPARENTHESIS, Position(1, 3), Position(1, 3), "("),
                Token(TokenType.IDENTIFIER, Position(1, 4), Position(1, 4), "a"),
                Token(TokenType.CLOSEPARENTHESIS, Position(1, 5), Position(1, 5), ")"),
                Token(TokenType.OPENCURLY, Position(1, 6), Position(1, 6), "{"),
                Token(TokenType.PRINTLN, Position(2, 1), Position(2, 7), "println"),
                Token(TokenType.OPENPARENTHESIS, Position(2, 8), Position(2, 8), "("),
                Token(TokenType.IDENTIFIER, Position(2, 9), Position(2, 9), "a"),
                Token(TokenType.CLOSEPARENTHESIS, Position(2, 10), Position(2, 10), ")"),
                Token(TokenType.CLOSECURLY, Position(3, 1), Position(3, 1), "}"),
                Token(TokenType.SEMICOLON, Position(3, 2), Position(3, 2), ";"),
            )
        val result = lexerSecondVersion.lex(input)
        assertEquals(expected, result)
    }
}
