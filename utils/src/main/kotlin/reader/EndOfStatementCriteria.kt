package reader

import token.Token
import token.TokenType

interface EndOfStatementCriteria {
    fun isEndOfStatementCriteria(token: Token): Boolean
}

class SemicolonEndOfStatementCriteria : EndOfStatementCriteria {
    override fun isEndOfStatementCriteria(token: Token): Boolean {
        return token.type == TokenType.SEMICOLON
    }
}

class ClosedCurlyEndOfStatementCriteria : EndOfStatementCriteria {
    override fun isEndOfStatementCriteria(token: Token): Boolean {
        return token.type == TokenType.CLOSECURLY
    }
}
