package org.example.token

import org.example.position.Position

data class IdentifierToken(val start: Position, val end: Position) : Token {}
