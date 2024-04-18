package formatter.expression

import ast.AST
import formatter.Formatter
import formatter.rule.FormattingRule
import kotlin.math.roundToInt
import kotlin.reflect.KClass

class LiteralNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val literalNode = node as ast.LiteralNode<*>
        when (literalNode.value) {
            is Double -> {
                val value = (literalNode.value as Double).roundToInt()
                return value.toString()
            }
            is String -> return "\"${literalNode.value}\""
            else -> return literalNode.value.toString()
        }
    }
}
