import ast.ProgramNode
import formatter.FormattingRule
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Assertions.assertEquals
import parser.factory.ProgramParserFactory
import parser.result.FailureResult
import parser.result.SuccessResult
import kotlin.test.Test

class FormatterTest {
    private val lexer = Lexer(getTokenMap())
    private val parser = ProgramParserFactory.create()
    private val configPath: String = "src/test/resources/formatter.test.config.json"

    @Test
    fun test_001() {
        val code = "let a: string=\"String\";"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens,1) as FailureResult
        val ast = result.message
        println(ast)
//        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath),FormatterMapFactory().createFormatterMap())
//        val expected = getStringFromFile("src/test/resources/test_001_result.ps")
//        assertEquals(expected, actual)

    }
}
