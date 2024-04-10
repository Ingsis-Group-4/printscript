package interpreter

import ast.AssignationNode
import ast.ColonNode
import ast.DeclarationNode
import ast.EqualsNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.ProgramNode
import ast.VariableType
import ast.VariableTypeNode
import position.Position
import kotlin.test.Test

class ProgramInterpreterTest {
    @Test
    fun testDeclarationAssignationAndPrint() {
        val input =
            listOf(
                DeclarationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2)),
                    null,
                    LetNode(Position(1, 1), Position(1, 1)),
                    ColonNode(Position(1, 1), Position(1, 1)),
                    VariableTypeNode(VariableType.STRING, Position(1, 1), Position(1, 1)),
                    null,
                    Position(1, 1),
                    Position(1, 2),
                ),
                AssignationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 1)),
                    LiteralNode("a", Position(1, 1), Position(1, 1)),
                    EqualsNode(Position(1, 1), Position(1, 1)),
                    Position(1, 1),
                    Position(1, 1),
                ),
                FunctionStatementNode(
                    Position(1, 1),
                    Position(1, 1),
                    PrintLnNode(
                        IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 1)),
                        Position(1, 1),
                        Position(1, 1),
                    ),
                ),
            )

        val interpreter =
            ProgramInterpreter()

        val result =
            interpreter.interpret(
                ProgramNode(
                    input,
                    Position(1, 1),
                    Position(1, 1),
                ),
            )

        val logs = result.logs

        assert(logs.size == 1)
        assert(logs[0] == "a")
    }

    @Test
    fun testDeclarationAssignationAndReAssignation() {
        val input =
            listOf(
                DeclarationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 2)),
                    null,
                    LetNode(Position(1, 1), Position(1, 1)),
                    ColonNode(Position(1, 1), Position(1, 1)),
                    VariableTypeNode(VariableType.STRING, Position(1, 1), Position(1, 1)),
                    null,
                    Position(1, 1),
                    Position(1, 2),
                ),
                AssignationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(2, 2)),
                    LiteralNode("a", Position(1, 1), Position(1, 1)),
                    EqualsNode(Position(1, 1), Position(1, 1)),
                    Position(1, 1),
                    Position(1, 1),
                ),
                AssignationNode(
                    IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 1)),
                    LiteralNode("b", Position(1, 1), Position(1, 1)),
                    EqualsNode(Position(1, 1), Position(1, 1)),
                    Position(1, 1),
                    Position(1, 1),
                ),
                FunctionStatementNode(
                    Position(1, 1),
                    Position(1, 1),
                    PrintLnNode(
                        IdentifierNode("x", VariableType.STRING, Position(1, 1), Position(1, 1)),
                        Position(1, 1),
                        Position(1, 1),
                    ),
                ),
            )

        val interpreter =
            ProgramInterpreter()

        val result =
            interpreter.interpret(
                ProgramNode(
                    input,
                    Position(1, 1),
                    Position(1, 1),
                ),
            )

        assert(result.logs.size == 1)
        assert(result.logs[0] == "b")
    }
}
