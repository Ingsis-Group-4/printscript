package org.example.parser

import org.example.parser.result.ParserResult
import token.Token

/**
 * Interface for defining parsers.
 * Implementations of Parser are responsible for parsing tokens and producing parsing results.
 */
interface Parser {
    /**
     * Parses a list of tokens starting from the specified currentIndex.
     * @param tokens The list of tokens to parse.
     * @param currentIndex The index from which to start parsing in the list of tokens.
     * @return A ParserResult representing the outcome of parsing.
     */
    fun parse(tokens: List<Token>, currentIndex: Int): ParserResult
}
