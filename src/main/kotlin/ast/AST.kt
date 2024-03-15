package org.example.ast

import org.example.common.visitor.Visitor

sealed interface AST{
    fun <T> accept(visitor: Visitor<T>): T
}
