package org.example.token

import org.example.position.Position

data class OpenParenthesis(val start: Position, val end: Position) : Token {}
