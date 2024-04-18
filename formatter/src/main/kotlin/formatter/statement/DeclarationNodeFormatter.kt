package formatter.statement

import ast.AST
import ast.KeywordNode
import formatter.Formatter
import formatter.rule.FormattingRule
import formatter.utils.formatNextNode
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
                    formatNextNode(formatterMap, declarationNode.expression!!, rule),
                )
            }
        }
    }

    private fun humanize(input: String): String {
        return input.lowercase(Locale.getDefault())
    }
}
