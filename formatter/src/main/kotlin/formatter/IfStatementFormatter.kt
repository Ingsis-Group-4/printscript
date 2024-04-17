package formatter

import ast.AST
import ast.IfStatement
import formatter.utils.formatNextNode
import kotlin.reflect.KClass

class IfStatementFormatter : Formatter {
    override fun format(
        node: AST,
        rule: FormattingRule,
        formatterMap: Map<KClass<out AST>, Formatter>,
    ): String {
        val ifNode = node as IfStatement
        val condition = formatNextNode(formatterMap, ifNode.getCondition(), rule)
        val thenBlock = formatNextNode(formatterMap, ifNode.getThenBlock(), rule)
        val elseBlock =
            if (hasElseBlock(ifNode)) {
                formatNextNode(formatterMap, ifNode.getElseBlock()!!, rule)
            } else {
                ""
            }
        return buildFormattedOutput(condition, thenBlock, elseBlock)
    }

    private fun hasElseBlock(ifNode: IfStatement) = ifNode.getElseBlock() != null

    private fun buildFormattedOutput(
        condition: String,
        thenBlock: String,
        elseBlock: String,
    ) = "if ($condition)\n{\n$thenBlock\n}${if (elseBlock.isNotBlank()) " else\n{\n$elseBlock\n}" else ""}"
}
