package org.example.token

import org.example.position.Position

data class Let(val start: Position, val end: Position) : Token {}

