package org.example.token

import org.example.position.Position

data class Println(val start: Position, val end: Position) : Token {}
