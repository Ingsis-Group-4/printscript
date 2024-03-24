package interpreter

import ast.AssignationNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.ProgramNode
import ast.VariableDeclarationNode
import ast.VariableType
import org.example.interpreter.ProgramInterpreter
import org.example.interpreter.VoidValue
import kotlin.test.Test

class ProgramInterpreterTest {
    @Test
    fun testDeclarationAssignationAndPrint() {
        val input =
            listOf(
                VariableDeclarationNode(IdentifierNode("x", VariableType.STRING)),
                AssignationNode(IdentifierNode("x", VariableType.STRING), LiteralNode("a")),
                PrintLnNode(IdentifierNode("x", VariableType.STRING)),
            )

        val interpreter = ProgramInterpreter(ProgramNode(input))

        val result = interpreter.interpret()

        assert(result is VoidValue)
    }

    @Test
    fun testDeclarationAssignationAndReAssignation() {
        val input =
            listOf(
                VariableDeclarationNode(IdentifierNode("x", VariableType.STRING)),
                AssignationNode(IdentifierNode("x", VariableType.STRING), LiteralNode("a")),
                AssignationNode(IdentifierNode("x", VariableType.STRING), LiteralNode("b")),
            )

        val interpreter = ProgramInterpreter(ProgramNode(input))

        val result = interpreter.interpret()

        assert(result is VoidValue)
    }
}
