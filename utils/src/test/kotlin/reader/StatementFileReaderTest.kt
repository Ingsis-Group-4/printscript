package reader

import lexer.LineLexer
import lexer.getTokenMap
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import token.Token
import utils.assertTokenListEquals
import java.io.File
import kotlin.test.assertFalse

class StatementFileReaderTest {
    private val lineLexer = LineLexer(getTokenMap())

    @Test
    fun `test_001 one statement in one line`() {
        val filePath = "src/test/resources/reader/file/test_001_input.ps"
        val fileLines = File(filePath).readLines()

        val expected = mutableListOf<List<Token>>()

        for (i in fileLines.indices) {
            expected.add(lineLexer.lex(fileLines[i], i + 1))
        }

        val statements = StatementFileReader(File(filePath).inputStream()).nextLine()

        for (i in statements.indices) {
            assertTokenListEquals(expected[i], statements[i])
        }
    }

    @Test
    fun `test_002 hasNext should return false in an empty file`() {
        val reader = StatementFileReader(File("src/test/resources/reader/file/test_002_input.ps").inputStream())

        assertFalse(reader.hasNextLine())
    }

    @Test
    fun `test_003 hasNext should return true in a non empty file`() {
        val reader = StatementFileReader(File("src/test/resources/reader/file/test_003_input.ps").inputStream())

        assertTrue(reader.hasNextLine())
    }

    // TODO Test this more thoroughly
}
