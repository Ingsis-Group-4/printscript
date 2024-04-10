package formatter

import ast.AST
import kotlin.reflect.KClass

class LiteralNodeFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val literalNode = node as ast.LiteralNode<*>
        return literalNode.value.toString()
    }
}
