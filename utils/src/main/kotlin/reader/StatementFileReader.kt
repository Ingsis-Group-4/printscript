package reader

import token.Token
import java.io.BufferedReader
import java.io.File

/**
 * Utility class for reading lines as tokens from a file
 *
 * Warning: This class is not immutable
 */
class StatementFileReader(
    filePath: String,
    private val lineReader: StatementLineReader = StatementLineReader(),
) {
    private val remainingTokens = mutableListOf<Token>()
    private var currentLine = 1
    private val buffer: BufferedReader = BufferedReader(File(filePath).reader())

    /**
     * Reads the next line, and returns a list of lists with the tokens of the complete statements the line has.
     *
     * If there is an incomplete statement, the class will store the remaining tokens for future reads
     *
     */
    fun nextLine(): List<List<Token>> {
        val line = buffer.readLine()
        val lineReaderOutput = lineReader.read(line, currentLine, remainingTokens)

        remainingTokens.addAll(lineReaderOutput.remainingTokens)

        currentLine++

        return lineReaderOutput.tokenStatements
    }

    fun hasNextLine(): Boolean {
        // Mark current position
        buffer.mark(10000)

        // Check if there are more lines left
        val hasLines = buffer.readLine() != null

        // Reset the pointer back to the marked position
        buffer.reset()
        return hasLines
    }
}