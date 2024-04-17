import ast.BlockNode
import ast.ColonNode
import ast.DeclarationNode
import ast.EqualsNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.IfStatement
import ast.LetNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.ProgramNode
import ast.VariableType
import ast.VariableTypeNode
import formatter.FormattingRule
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Assertions.assertEquals
import parser.factory.ProgramParserFactory
import parser.result.SuccessResult
import kotlin.test.Ignore
import kotlin.test.Test

class FormatterTest {
    private val lexer = Lexer(getTokenMap())
    private val parser = ProgramParserFactory.create()
    private val configPath: String = "src/test/resources/formatter.test.config.json"

    @Test
    fun `test_001 String Declaration`() {
        val code = "let a: string=\"String\";"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
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
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_002_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_003 Two Statements`() {
        val code = "let              first:           number =     1     ;\nlet      second    : string           = \"2\"     ;"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_003_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_004 Print Statement`() {
        val code = "let a          : number     =    1   ;\nprintln(1+a      )  ;"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_004_result.ps")
        assertEquals(expected, actual)
    }

//    @Ignore
    @Test
    fun `test_005 If Statement`() {
        val code = "let a: number = 1;\nif(b) {\n    println(1);\n   println(2);\n}"
        val tokens = lexer.lex(code)
        val result =
            SuccessResult(
                ProgramNode(
                    listOf(
                        DeclarationNode(
                            IdentifierNode("a", VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                            LetNode(start = tokens.first().start, end = tokens.last().end),
                            ColonNode(start = tokens.first().start, end = tokens.last().end),
                            VariableTypeNode(VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            EqualsNode(start = tokens.first().start, end = tokens.last().end),
                            tokens.first().start,
                            tokens.last().end,
                        ),
                        IfStatement(
                            start = tokens.first().start,
                            end = tokens.last().end,
                            condition =
                                IdentifierNode(
                                    "b",
                                    VariableType.BOOLEAN,
                                    start = tokens.first().start,
                                    end = tokens.last().end,
                                ),
                            thenBlock =
                                BlockNode(
                                    start = tokens.first().start,
                                    end = tokens.last().end,
                                    listOf(
                                        DeclarationNode(
                                            IdentifierNode("a", VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                                            LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                                            LetNode(start = tokens.first().start, end = tokens.last().end),
                                            ColonNode(start = tokens.first().start, end = tokens.last().end),
                                            VariableTypeNode(VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                                            EqualsNode(start = tokens.first().start, end = tokens.last().end),
                                            tokens.first().start,
                                            tokens.last().end,
                                        ),
                                        FunctionStatementNode(
                                            functionNode =
                                                PrintLnNode(
                                                    LiteralNode(2, start = tokens.first().start, end = tokens.last().end),
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                ),
                                            start = tokens.first().start,
                                            end = tokens.last().end,
                                        ),
                                    ),
                                ),
                            elseBlock = null,
                        ),
                    ),
                    start = tokens.first().start,
                    end = tokens.last().end,
                ),
                tokens.size,
            )
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_005_result.ps")
        assertEquals(expected, actual)
    }

    @Ignore
    @Test
    fun `test_006 Const Declaration`() {
        val code = "const a: string=\"String\";"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_001_result.ps")
        assertEquals(expected, actual)
    }

    @Ignore
    @Test
    fun `test_007 Boolean Declaration`() {
        val code = "let    a:boolean =true;"
        val tokens = lexer.lex(code)
        val result = parser.parse(tokens, 0) as SuccessResult
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_007_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_008 Nested If Statement`() {
        val code =
            """
            let a: number = 1;
            if(a == 1) {
                if(a == 1) {
                    println(1);
                }
                println(2);
            }
            """.trimIndent()
        val tokens = lexer.lex(code)
        val result =
            SuccessResult(
                ProgramNode(
                    listOf(
                        DeclarationNode(
                            IdentifierNode("a", VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                            LetNode(start = tokens.first().start, end = tokens.last().end),
                            ColonNode(start = tokens.first().start, end = tokens.last().end),
                            VariableTypeNode(VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            EqualsNode(start = tokens.first().start, end = tokens.last().end),
                            tokens.first().start,
                            tokens.last().end,
                        ),
                        IfStatement(
                            start = tokens.first().start,
                            end = tokens.last().end,
                            condition = IdentifierNode("b", VariableType.BOOLEAN, start = tokens.first().start, end = tokens.last().end),
                            thenBlock =
                                BlockNode(
                                    start = tokens.first().start,
                                    end = tokens.last().end,
                                    listOf(
                                        IfStatement(
                                            start = tokens.first().start,
                                            end = tokens.last().end,
                                            condition =
                                                IdentifierNode(
                                                    "b",
                                                    VariableType.BOOLEAN,
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                ),
                                            thenBlock =
                                                BlockNode(
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                    listOf(
                                                        FunctionStatementNode(
                                                            functionNode =
                                                                PrintLnNode(
                                                                    LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                                                                    start = tokens.first().start,
                                                                    end = tokens.last().end,
                                                                ),
                                                            start = tokens.first().start,
                                                            end = tokens.last().end,
                                                        ),
                                                    ),
                                                ),
                                            elseBlock = null,
                                        ),
                                        FunctionStatementNode(
                                            functionNode =
                                                PrintLnNode(
                                                    LiteralNode(2, start = tokens.first().start, end = tokens.last().end),
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                ),
                                            start = tokens.first().start,
                                            end = tokens.last().end,
                                        ),
                                    ),
                                ),
                            elseBlock = null,
                        ),
                    ),
                    start = tokens.first().start,
                    end = tokens.last().end,
                ),
                tokens.size,
            )
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_008_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_009 Simple If Else Statement`() {
        val code =
            """
            let a: number = 1;
            if(a == 1) {
                println(1);
            } else {
                println(2);
            }
            """.trimIndent()
        val tokens = lexer.lex(code)
        val result =
            SuccessResult(
                ProgramNode(
                    listOf(
                        DeclarationNode(
                            IdentifierNode("a", VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                            LetNode(start = tokens.first().start, end = tokens.last().end),
                            ColonNode(start = tokens.first().start, end = tokens.last().end),
                            VariableTypeNode(VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            EqualsNode(start = tokens.first().start, end = tokens.last().end),
                            tokens.first().start,
                            tokens.last().end,
                        ),
                        IfStatement(
                            start = tokens.first().start,
                            end = tokens.last().end,
                            condition = IdentifierNode("b", VariableType.BOOLEAN, start = tokens.first().start, end = tokens.last().end),
                            thenBlock =
                                BlockNode(
                                    start = tokens.first().start,
                                    end = tokens.last().end,
                                    listOf(
                                        FunctionStatementNode(
                                            functionNode =
                                                PrintLnNode(
                                                    LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                ),
                                            start = tokens.first().start,
                                            end = tokens.last().end,
                                        ),
                                    ),
                                ),
                            elseBlock =
                                BlockNode(
                                    start = tokens.first().start,
                                    end = tokens.last().end,
                                    listOf(
                                        FunctionStatementNode(
                                            functionNode =
                                                PrintLnNode(
                                                    LiteralNode(2, start = tokens.first().start, end = tokens.last().end),
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                ),
                                            start = tokens.first().start,
                                            end = tokens.last().end,
                                        ),
                                    ),
                                ),
                        ),
                    ),
                    start = tokens.first().start,
                    end = tokens.last().end,
                ),
                tokens.size,
            )
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_009_result.ps")
        assertEquals(expected, actual)
    }

    @Test
    fun `test_010 Nested If Else Statement`() {
        val code =
            """
            let a: number = 1;
            if(a == 1) {
                if(a == 1) {
                    println(1);
                } else {
                    println(2);
                }
            } else {
                println(3);
            }
            """.trimIndent()
        val tokens = lexer.lex(code)
        val result =
            SuccessResult(
                ProgramNode(
                    listOf(
                        DeclarationNode(
                            IdentifierNode("a", VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                            LetNode(start = tokens.first().start, end = tokens.last().end),
                            ColonNode(start = tokens.first().start, end = tokens.last().end),
                            VariableTypeNode(VariableType.NUMBER, start = tokens.first().start, end = tokens.last().end),
                            EqualsNode(start = tokens.first().start, end = tokens.last().end),
                            tokens.first().start,
                            tokens.last().end,
                        ),
                        IfStatement(
                            start = tokens.first().start,
                            end = tokens.last().end,
                            condition = IdentifierNode("b", VariableType.BOOLEAN, start = tokens.first().start, end = tokens.last().end),
                            thenBlock =
                                BlockNode(
                                    start = tokens.first().start,
                                    end = tokens.last().end,
                                    listOf(
                                        IfStatement(
                                            start = tokens.first().start,
                                            end = tokens.last().end,
                                            condition =
                                                IdentifierNode(
                                                    "b",
                                                    VariableType.BOOLEAN,
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                ),
                                            thenBlock =
                                                BlockNode(
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                    listOf(
                                                        FunctionStatementNode(
                                                            functionNode =
                                                                PrintLnNode(
                                                                    LiteralNode(1, start = tokens.first().start, end = tokens.last().end),
                                                                    start = tokens.first().start,
                                                                    end = tokens.last().end,
                                                                ),
                                                            start = tokens.first().start,
                                                            end = tokens.last().end,
                                                        ),
                                                    ),
                                                ),
                                            elseBlock =
                                                BlockNode(
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                    listOf(
                                                        FunctionStatementNode(
                                                            functionNode =
                                                                PrintLnNode(
                                                                    LiteralNode(2, start = tokens.first().start, end = tokens.last().end),
                                                                    start = tokens.first().start,
                                                                    end = tokens.last().end,
                                                                ),
                                                            start = tokens.first().start,
                                                            end = tokens.last().end,
                                                        ),
                                                    ),
                                                ),
                                        ),
                                    ),
                                ),
                            elseBlock =
                                BlockNode(
                                    start = tokens.first().start,
                                    end = tokens.last().end,
                                    listOf(
                                        FunctionStatementNode(
                                            functionNode =
                                                PrintLnNode(
                                                    LiteralNode(3, start = tokens.first().start, end = tokens.last().end),
                                                    start = tokens.first().start,
                                                    end = tokens.last().end,
                                                ),
                                            start = tokens.first().start,
                                            end = tokens.last().end,
                                        ),
                                    ),
                                ),
                        ),
                    ),
                    start = tokens.first().start,
                    end = tokens.last().end,
                ),
                tokens.size,
            )
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configPath), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_010_result.ps")
        assertEquals(expected, actual)
    }
}
