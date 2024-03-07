package org.example.token

import org.example.position.Position

data class Identifier(val start: Position, val end: Position) : Token {}
