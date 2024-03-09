package org.example.token

import org.example.position.Position

data class PrintlnToken(val start: Position, val end: Position) : Token {}
