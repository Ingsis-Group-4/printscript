package org.example.token

import org.example.position.Position

data class StringToken(val start: Position, val end: Position) : Token {}