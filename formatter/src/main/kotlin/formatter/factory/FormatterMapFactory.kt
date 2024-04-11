package formatter.factory

import ast.AST
import ast.AssignationNode
import ast.BinaryOperation
import ast.DeclarationNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.KeywordNode
import ast.LetNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.UnaryOperation
import formatter.AssignationNodeFormatter
import formatter.BinaryOperationNodeFormatter
import formatter.DeclarationNodeFormatter
import formatter.Formatter
import formatter.FunctionStatementNodeFormatter
import formatter.IdentifierNodeFormatter
import formatter.LiteralNodeFormatter
import formatter.PrintLnNodeFormatter
import formatter.UnaryOperationNodeFormatter
import kotlin.reflect.KClass

class FormatterMapFactory {
    fun createFormatterMap(): Map<KClass<out AST>, Formatter> {
        val keywordMap =
            mapOf<KClass<out KeywordNode>, String>(
                LetNode::class to "let",
            )
        return mapOf(
            LiteralNode::class to LiteralNodeFormatter(),
            IdentifierNode::class to IdentifierNodeFormatter(),
            DeclarationNode::class to DeclarationNodeFormatter(keywordMap),
            AssignationNode::class to AssignationNodeFormatter(),
            BinaryOperation::class to BinaryOperationNodeFormatter(),
            UnaryOperation::class to UnaryOperationNodeFormatter(),
            PrintLnNode::class to PrintLnNodeFormatter(),
            FunctionStatementNode::class to FunctionStatementNodeFormatter(),
        )
    }
}
