package org.example.factory

import org.example.parser.Parser

interface ParserFactory {
    fun create(): Parser
}