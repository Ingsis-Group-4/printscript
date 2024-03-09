package org.example.token

import org.example.position.Position

data class CloseParenthesisToken(val start: Position, val end: Position) : Token {}
