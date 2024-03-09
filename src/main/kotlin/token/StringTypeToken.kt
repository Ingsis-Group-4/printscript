package org.example.token

import org.example.position.Position

data class StringTypeToken(val start: Position, val end: Position) : Token {}
