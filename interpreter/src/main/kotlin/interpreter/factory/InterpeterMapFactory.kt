package interpreter.factory

import ast.AST
import ast.AssignationNode
import ast.DeclarationNode
import ast.FunctionStatementNode
import ast.IfStatement
import interpreter.FunctionStatementInterpreter
import interpreter.IfStatementInterpreter
import interpreter.Interpreter
import interpreter.VariableStatementInterpreter
import version.Version
import kotlin.reflect.KClass

fun getInterpreterMap(version: Version): Map<KClass<out AST>, Interpreter> {
    val v1Map: Map<KClass<out AST>, Interpreter> =
        mapOf(
            FunctionStatementNode::class to FunctionStatementInterpreter(),
            DeclarationNode::class to VariableStatementInterpreter(),
            AssignationNode::class to VariableStatementInterpreter(),
        )

    val v2Map =
        mapOf(
            IfStatement::class to IfStatementInterpreter(),
        )

    if (version == Version.V2) {
        return v1Map + v2Map
    }

    return v1Map
}
