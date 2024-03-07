package org.example.token

import org.example.position.Position

data class Sum(val start: Position, val end: Position) : Token {}
