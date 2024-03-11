package org.example.ast

import org.example.interpreter.Visitor

sealed interface AST{
    fun <T> accept(visitor: Visitor<T>): T
}
