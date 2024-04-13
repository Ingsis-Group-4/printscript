package parser.expression

import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Test
import parser.factory.ExpressionParserFactory
import parser.factory.StatementParserFactory
import parser.result.SuccessResult
import parser.utils.convertToJson
import parser.utils.getJsonFromFile
import version.Version
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ReadInputTest {
    private val lexer = Lexer(getTokenMap(Version.V2))
    private val expressionParser = ExpressionParserFactory.create(Version.V2)
    private val statementParser = StatementParserFactory.create(Version.V2)

    @Test
    fun `test_001 test parse readInput with expression parser`() {
        val code = "readInput(\"Hello: \")"
        val tokens = lexer.lex(code)

        val result = expressionParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/expression/test_001_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_002 test parse readInput in assignation with statement parser`() {
        val code = "a = readInput(\"Hello: \");"
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/expression/test_002_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 test parse readInput in assignation with statement parser`() {
        val code = "let a: Boolean = readInput(\"Hello: \");"
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/expression/test_003_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }
}
