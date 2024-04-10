package formatter

import ast.AST
import ast.LetNode
import kotlin.reflect.KClass

class DeclarationNodeFormatter(private val keywordMap: Map<KClass<LetNode>, String>) : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
    ): String {
        val declarationNode = node as ast.DeclarationNode
        return buildString {
            append(keywordMap[declarationNode.keywordNode::class])
            append(" ")
            append(declarationNode.identifier.variableName)
            append(if (rule.spaceBetweenColon) " : " else ":")
            declarationNode.equalsNode?.let {
                append(if (rule.spaceBetweenEqualSign) " = " else "=")
                append(getExpression(declarationNode.expression!!, rule))
            }
        }
    }
}
