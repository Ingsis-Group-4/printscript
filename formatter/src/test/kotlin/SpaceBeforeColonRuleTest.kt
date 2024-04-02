import ast.AssignationNode
import ast.ColonNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.ProgramNode
import ast.VariableDeclarationNode
import ast.VariableType
import ast.VariableTypeNode
import formatter.Formatter
import formatter.rule.SpaceBeforeColonRule
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SpaceBeforeColonRuleTest {
    @Test
    fun testRuleWithNoSpaceAndJustOneVariableDeclarationNode() {
        // example: let a:Number = 10
        //         12345678901234567
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 14), Position(1, 14))
        val expressionNode = LiteralNode(10.0, Position(1, 16), Position(1, 17))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 17),
            )
        val statements = listOf(variableDeclarationNode)

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 17))
        val rule = SpaceBeforeColonRule(false)
        val formatter = Formatter(listOf(rule))

        val result = formatter.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.keywordNode == keywordNode)
                assert(statementResult.expression == expressionNode)
            }
        }
    }

    @Test
    fun testRuleWithNoSpaceAndJustOneVariableDeclarationNodeAndShouldChangeNode() {
        // example: let a :Number=10
        //         1234567890123456
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13))
        val equalsNode = EqualsNode(Position(1, 14), Position(1, 14))
        val expressionNode = LiteralNode(10.0, Position(1, 15), Position(1, 16))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 16),
            )
        val statements = listOf(variableDeclarationNode)

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 16))
        val rule = SpaceBeforeColonRule(false)
        val formatter = Formatter(listOf(rule))

        val result = formatter.format(programNode)
        val newColonNode = ColonNode(Position(1, 6), Position(1, 6))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode.getStart(), newColonNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode.getEnd(), newColonNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode, typeNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).equalsNode, equalsNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).expression, expressionNode)
    }

    @Test
    fun testRuleWithNoSpaceAndShouldMoveColonNodeMoreThanOneSpace() {
        // example: let a   :Number=10
        //         123456789012345678
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 9), Position(1, 9))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 10), Position(1, 15))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val expressionNode = LiteralNode(10.0, Position(1, 17), Position(1, 18))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 18),
            )
        val statements = listOf(variableDeclarationNode)

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 18))
        val rule = SpaceBeforeColonRule(false)
        val formatter = Formatter(listOf(rule))

        val result = formatter.format(programNode)
        val newColonNode = ColonNode(Position(1, 6), Position(1, 6))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode.getStart(), newColonNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode, typeNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).equalsNode, equalsNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).expression, expressionNode)
    }

    @Test
    fun testRuleWithSpace() {
        // example: let a :Number = 10
        //         12345678901234567
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13))
        val equalsNode = EqualsNode(Position(1, 15), Position(1, 15))
        val expressionNode = LiteralNode(10.0, Position(1, 17), Position(1, 18))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 18),
            )
        val statements = listOf(variableDeclarationNode)

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 18))
        val rule = SpaceBeforeColonRule(true)
        val formatter = Formatter(listOf(rule))

        val result = formatter.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.keywordNode == keywordNode)
                assert(statementResult.expression == expressionNode)
            }
        }
    }

    @Test
    fun testRuleWithSpaceAndShouldChangeNode() {
        // example: let a:Number=10
        //         1234567890123456
        // should change node. In this case, everything after the colon node should be moved one space to the right
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 13), Position(1, 13))
        val expressionNode = LiteralNode(10.0, Position(1, 14), Position(1, 15))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 15),
            )
        val statements = listOf(variableDeclarationNode)

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 15))
        val rule = SpaceBeforeColonRule(true)
        val formatter = Formatter(listOf(rule))

        val result = formatter.format(programNode)
        val newColonNode = ColonNode(Position(1, 7), Position(1, 7))
        val newTypeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13))
        val newEqualsNode = EqualsNode(Position(1, 14), Position(1, 14))
        val newExpressionNode = LiteralNode(10.0, Position(1, 15), Position(1, 16))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode.getStart(), newColonNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode.getEnd(), newColonNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        println((result.statements[0] as VariableDeclarationNode).typeNode.getStart().column)
        println((result.statements[0] as VariableDeclarationNode).typeNode.getEnd().column)
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getStart(), newTypeNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getEnd(), newTypeNode.getEnd())
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let {
            assertEquals(
                it.getStart(),
                newEqualsNode.getStart(),
            )
        }
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let {
            assertEquals(
                it.getEnd(),
                newEqualsNode.getEnd(),
            )
        }
        (result.statements[0] as VariableDeclarationNode).expression?.let {
            assertEquals(
                it.getStart(),
                newExpressionNode.getStart(),
            )
        }
        (result.statements[0] as VariableDeclarationNode).expression?.let {
            assertEquals(
                it.getEnd(),
                newExpressionNode.getEnd(),
            )
        }
    }

    @Test
    fun testRuleWithSpaceAndShouldMoveColonNodeMoreThanOneSpace() {
        // example: let a   :Number=10
        //         123456789012345678
        // should change node. In this case,only the colon node will be moved to the left
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 9), Position(1, 9))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 10), Position(1, 15))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val expressionNode = LiteralNode(10.0, Position(1, 17), Position(1, 18))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 18),
            )
        val statements = listOf(variableDeclarationNode)

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 18))
        val rule = SpaceBeforeColonRule(true)
        val formatter = Formatter(listOf(rule))

        val result = formatter.format(programNode)
        val newColonNode = ColonNode(Position(1, 7), Position(1, 7))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode.getStart(), newColonNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode.getEnd(), newColonNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode, typeNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).equalsNode, equalsNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).expression, expressionNode)
    }

    @Test
    fun testRuleForAssignationNode() {
        // example: a = 10
        //         12345678
        // should not change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 3), Position(1, 3))
        val expressionNode = LiteralNode(10.0, Position(1, 5), Position(1, 6))
        val assignationNode = AssignationNode(identifierNode, expressionNode, equalsNode, Position(1, 1), Position(1, 6))
        val statements = listOf(assignationNode)

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 6))
        val rule = SpaceBeforeColonRule(true)
        val formatter = Formatter(listOf(rule))

        val result = formatter.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.expression == expressionNode)
            }
        }
    }
}
