package org.example.token

import org.example.position.Position
import kotlin.String

data class Token(val type: TokenType, val start:Position, val end:Position, val value:String) {


}