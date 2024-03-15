package org.example.interpreter

import org.example.ast.AST

interface Interpreter {
    fun interpret(): Value
}
