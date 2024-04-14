package sca.rule

import ast.AST
import ast.AssignationNode
import ast.DeclarationNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.ProgramNode
import ast.ReadInputNode
import ast.StatementNode
import sca.checkProgramNode

class ReadInputArgumentRule : Rule {
    override fun check(ast: AST): RuleResult {
        return when (ast) {
            is ProgramNode -> checkProgramNode(ast, ::checkStatementNode)
            is StatementNode -> checkStatementNode(ast)
            else -> RuleSuccess
        }
    }

    private fun checkStatementNode(ast: StatementNode): RuleResult {
        return when (ast) {
            is AssignationNode -> RuleSuccess
            is DeclarationNode -> RuleSuccess
            is FunctionStatementNode -> checkFunctionStatementNode(ast)
            else -> RuleSuccess
        }
    }

    private fun checkFunctionStatementNode(ast: FunctionStatementNode): RuleResult {
        return when (val functionNode = ast.getFunctionNode()) {
            is ReadInputNode -> checkReadInputNode(functionNode)
            else -> RuleSuccess
        }
    }

    private fun checkReadInputNode(readInputNode: ReadInputNode): RuleResult {
        return when (val expression = readInputNode.getExpression()) {
            is IdentifierNode -> RuleSuccess
            is LiteralNode<*> -> RuleSuccess
            else -> RuleFailures(listOf(FailurePayload("ReadInputNode argument must be an IdentifierNode", expression.getStart())))
        }
    }
}
