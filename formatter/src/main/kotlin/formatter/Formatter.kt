package formatter

import ast.AST
import formatter.rule.FormattingRule
import kotlin.reflect.KClass

interface Formatter {
    fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String
}
