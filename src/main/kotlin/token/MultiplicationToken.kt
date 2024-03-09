package org.example.token

import org.example.position.Position

data class MultiplicationToken(val start: Position, val end: Position) : Token {}
