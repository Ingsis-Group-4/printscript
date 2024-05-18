package conditional

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
import formatter.ProgramNodeFormatter
import formatter.factory.FormatterMapFactory
import formatter.rule.FormattingRule
import lexer.Lexer
import lexer.getTokenMap
import org.junit.jupiter.api.Assertions
import parser.factory.ProgramParserFactory
import parser.factory.StatementParserFactory
import parser.result.SuccessResult
import utils.getStringFromFile
import version.Version
import java.io.File
import kotlin.test.Test

class IfStatementTest {
    private val lexer = Lexer(getTokenMap(Version.V2))
    private val parser = ProgramParserFactory.create()
    private val statementParser = StatementParserFactory.create(Version.V2)
    private val configContent: String = File("src/test/resources/formatter.test.config.json").readText()

    @Test
    fun `test_005 If Statement`() {
        val code = "let a: number = 1;\nif(b) {\n    println(1);\n   println(2);\n}"
        val tokens = lexer.lex(code)
        val result =
            SuccessResult(
                ProgramNode(
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
                    ),
                    start = tokens.first().start,
                    end = tokens.last().end,
                ),
                tokens.size,
            )
        val ast = result.value as ProgramNode
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_005_result.ps")
        Assertions.assertEquals(expected, actual)
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
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_008_result.ps")
        Assertions.assertEquals(expected, actual)
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
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_009_result.ps")
        Assertions.assertEquals(expected, actual)
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
        val actual = ProgramNodeFormatter().format(ast, FormattingRule(configContent), FormatterMapFactory().createFormatterMap())
        val expected = getStringFromFile("src/test/resources/test_010_result.ps")
        Assertions.assertEquals(expected, actual)
    }
}
