package formatter

import ast.AST
import ast.KeywordNode
import java.util.Locale
import kotlin.reflect.KClass

class DeclarationNodeFormatter(private val keywordMap: Map<KClass<out KeywordNode>, String>) : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val declarationNode = node as ast.DeclarationNode
        return buildString {
            append(keywordMap[declarationNode.keywordNode::class])
            append(" ")
            append(declarationNode.identifier.variableName)
            append(if (rule.hasSpaceBetweenColon) " : " else ":")
            append(humanize(declarationNode.typeNode.variableType.name))
            declarationNode.equalsNode?.let {
                append(if (rule.hasSpaceBetweenEqualSign) " = " else "=")
                append(
                    formatterMap[declarationNode.expression!!::class]?.format(declarationNode.expression!!, rule, formatterMap)
                        ?: throw IllegalArgumentException("No formatter found for ${declarationNode.expression!!::class}"),
                )
            }
        }
    }
}

private fun humanize(input: String): String {
    return input.lowercase(Locale.getDefault())
}
