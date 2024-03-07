package org.example.token

import org.example.position.Position

data class Subtraction(val start: Position, val end: Position) : Token {}
