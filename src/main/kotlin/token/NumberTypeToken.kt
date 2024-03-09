package org.example.token

import org.example.position.Position

data class NumberTypeToken(val start: Position, val end: Position) : Token {}
