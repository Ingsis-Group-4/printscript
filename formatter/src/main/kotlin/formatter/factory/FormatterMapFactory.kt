package formatter.factory

import ast.AST
import ast.AssignationNode
import ast.BinaryOperation
import ast.BlockNode
import ast.ConstNode
import ast.DeclarationNode
import ast.FunctionNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.IfStatement
import ast.KeywordNode
import ast.LetNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.UnaryOperation
import formatter.AssignationNodeFormatter
import formatter.BinaryOperationNodeFormatter
import formatter.BlockNodeFormatter
import formatter.DeclarationNodeFormatter
import formatter.Formatter
import formatter.FunctionCallFormatter
import formatter.FunctionStatementNodeFormatter
import formatter.IdentifierNodeFormatter
import formatter.IfStatementFormatter
import formatter.LiteralNodeFormatter
import formatter.PrintLnNodeFormatter
import formatter.ReadEnvNodeFormatter
import formatter.ReadInputNodeFormatter
import formatter.UnaryOperationNodeFormatter
import formatter.utils.DefaultOperatorFormatter
import kotlin.reflect.KClass

class FormatterMapFactory {
    fun createFormatterMap(): Map<KClass<out AST>, Formatter> {
        val keywordMap =
            mapOf<KClass<out KeywordNode>, String>(
                LetNode::class to "let",
                ConstNode::class to "const",
            )
        val functionNameMap =
            mapOf<KClass<out FunctionNode>, String>(
                PrintLnNode::class to "println",
                ReadEnvNode::class to "readEnv",
                ReadInputNode::class to "readInput",
            )
        return mapOf(
            LiteralNode::class to LiteralNodeFormatter(),
            IdentifierNode::class to IdentifierNodeFormatter(),
            DeclarationNode::class to DeclarationNodeFormatter(keywordMap),
            AssignationNode::class to AssignationNodeFormatter(),
            BinaryOperation::class to BinaryOperationNodeFormatter(DefaultOperatorFormatter),
            UnaryOperation::class to UnaryOperationNodeFormatter(),
            PrintLnNode::class to PrintLnNodeFormatter(),
            FunctionStatementNode::class to FunctionStatementNodeFormatter(),
            IfStatement::class to IfStatementFormatter(),
            ReadInputNode::class to ReadInputNodeFormatter(),
            BlockNode::class to BlockNodeFormatter(),
            ReadEnvNode::class to ReadEnvNodeFormatter(),
            FunctionNode::class to FunctionCallFormatter(functionNameMap),
        )
    }
}
