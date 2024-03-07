package org.example.token

import org.example.position.Position

data class NumberType(val start: Position, val end: Position) : Token {}
