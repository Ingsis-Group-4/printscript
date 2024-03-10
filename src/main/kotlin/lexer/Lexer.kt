package org.example.lexer

import org.example.position.Position
import org.example.token.*
import java.util.regex.Matcher

class Lexer(val tokenMap: Map<TokenType, TokenMatcher>) {

    fun lex(input: String): List<Token> {
        val tokenTypes = tokenMap.keys.toList()
        val tokenList = mutableListOf<Token>()
        var currentLineCounter = 1;
        for (currentLine in input.lines()) {
            //get the tokens of the current line
            tokenList+= getTokensByLine(currentLine, tokenTypes, currentLineCounter)
            currentLineCounter++
        }
        return tokenList
    }

    private fun getTokensByLine(
        inputLine: String,
        tokenTypes: List<TokenType>,
        currentLine: Int
    ): List<Token> {
        val auxTokenList = mutableListOf<Token>()
        val matcher = TokenMatcher(tokenMap.values.toList()).getMatcher(inputLine)

        //while there are still matches in the line
        while (matcher.find()) {
            for (tokenType in tokenTypes) {
                //if the token type is found in the current match
                if (matcher.group(tokenType.toString()) != null) {
                    //create the token and add it to the list
                    auxTokenList.add(createToken( tokenType, currentLine, matcher))
                    break
                }
            }
        }
        return auxTokenList
    }

    private fun createToken(
        tokenType: TokenType,
        currentLine: Int,
        matcher: Matcher
    ): Token {
        return Token(
            tokenType,
            Position(currentLine, matcher.start() + 1),
            Position(currentLine, matcher.end()),
            matcher.group()
        )
    }

}