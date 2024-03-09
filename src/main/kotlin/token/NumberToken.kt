package org.example.token

import org.example.position.Position

data class NumberToken(val start: Position, val end: Position) : Token {}
