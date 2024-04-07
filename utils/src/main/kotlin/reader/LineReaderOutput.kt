package reader

import token.Token

class LineReaderOutput(
    val tokenStatements: List<List<Token>>,
    val remainingTokens: List<Token>,
)
