package rule

import ast.AssignationNode
import ast.ColonNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.ProgramNode
import ast.SumNode
import ast.VariableDeclarationNode
import ast.VariableType
import ast.VariableTypeNode
import formatter.AstRuleProcessor
import formatter.rule.SpaceAfterColonRule
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SpaceAfterColonRuleTest {
    @Test
    fun testRuleWithNoSpaceAndShouldNotChangeNodes() {
        // example:let a:Number = 10
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
        val rule = SpaceAfterColonRule(hasSpace = false)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 17))
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
    fun testRuleWithNoSpaceButShouldMoveNodeOneSpace() {
        // example:let a: Number = 10
        //         123456789012345678
        // should move type node one space to the left
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
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
        val rule = SpaceAfterColonRule(hasSpace = false)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 18))
        val result = astRuleProcessor.format(programNode)
        val newTypeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getStart(), newTypeNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getEnd(), newTypeNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode, colonNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).equalsNode, equalsNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).expression, expressionNode)
    }

    @Test
    fun testRuleWithNoSpaceButShouldMoveNodeMoreThanOneSpace() {
        // example:let a:   Number = 10
        //         12345678901234567890
        // should move type node 3 spaces to the left
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 10), Position(1, 15))
        val equalsNode = EqualsNode(Position(1, 17), Position(1, 17))
        val expressionNode = LiteralNode(10.0, Position(1, 19), Position(1, 20))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 20),
            )
        val statements = listOf(variableDeclarationNode)
        val rule = SpaceAfterColonRule(hasSpace = false)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 20))
        val result = astRuleProcessor.format(programNode)
        val newTypeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getStart(), newTypeNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getEnd(), newTypeNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode, colonNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).equalsNode, equalsNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).expression, expressionNode)
    }

    @Test
    fun testRuleWithSpaceAndShouldNotChangeNodes() {
        // example
        // let a: Number = 10
        // 1234567890123456789
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
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
        val rule = SpaceAfterColonRule(hasSpace = true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 18))
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
    fun testRuleWithSpaceAndShouldMoveSomeNodesOneSpace() {
        // example:let a:Number = 10
        //         12345678901234567
        // should move type node and all nodes after type node one space to the right
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
        val rule = SpaceAfterColonRule(hasSpace = true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 17))
        val result = astRuleProcessor.format(programNode)
        val newTypeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13))
        val newEqualsNode = EqualsNode(Position(1, 15), Position(1, 15))
        val newExpressionNode = LiteralNode(10.0, Position(1, 17), Position(1, 18))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getStart(), newTypeNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getEnd(), newTypeNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode, colonNode)
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getStart(), newEqualsNode.getStart()) }
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getEnd(), newEqualsNode.getEnd()) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getStart(), newExpressionNode.getStart()) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getEnd(), newExpressionNode.getEnd()) }
    }

    @Test
    fun testRuleWithSpaceAndShouldMoveSomeNodesAndExpressionNodeOneSpace() {
        // example:let a: Number = 2 + 3
        //         12345678901234567890
        // should move type node and all nodes after type node one space to the right
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 14), Position(1, 14))
        val firstExpressionNode = LiteralNode(2.0, Position(1, 16), Position(1, 16))
        val secondExpressionNode = LiteralNode(3.0, Position(1, 20), Position(1, 20))
        val sumNode =
            SumNode(
                firstExpressionNode,
                secondExpressionNode,
                OperatorNode(Position(1, 18), Position(1, 18), OperatorType.SUM),
                Position(1, 16),
                Position(1, 20),
            )
        val expressionNode = sumNode
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 20),
            )
        val statements = listOf(variableDeclarationNode)
        val rule = SpaceAfterColonRule(hasSpace = true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 20))
        val result = astRuleProcessor.format(programNode)
        val newTypeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13))
        val newEqualsNode = EqualsNode(Position(1, 15), Position(1, 15))
        val newFirstExpressionNode = LiteralNode(2.0, Position(1, 17), Position(1, 17))
        val newSecondExpressionNode = LiteralNode(3.0, Position(1, 21), Position(1, 21))
        val newSumNode =
            SumNode(
                newFirstExpressionNode,
                newSecondExpressionNode,
                OperatorNode(Position(1, 19), Position(1, 19), OperatorType.SUM),
                Position(1, 17),
                Position(1, 21),
            )
        val newExpressionNode = newSumNode
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getStart(), newTypeNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getEnd(), newTypeNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode, colonNode)
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getStart(), newEqualsNode.getStart()) }
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getEnd(), newEqualsNode.getEnd()) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getStart(), newExpressionNode.getStart()) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getEnd(), newExpressionNode.getEnd()) }
        assertIs<SumNode>((result.statements[0] as VariableDeclarationNode).expression)
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
            newFirstExpressionNode.getStart(),
        )
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
            newFirstExpressionNode.getEnd(),
        )
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
            newSecondExpressionNode.getStart(),
        )
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
            newSecondExpressionNode.getEnd(),
        )
    }

    @Test
    fun testRuleWithSpaceAndShouldMoveMoreThanOneSpace() {
        // example:let a:   Number = 2 + 3
        //         123456789012345678901234
        // should move type node and all nodes after type node one space to the right
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 10), Position(1, 15))
        val equalsNode = EqualsNode(Position(1, 17), Position(1, 17))
        val firstExpressionNode = LiteralNode(2.0, Position(1, 19), Position(1, 19))
        val secondExpressionNode = LiteralNode(3.0, Position(1, 23), Position(1, 23))
        val sumNode =
            SumNode(
                firstExpressionNode,
                secondExpressionNode,
                OperatorNode(Position(1, 21), Position(1, 21), OperatorType.SUM),
                Position(1, 19),
                Position(1, 23),
            )
        val expressionNode = sumNode
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 23),
            )
        val statements = listOf(variableDeclarationNode)
        val rule = SpaceAfterColonRule(hasSpace = true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 23))
        val result = astRuleProcessor.format(programNode)
        val newTypeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 8), Position(1, 13))
        assert(result is ProgramNode)
        assertIs<VariableDeclarationNode>((result as ProgramNode).statements[0])
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getStart(), newTypeNode.getStart())
        assertEquals((result.statements[0] as VariableDeclarationNode).typeNode.getEnd(), newTypeNode.getEnd())
        assertEquals((result.statements[0] as VariableDeclarationNode).keywordNode, keywordNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as VariableDeclarationNode).colonNode, colonNode)
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getStart(), equalsNode.getStart()) }
        (result.statements[0] as VariableDeclarationNode).equalsNode?.let { assertEquals(it.getEnd(), equalsNode.getEnd()) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getStart(), expressionNode.getStart()) }
        (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getEnd(), expressionNode.getEnd()) }
        assertIs<SumNode>((result.statements[0] as VariableDeclarationNode).expression)
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
            firstExpressionNode.getStart(),
        )
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
            firstExpressionNode.getEnd(),
        )
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
            secondExpressionNode.getStart(),
        )
        assertEquals(
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
            secondExpressionNode.getEnd(),
        )
    }

    @Test
    fun testRuleForAssignationNode() {
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 3), Position(1, 3))
        val expressionNode = LiteralNode(10.0, Position(1, 5), Position(1, 6))
        val assignationNode = AssignationNode(identifierNode, expressionNode, equalsNode, Position(1, 1), Position(1, 6))
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 6))
        val rule = SpaceAfterColonRule(hasSpace = true)
        val astRuleProcessor = AstRuleProcessor(listOf(rule))
        val result = astRuleProcessor.format(programNode)
        assertIs<ProgramNode>(result)
        assertIs<AssignationNode>(result.statements[0])
        assertEquals((result.statements[0] as AssignationNode).identifier, identifierNode)
        assertEquals((result.statements[0] as AssignationNode).equalsNode, equalsNode)
        assertEquals((result.statements[0] as AssignationNode).expression, expressionNode)
    }
}
