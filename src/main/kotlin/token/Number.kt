package org.example.token

import org.example.position.Position

data class Number(val start: Position, val end: Position) : Token {}
