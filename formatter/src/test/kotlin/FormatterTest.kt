import ast.ProgramNode
import formatter.FormattingRule
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Assertions.assertEquals
import parser.factory.ProgramParserFactory
import parser.result.SuccessResult
import kotlin.test.Test

class FormatterTest {
    private val lexer = Lexer(getTokenMap())
    private val parser = ProgramParserFactory.create()
    private val configPath: String = "src/test/resources/formatter.test.config.json"

    @Test
    fun `test_001 String Declaration`() {
        val code = "let a: String=\"String\";"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_001_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_002 Int Declaration`() {
        val code = "let   secretNumber    : Number  =           1000;"
        val tokens = lexer.lex(code)
        println(tokens)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_002_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 Two Statements`() {
        val code = "let              first:           Number =     1     ;\nlet      second    : String           = \"2\"     ;"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_003_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_004 Print Statement`() {
        val code = "let a          : Number     =    1   ;\nprintln(1+a      )  ;"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_004_result.ps")
        assertEquals(expected, actual)
    }
}
