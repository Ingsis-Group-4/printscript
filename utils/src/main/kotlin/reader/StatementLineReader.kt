package reader

import lexer.LineLexer
import lexer.getTokenMap
import token.Token
import token.TokenType
import version.Version

/**
 * Utility class for getting all the statements of a single line.
 */
class StatementLineReader(
    private val version: Version,
) {
    private val lexer: LineLexer = LineLexer(getTokenMap(version))

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

    private fun divideIntoStatementsAndRemaining(
        tokens: List<Token>,
        criteriaParam: EndOfStatementCriteria = SemicolonEndOfStatementCriteria(),
        statements: MutableList<List<Token>> = mutableListOf(),
        currentStatementParam: MutableList<Token> = mutableListOf(),
    ): LineReaderOutput {
        var criteria = criteriaParam
        var currentStatement = currentStatementParam
        tokens.forEachIndexed { i, token ->
            when {
                token.type == TokenType.IF -> {
                    return ifStatementHandler(currentStatement, token, tokens, i, statements)
                }
                criteria.isEndOfStatementCriteria(token) -> {
                    currentStatement.add(token)
                    if (criteria is ClosedCurlyEndOfStatementCriteria) {
                        if (isNotFinalToken(i, tokens) && nextTokenIsElseBlock(tokens, i)) {
                            return@forEachIndexed
                        }
                    }
                    statements.add(currentStatement)
                    currentStatement = mutableListOf()
                    criteria = SemicolonEndOfStatementCriteria()
                }
                else -> currentStatement.add(token)
            }
        }
        return LineReaderOutput(statements, currentStatement)
    }

    private fun ifStatementHandler(
        currentStatement: MutableList<Token>,
        token: Token,
        tokens: List<Token>,
        i: Int,
        statements: MutableList<List<Token>>,
    ): LineReaderOutput {
        currentStatement.add(token)
        return divideIntoStatementsAndRemaining(
            tokens.subList(i + 1, tokens.size),
            ClosedCurlyEndOfStatementCriteria(),
            statements,
            currentStatement,
        )
    }

//        val statements = mutableListOf<List<Token>>()
//        var currentStatement = mutableListOf<Token>()
//
//        for (token in tokens) {
//            if (isEndOfStatementCriteria(token)) {
//                currentStatement.add(token)
//                statements.add(currentStatement)
//                currentStatement = mutableListOf()
//            } else {
//                currentStatement.add(token)
//            }
//        }
//
//        return LineReaderOutput(statements, currentStatement)

    private fun nextTokenIsElseBlock(
        tokens: List<Token>,
        i: Int,
    ) = tokens[i + 1].type == TokenType.ELSE

    private fun isNotFinalToken(
        i: Int,
        tokens: List<Token>,
    ) = i < tokens.size - 1

    private fun isEndOfStatementCriteria(token: Token): Boolean {
        return token.type == TokenType.SEMICOLON
    }
}
