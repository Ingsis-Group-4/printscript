package org.example.token

import org.example.position.Position

data class LetToken(val start: Position, val end: Position) : Token {}
