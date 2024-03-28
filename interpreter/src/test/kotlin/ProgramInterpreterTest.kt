package interpreter

import ast.AssignationNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.ProgramNode
import ast.VariableDeclarationNode
import ast.VariableType
import position.Position
import kotlin.test.Test

class ProgramInterpreterTest {
    @Test
    fun testDeclarationAssignationAndPrint() {
        val input =
            listOf(
                VariableDeclarationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2)),
                    null,
                    Position(1, 1),
                    Position(1, 2),
                ),
                AssignationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 1)),
                    LiteralNode("a", Position(1, 1), Position(1, 1)),
                    Position(1, 1),
                    Position(1, 1),
                ),
                PrintLnNode(IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 1)), Position(1, 1), Position(1, 1)),
            )

        val interpreter = ProgramInterpreter(ProgramNode(input, Position(1, 1), Position(1, 1)))

        val result = interpreter.interpret()

        assert(result is VoidValue)
    }

    @Test
    fun testDeclarationAssignationAndReAssignation() {
        val input =
            listOf(
                VariableDeclarationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2)),
                    null,
                    Position(1, 1),
                    Position(1, 2),
                ),
                AssignationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(2, 2)),
                    LiteralNode("a", Position(1, 1), Position(1, 1)),
                    Position(1, 1),
                    Position(1, 1),
                ),
                AssignationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 1)),
                    LiteralNode("b", Position(1, 1), Position(1, 1)),
                    Position(1, 1),
                    Position(1, 1),
                ),
            )

        val interpreter = ProgramInterpreter(ProgramNode(input, Position(1, 1), Position(1, 1)))

        val result = interpreter.interpret()

        assert(result is VoidValue)
    }
}
