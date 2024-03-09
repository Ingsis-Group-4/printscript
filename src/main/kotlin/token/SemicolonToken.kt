package org.example.token

import org.example.position.Position

data class SemicolonToken(val start: Position, val end: Position) : Token {}
