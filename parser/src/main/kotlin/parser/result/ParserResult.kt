package parser.result

import ast.AST

/**
 * Sealed interface for representing the result of parsing.
 * Subclasses of ParserResult represent either successful or failed parsing outcomes.
 */
sealed interface ParserResult

/**
 * Represents a successful parsing result.
 * @property value The resulting abstract syntax tree (AST) from successful parsing.
 * @property lastValidatedIndex The index of the last token successfully validated during parsing.
 */
data class SuccessResult(val value: AST, val lastValidatedIndex: Int) : ParserResult

/**
 * Represents a failed parsing result.
 * @property message A descriptive message indicating the reason for parsing failure.
 * @property lastValidatedIndex The index of the last token successfully validated before the parsing failure occurred.
 */
data class FailureResult(val message: String, val lastValidatedIndex: Int) : ParserResult
