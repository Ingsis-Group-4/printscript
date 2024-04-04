package rule

import ast.AssignationNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.LiteralNode
import ast.PrintLnNode
import ast.StatementNode
import ast.VariableType
import formatter.rule.LineBreakBeforePrintLnRule
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals

class LineBreakBeforePrintLnRuleTest {
    @Test
    fun shouldReturnUnchangedStatementsWhenDifferenceIsAcceptable() {
        val rule = LineBreakBeforePrintLnRule(2)
        val statements = createTestStatements(3)
        val result = rule.apply(1, statements)
        assertEquals(statements, result)
    }

    @Test
    fun shouldReturnModifiedStatementsWhenDifferenceIsNotAcceptable() {
        val rule = LineBreakBeforePrintLnRule(0)
        val statements = createTestStatements(2)
        val result = rule.apply(1, statements)
        assertEquals(2, result[1].getStart().line)
    }

    @Test
    fun shouldReturnUnchangedStatementsWhenCurrentStatementIsNotPrintLnNode() {
        val rule = LineBreakBeforePrintLnRule(2)
        val statements = listOf(createTestAssignationNode(1), createTestAssignationNode(3))
        val result = rule.apply(1, statements)
        assertEquals(statements, result)
    }

    @Test
    fun shouldReturnUnchangedStatementsWhenCurrentStatementIndexIsZero() {
        val rule = LineBreakBeforePrintLnRule(2)
        val statements = createTestStatements(3)
        val result = rule.apply(0, statements)
        assertEquals(statements, result)
    }

    private fun createTestStatements(lineDifference: Int): List<StatementNode> {
        return listOf(createTestAssignationNode(1), createTestPrintLnNode(1 + lineDifference))
    }

    private fun createTestAssignationNode(line: Int): AssignationNode {
        val literalNode = LiteralNode(10.0, Position(line, 5), Position(line, 6))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(line, 1), Position(line, 1))
        val equalsNode = EqualsNode(Position(line, 3), Position(line, 3))
        return AssignationNode(identifierNode, literalNode, equalsNode, Position(line, 1), Position(line, 6))
        // a = 10
        // 123456
    }

    private fun createTestPrintLnNode(line: Int): PrintLnNode {
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(line, 9), Position(line, 9))
        return PrintLnNode(identifierNode, Position(line, 1), Position(line, 10))
        // println(a)
        // 1234567890
    }
}
