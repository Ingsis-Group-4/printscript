package org.example.token

import org.example.position.Position

data class AssignationToken(val start: Position, val end: Position) : Token {}



