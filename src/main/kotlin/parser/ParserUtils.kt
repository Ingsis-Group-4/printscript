package org.example.parser

import org.example.token.Token

fun next(tokens: List<Token>, index: Int): Token {
    return tokens[index + 1]
}

fun at(tokens: List<Token>, index: Int): Token {
    return tokens[index]
}