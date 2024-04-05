package parser.statement

import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Test
import parser.factory.StatementProgramFactory
import parser.result.SuccessResult
import parser.utils.convertToJson
import parser.utils.getJsonFromFile
import kotlin.test.Ignore
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StatementParserTest {
    private val lexer = Lexer(getTokenMap())
    private val statementParser = StatementProgramFactory.create()

    @Ignore
    @Test
    fun `test_001 Empty statement`() {
        val code = ""
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens, 0)

        // TODO: What should we return or do here?
    }

    @Test
    fun `test_002 One assignation statement with literal expression should return ast`() {
        val code = "x = 5;"
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens)
        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_002_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 Two assignation statements should return ast of the first statement only`() {
        val code =
            """
            y = 10;
            x = 5;
            """.trimIndent()
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens)
        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_003_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_004 One declaration statement with literal expression should return ast`() {
        val code = "let x: Number = 5;"
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens)
        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_004_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_005 One println statement with sum expression should return ast`() {
        val code = "println(5 + 5);"
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens)
        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_005_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }
}
