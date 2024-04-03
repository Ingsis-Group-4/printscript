package stringifier

import ast.AssignationNode
import ast.ColonNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.VariableDeclarationNode
import ast.VariableType
import ast.VariableTypeNode
import formatter.stringifier.VariableStatementNodeStringifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import position.Position

class VariableStatementNodeStringifierTest {
    private val stringifier = VariableStatementNodeStringifier()

    @Test
    fun `should stringify assignation node correctly`() {
        val assignationNode =
            AssignationNode(
                identifier = IdentifierNode("x", null, Position(1, 1), Position(1, 1)),
                equalsNode = EqualsNode(Position(1, 3), Position(1, 3)),
                expression = LiteralNode(1, Position(1, 5), Position(1, 5)),
                start = Position(1, 1),
                end = Position(1, 6),
            )

        val result = stringifier.stringify(assignationNode)

        assertEquals("x = 1", result)
    }

    @Test
    fun `should stringify variable declaration node correctly`() {
        val variableDeclarationNode =
            VariableDeclarationNode(
                keywordNode = LetNode(Position(1, 1), Position(1, 3)),
                identifier = IdentifierNode("x", VariableType.NUMBER, Position(1, 5), Position(1, 5)),
                colonNode = ColonNode(Position(1, 6), Position(1, 6)),
                typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13)),
                equalsNode = EqualsNode(Position(1, 15), Position(1, 15)),
                expression = LiteralNode(1, Position(1, 17), Position(1, 17)),
                start = Position(1, 1),
                end = Position(1, 18),
            )

        val result = stringifier.stringify(variableDeclarationNode)

        assertEquals("let x: Number = 1", result)
    }

    @Test
    fun `should stringify variable declaration node without expression correctly`() {
        val variableDeclarationNode =
            VariableDeclarationNode(
                keywordNode = LetNode(Position(1, 1), Position(1, 3)),
                identifier = IdentifierNode("x", null, Position(1, 5), Position(1, 5)),
                colonNode = ColonNode(Position(1, 6), Position(1, 6)),
                typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13)),
                equalsNode = null,
                expression = null,
                start = Position(1, 1),
                end = Position(1, 14),
            )

        val result = stringifier.stringify(variableDeclarationNode)

        assertEquals("let x: Number", result)
    }

    @Test
    fun `should stringify variable string declaration`() {
        val variableDeclarationNode =
            VariableDeclarationNode(
                keywordNode = LetNode(Position(1, 1), Position(1, 3)),
                identifier = IdentifierNode("x", VariableType.STRING, Position(1, 5), Position(1, 5)),
                colonNode = ColonNode(Position(1, 6), Position(1, 6)),
                typeNode = VariableTypeNode(VariableType.STRING, Position(1, 8), Position(1, 13)),
                equalsNode = EqualsNode(Position(1, 15), Position(1, 15)),
                expression = LiteralNode("Hello", Position(1, 17), Position(1, 22)),
                start = Position(1, 1),
                end = Position(1, 23),
            )

        val result = stringifier.stringify(variableDeclarationNode)

        assertEquals("let x: String = \"Hello\"", result)
    }
}
