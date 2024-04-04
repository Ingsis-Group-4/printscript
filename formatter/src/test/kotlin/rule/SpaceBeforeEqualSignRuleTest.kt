package rule

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
import formatter.AstRuleProcessor
import formatter.rule.SpaceBeforeEqualSignRule
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SpaceBeforeEqualSignRuleTest {
    @Test
    fun testNoSpaceAndShouldNotMoveNodes() {
        // example: let a:Number=10
        //          12345678901234567
        // should not change node
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
        val rule = SpaceBeforeEqualSignRule(false)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))

        val result = astRuleProcessor.format(programNode)
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
    fun testNoSpaceAndShouldMoveNode() {
        // let a:Number =10
        // 1234567890123456
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
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
                Position(1, 15),
            )
        val statements = listOf(variableDeclarationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 16))
        val rule = SpaceBeforeEqualSignRule(false)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val newEqualsNodePosition = Position(1, 13)
        val result = astRuleProcessor.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                statementResult.equalsNode?.let { assertEquals(newEqualsNodePosition, it.getStart()) }
                statementResult.equalsNode?.let { assertEquals(newEqualsNodePosition, it.getEnd()) }
                assert(statementResult.keywordNode == keywordNode)
                assert(statementResult.expression == expressionNode)
            }
        }
    }

    @Test
    fun testSpaceAndShouldMoveNodes() {
        // example: let a:Number=10
        //          12345678901234567
        // should not change node
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
        val rule = SpaceBeforeEqualSignRule(true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 15))
        val newEqualsNodePosition = Position(1, 14)
        val newExpressionNode = LiteralNode(10.0, Position(1, 15), Position(1, 16))
        val result = astRuleProcessor.format(programNode)
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode, typeNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode, colonNode)
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getStart(), newEqualsNodePosition) }
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getEnd(), newEqualsNodePosition) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getStart(), newExpressionNode.getStart()) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getEnd(), newExpressionNode.getEnd()) }
    }

    @Test
    fun testSpaceAndShouldNotMoveAnyNode() {
        // example: let a:Number =10
        //          12345678901234567
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
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
        val rule = SpaceBeforeEqualSignRule(true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))

        val result = astRuleProcessor.format(programNode)
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
    fun testSpaceAndShouldMoveMoreThanOneSpace() {
        // example: let a:Number    =10
        //          1234567890123456789
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 17), Position(1, 17))
        val expressionNode = LiteralNode(10.0, Position(1, 18), Position(1, 19))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 19),
            )
        val statements = listOf(variableDeclarationNode)
        val rule = SpaceBeforeEqualSignRule(true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 19))
        val newEqualsNodePosition = Position(1, 14)
        val result = astRuleProcessor.format(programNode)
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode, typeNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode, colonNode)
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getStart(), newEqualsNodePosition) }
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getEnd(), newEqualsNodePosition) }
        assertEquals((result.statements[0] as VariableDeclarationNode).expression, expressionNode)
    }

    @Test
    fun testNoSpaceAssignationNode() {
        // example: a=10
        //          12345
        // should not change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 2), Position(1, 2))
        val expressionNode = LiteralNode(10.0, Position(1, 3), Position(1, 4))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 4),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 4))
        val rule = SpaceBeforeEqualSignRule(false)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val result = astRuleProcessor.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.expression == expressionNode)
            }
        }
    }

    @Test
    fun testNoSpaceAndShouldMoveAssignationNode() {
        // example: a =10
        //          12345
        // should not change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 3), Position(1, 3))
        val expressionNode = LiteralNode(10.0, Position(1, 4), Position(1, 5))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 4),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 5))
        val astRuleProcessor = AstRuleProcessor(listOf(SpaceBeforeEqualSignRule(false)))
        val newEqualsNodePosition = Position(1, 2)
        val result = astRuleProcessor.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode.getStart() == newEqualsNodePosition)
                assert(statementResult.equalsNode.getEnd() == newEqualsNodePosition)
                assert(statementResult.expression == expressionNode)
            }
        }
    }

    @Test
    fun testSpaceAndShouldNotMoveAssignationNode() {
        // example: a =10
        //          12345
        // should not change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 3), Position(1, 3))
        val expressionNode = LiteralNode(10.0, Position(1, 4), Position(1, 5))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 4),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 5))
        val astRuleProcessor = AstRuleProcessor(listOf(SpaceBeforeEqualSignRule(true)))
        val result = astRuleProcessor.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.expression == expressionNode)
            }
        }
    }

    @Test
    fun testSpaceAndShouldMoveNodesAssignationNode() {
        // example: a=10
        //          12345
        // should not change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 2), Position(1, 2))
        val expressionNode = LiteralNode(10.0, Position(1, 3), Position(1, 4))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 4),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 4))
        val astRuleProcessor = AstRuleProcessor(listOf(SpaceBeforeEqualSignRule(true)))
        val newEqualsNodePosition = Position(1, 3)
        val newExpressionNode = LiteralNode(10.0, Position(1, 4), Position(1, 5))
        val result = astRuleProcessor.format(programNode)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode.getStart() == newEqualsNodePosition)
                assert(statementResult.equalsNode.getEnd() == newEqualsNodePosition)
                assert(statementResult.expression.getStart() == newExpressionNode.getStart())
                assert(statementResult.expression.getEnd() == newExpressionNode.getEnd())
            }
        }
    }

    @Test
    fun testSpaceAndShouldMoveSeveralSpacesAssignationNode() {
        // example: a    =10
        //          123456789
        // should not change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 6), Position(1, 6))
        val expressionNode = LiteralNode(10.0, Position(1, 7), Position(1, 8))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 8),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 8))
        val astRuleProcessor = AstRuleProcessor(listOf(SpaceBeforeEqualSignRule(true)))
        val result = astRuleProcessor.format(programNode)
        val newEqualsNodePosition = Position(1, 3)
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode.getStart() == newEqualsNodePosition)
                assert(statementResult.equalsNode.getEnd() == newEqualsNodePosition)
                assert(statementResult.expression == expressionNode)
            }
        }
    }
}
