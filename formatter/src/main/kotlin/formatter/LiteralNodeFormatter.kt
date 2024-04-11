package formatter

import ast.AST
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
            else -> return literalNode.value.toString()
        }
    }
}
