package lexer

import position.Position
import token.Token
import token.TokenMatcher
import token.TokenType
import java.util.regex.Matcher

class LineLexer(
    private val tokenMap: Map<TokenType, TokenMatcher>,
) {
    fun lex(
        input: String,
        currentLine: Int,
    ): List<Token> {
        val tokenTypes = tokenMap.keys.toList()
        return getTokensByLine(input, tokenTypes, currentLine)
    }

    private fun getTokensByLine(
        inputLine: String,
        tokenTypes: List<TokenType>,
        currentLine: Int,
    ): List<Token> {
        val auxTokenList = mutableListOf<Token>()
        val matcher = TokenMatcher(tokenMap.values.toList()).getMatcher(inputLine)

        // while there are still matches in the line
        while (matcher.find()) {
            for (tokenType in tokenTypes) {
                // if the token type is found in the current match
                if (matcher.group(tokenType.toString()) != null) {
                    // create the token and add it to the list
                    auxTokenList.add(createToken(tokenType, currentLine, matcher))
                    break
                }
            }
        }
        return auxTokenList
    }

    private fun createToken(
        tokenType: TokenType,
        currentLine: Int,
        matcher: Matcher,
    ): Token {
        return Token(
            tokenType,
            Position(currentLine, matcher.start() + 1),
            Position(currentLine, matcher.end()),
            matcher.group(),
        )
    }
}
