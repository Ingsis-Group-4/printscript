package org.example.token

import org.example.position.Position

data class OpenParenthesisToken(val start: Position, val end: Position) : Token {}
