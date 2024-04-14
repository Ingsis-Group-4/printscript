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

class ReadEnvTest {
    private val lexer = Lexer(getTokenMap(Version.V2))
    private val expressionParser = ExpressionParserFactory.create(Version.V2)
    private val statementParser = StatementParserFactory.create(Version.V2)

    @Test
    fun `test_001 test parse readEnv with expression parser`() {
        val code = "readEnv(\"Hello: \")"
        val tokens = lexer.lex(code)

        val result = expressionParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/expression/test_read_env_001_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_002 test parse readEnv in assignation with statement parser`() {
        val code = "a = readEnv(\"Hello: \");"
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/expression/test_read_env_002_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 test parse readEnv in assignation with statement parser`() {
        val code = "let a: boolean = readEnv(\"Hello: \");"
        val tokens = lexer.lex(code)

        val result = statementParser.parse(tokens, 0)

        assertIs<SuccessResult>(result)

        val ast = result.value

        val expected = getJsonFromFile("src/test/resources/expression/test_read_env_003_result.json")
        val actual = convertToJson(ast)

        assertEquals(expected, actual)
    }
}
