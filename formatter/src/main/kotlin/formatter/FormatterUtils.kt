package formatter

import ast.AST

fun addWhiteSpace(resultString: String): String {
    return "$resultString "
}

fun getExpression(
    node: AST,
    rule: FormattingRule,
): String {
    val expressionNode = node as ast.ExpressionNode
    TODO()
}
