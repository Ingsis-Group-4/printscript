package org.example.token

import org.example.position.Position

data class SubtractionToken(val start: Position, val end: Position) : Token {}
