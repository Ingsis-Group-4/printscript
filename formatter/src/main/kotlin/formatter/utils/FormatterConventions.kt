package formatter.utils

import ast.AST
import formatter.Formatter
import formatter.rule.FormattingRule
import kotlin.reflect.KClass

fun formatNextNode(
    formatterMap: Map<KClass<out AST>, Formatter>,
    ast: AST,
    rule: FormattingRule,
): String {
    return (
        formatterMap[getClass(ast)]?.format(ast, rule, formatterMap)
            ?: throw IllegalArgumentException("No formatter found for ${getClass(ast)}")
    )
}

fun getClass(ast: AST): KClass<out AST> {
    return ast::class
}
