package parser.conditional

import lexer.Lexer
import lexer.getTokenMap
import parser.factory.BlockParserFactory
import parser.result.SuccessResult
import parser.utils.convertToJson
import parser.utils.getJsonFromFile
import version.Version
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class IfStatementParserTest {
    private val ifStatementParser = IfStatementParser(BlockParserFactory.create())
    private val lexer = Lexer(getTokenMap(Version.V2))

    @Test
    fun `test_001 if with variable assignation`() {
        val code = "if (x) { x = 5.0; }"
        val tokens = lexer.lex(code)

        val result = ifStatementParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/conditional/test_001_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_002 if with two assignations`() {
        val code = "if (x) { x = 5.0; y = 10.0; }"
        val tokens = lexer.lex(code)

        val result = ifStatementParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/conditional/test_002_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 if with assignation and else with assignation`() {
        val code = "if (x) { x = 5.0; } else { x = 3.0; }"
        val tokens = lexer.lex(code)

        val result = ifStatementParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/conditional/test_003_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }
}
