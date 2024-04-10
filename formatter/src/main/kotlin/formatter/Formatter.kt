package formatter

import ast.AST

interface Formatter {
    fun format(
        node: AST,
        rule: FormattingRule,
    ): String
}
