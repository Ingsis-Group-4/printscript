package org.example.token

import org.example.position.Position

data class Colon(val start: Position, val end: Position) : Token {}
