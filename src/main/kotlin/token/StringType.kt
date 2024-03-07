package org.example.token

import org.example.position.Position

data class StringType(val start: Position, val end: Position) : Token {}
