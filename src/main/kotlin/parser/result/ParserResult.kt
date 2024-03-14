package org.example.parser.result

import org.example.ast.AST

sealed interface ParserResult;

data class SuccessResult(val value: AST, val lastValidatedIndex: Int): ParserResult

data class ErrorResult(val message: String, val lastValidatedIndex: Int): ParserResult
