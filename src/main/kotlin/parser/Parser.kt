package org.example.parser

import org.example.ast.AST
import org.example.token.Token

interface Parser {
    fun parse(tokens: List<Token>, currentIndex: Int): AST
}