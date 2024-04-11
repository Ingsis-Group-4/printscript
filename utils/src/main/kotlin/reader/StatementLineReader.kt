package reader

import lexer.LineLexer
import lexer.getTokenMap
import token.Token
import token.TokenType

/**
 * Utility class for getting all the statements of a single line.
 */
class StatementLineReader(
    private val lexer: LineLexer = LineLexer(getTokenMap()),
) {
    /**
     * Method for reading all statements and remaining tokens of one line.
     *
     * It receives the line to be lexed, the line
     * index (to add the line in the position of each token),
     * and an optional list of tokens that remained from a previous line
     *
     * It returns an object which contains:
     * - A list of lists, which are all the complete statements from that line
     * - Another list with the remaining tokens that do not complete a statement.
     *
     * @param line
     * @param lineIndex
     * @param prevTokens (Optional)
     *
     * @return LineReaderOutput
     */
    fun read(
        line: String,
        lineIndex: Int,
        prevTokens: List<Token> = listOf(),
    ): LineReaderOutput {
        val lineTokens = lexer.lex(line, lineIndex)

        val totalTokens = prevTokens + lineTokens

        return divideIntoStatementsAndRemaining(totalTokens)
    }

    private fun divideIntoStatementsAndRemaining(tokens: List<Token>): LineReaderOutput {
        val statements = mutableListOf<List<Token>>()
        var currentStatement = mutableListOf<Token>()

        for (token in tokens) {
            if (isEndOfStatement(token)) {
                currentStatement.add(token)
                statements.add(currentStatement)
                currentStatement = mutableListOf()
            } else {
                currentStatement.add(token)
            }
        }

        return LineReaderOutput(statements, currentStatement)
    }

    private fun isEndOfStatement(token: Token): Boolean {
        return token.type == TokenType.SEMICOLON
    }
}
