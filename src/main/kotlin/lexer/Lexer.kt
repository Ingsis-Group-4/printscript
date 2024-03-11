package org.example.lexer

import org.example.position.Position
import org.example.token.*

class Lexer {
    val tokenMap: Map<TokenType, TokenMatcher>

    constructor(tokenMap: Map<TokenType, TokenMatcher>) {
        this.tokenMap = tokenMap
    }

    fun lex(input: String): List<Token> {

        val tokenTypes = tokenMap.keys

        val tokenList = mutableListOf<Token>()
        var currentLine = 1;
        for (line in input.lines()) {
            //create a new Matcher out of all of the token matchers in the tokenMap
            val matcher = TokenMatcher(tokenMap.values.toList()).getMatcher(line)
            while (matcher.find()) {
                for (tokenType in tokenTypes) {
                    if (matcher.group(tokenType.toString()) != null) {
                        tokenList.add(
                            Token(
                                tokenType,
                                Position(currentLine, matcher.start()+1),
                                Position(currentLine, matcher.end()+1),
                                matcher.group()
                            )
                        )
                        break
                    }
                }
            }
            currentLine++
        }
        return tokenList
    }

}