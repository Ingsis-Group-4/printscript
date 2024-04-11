package formatter

import ast.AST
import kotlin.reflect.KClass

interface Formatter {
    fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String
}
