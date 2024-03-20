package ast

import position.Position

sealed interface AST{
    fun getStart(): Position
    fun getEnd(): Position
}
