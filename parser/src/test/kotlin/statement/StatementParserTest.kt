package parser.statement

import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Test
import parser.factory.StatementParserFactory
import parser.result.SuccessResult
import parser.utils.convertToJson
import parser.utils.getJsonFromFile
import version.Version
import kotlin.test.Ignore
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StatementParserTest {
    private val lexer = Lexer(getTokenMap())
    private val statementParser = StatementParserFactory.create()

    private val lexerV2 = Lexer(getTokenMap(Version.V2))
    private val statementParserV2 = StatementParserFactory.create(Version.V2)

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

    @Test
    fun `test_006 If statement with else`() {
        val code = "if (x) { x = 5.0; } else { x = 3.0; }"
        val tokens = lexerV2.lex(code)

        val result = statementParserV2.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_006_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_007 assignation with true`() {
        val code = "a = true;"
        val tokens = lexerV2.lex(code)

        val result = statementParserV2.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_007_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_008 assignation with false`() {
        val code = "a = false;"
        val tokens = lexerV2.lex(code)

        val result = statementParserV2.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_008_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_009 variable declaration with true`() {
        val code = "let a: Boolean = true;"
        val tokens = lexerV2.lex(code)

        val result = statementParserV2.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/statement/test_009_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }
}
