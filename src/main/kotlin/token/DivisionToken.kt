package org.example.token

import org.example.position.Position

data class DivisionToken(val start: Position, val end: Position) : Token {}

