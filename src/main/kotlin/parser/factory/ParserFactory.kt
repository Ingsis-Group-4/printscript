package org.example.parser.factory

import org.example.parser.Parser

interface ParserFactory {
    fun create(): Parser
}