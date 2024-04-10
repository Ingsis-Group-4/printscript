package reader

import lexer.Lexer
import lexer.LineLexer
import lexer.getTokenMap
import org.junit.jupiter.api.Test
import utils.assertTokenListEquals
import kotlin.test.assertEquals

class StatementLineReaderTest {
    private val lexer = Lexer(getTokenMap())
    private val lineLexer = LineLexer(getTokenMap())

    @Test
    fun `test_001 single statement in one line`() {
        val line = "let x: String;"
        val expected = listOf(lexer.lex(line))

        val output = StatementLineReader().read(line, 1)

        for (i in expected.indices) {
            assertTokenListEquals(expected[i], output.tokenStatements[i])
        }

        assertEquals(0, output.remainingTokens.size)
    }

    @Test
    fun `test_002 two statements in one lines`() {
        val line = "let x: String; let a: String;"
        val expected =
            listOf(
                lexer.lex("let x: String;"),
                lexer.lex("               let a: String;"),
            )

        val output = StatementLineReader().read(line, 1)

        for (i in expected.indices) {
            assertTokenListEquals(expected[i], output.tokenStatements[i])
        }
    }

    @Test
    fun `test_003 one complete statement and one incomplete should return one statement and the reminder`() {
        val line = "let x: String; let a"
        val expectedStatements =
            listOf(
                lexer.lex("let x: String;"),
            )

        val expectedRemaining = lexer.lex("               let a")

        val output = StatementLineReader().read(line, 1)

        for (i in expectedStatements.indices) {
            assertTokenListEquals(expectedStatements[i], output.tokenStatements[i])
        }

        assertTokenListEquals(expectedRemaining, output.remainingTokens)
    }

    @Test
    fun `test_004 one incomplete statement in one line and the rest of it in the second line`() {
        // Setup first line
        val firstLine = "let x"
        val expectedRemainingFirstLine = lexer.lex("let x")

        // Execute first line
        val outputFirstLine = StatementLineReader().read(firstLine, 1)

        // Assert first line
        assertEquals(0, outputFirstLine.tokenStatements.size)
        assertTokenListEquals(expectedRemainingFirstLine, outputFirstLine.remainingTokens)

        // Setup second line
        val secondLine = ": String;"
        val expectedStatementsSecondLine =
            listOf(
                lexer.lex("let x\n: String;"),
            )

        // Execute second line with previous remaining tokens
        val outputSecondLine = StatementLineReader().read(secondLine, 2, outputFirstLine.remainingTokens)

        // Assert second line
        for (i in expectedStatementsSecondLine.indices) {
            assertTokenListEquals(expectedStatementsSecondLine[i], outputSecondLine.tokenStatements[i])
        }

        assertEquals(0, outputSecondLine.remainingTokens.size)
    }

    @Test
    fun `test_005 one incomplete statement in one line and the rest of it in the second line with another statement`() {
        // Setup first line
        val firstLine = "let x"
        val expectedRemainingFirstLine = lexer.lex("let x")

        // Execute first line
        val outputFirstLine = StatementLineReader().read(firstLine, 1)

        // Assert first line
        assertEquals(0, outputFirstLine.tokenStatements.size)
        assertTokenListEquals(expectedRemainingFirstLine, outputFirstLine.remainingTokens)

        // Setup second line
        val secondLine = ": String; let a: String;"
        val expectedStatementsSecondLine =
            listOf(
                lexer.lex("let x\n: String;"),
                lineLexer.lex("          let a: String;", 2),
            )

        // Execute second line with previous remaining tokens
        val outputSecondLine = StatementLineReader().read(secondLine, 2, outputFirstLine.remainingTokens)

        // Assert second line
        for (i in expectedStatementsSecondLine.indices) {
            assertTokenListEquals(expectedStatementsSecondLine[i], outputSecondLine.tokenStatements[i])
        }

        assertEquals(0, outputSecondLine.remainingTokens.size)
    }
}
