package statement

import ast.ProgramNode
import ast.StatementNode
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import formatter.rule.FormattingRule
import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Assertions.assertEquals
import parser.factory.ProgramParserFactory
import parser.factory.StatementParserFactory
import parser.result.SuccessResult
import utils.getStringFromFile
import version.Version
import java.io.File
import kotlin.test.Test

class FormatterTest {
    private val lexer = Lexer(getTokenMap(Version.V2))
    private val parser = ProgramParserFactory.create()
    private val statementParser = StatementParserFactory.create(Version.V2)
    private val configContent: String = File("src/test/resources/formatter.test.config.json").readText()

    @Test
    fun `test_001 String Declaration`() {
        val code = "let a: string=\"String\";"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_001_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_002 Int Declaration`() {
        val code = "let   secretNumber    : number  =           1000;"
        val tokens = lexer.lex(code)
        println(tokens)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_002_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 Two Statements`() {
        val code = "let              first:           number =     1     ;\nlet      second    : string           = \"2\"     ;"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_003_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_004 Print Statement`() {
        val code = "let a          : number     =    1   ;\nprintln(1+a      )  ;"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_004_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_006 Const Declaration`() {
        val code = "const a: string=\"String\";"
        val tokens = lexer.lex(code)
        val result = statementParser.parse(tokens, 0) as SuccessResult
        val ast =
            ProgramNode(
                listOf(
                    result.value as StatementNode,
                ),
                tokens.first().start,
                tokens.last().end,
            )
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_006_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_007 Boolean Declaration`() {
        val code = "let    a:boolean =true;"
        val tokens = lexer.lex(code)
        val result = statementParser.parse(tokens, 0) as SuccessResult
        val ast =
            ProgramNode(
                listOf(
                    result.value as StatementNode,
                ),
                tokens.first().start,
                tokens.last().end,
            )
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_007_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_011 Read Input`() {
        val code = "let a: number = readInput     (   \"Hola\");"
        val tokens = lexer.lex(code)
        val result = statementParser.parse(tokens, 0) as SuccessResult
        val ast =
            ProgramNode(
                listOf(
                    result.value as StatementNode,
                ),
                tokens.first().start,
                tokens.last().end,
            )
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_011_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_012 Read Env`() {
        val code = "let a: number = readEnv     (   \"Hola\");"
        val tokens = lexer.lex(code)
        val result = statementParser.parse(tokens, 0) as SuccessResult
        val ast =
            ProgramNode(
                listOf(
                    result.value as StatementNode,
                ),
                tokens.first().start,
                tokens.last().end,
            )
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_012_result.ps")
        assertEquals(expected, actual)
    }
}
