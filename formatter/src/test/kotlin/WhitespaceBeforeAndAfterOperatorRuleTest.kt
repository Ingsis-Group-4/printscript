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
import formatter.rule.WhitespaceBeforeAndAfterOperatorRule
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class WhitespaceBeforeAndAfterOperatorRuleTest {
    private val rule = WhitespaceBeforeAndAfterOperatorRule(true)
    private val astRuleProcessor = AstRuleProcessor(listOf(rule))

    @Test
    fun testShouldReturnNodesUnchanged() {
        // example: let a :Number = 10 + 2
        //         1234567890123456789012345
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 9), Position(1, 14))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val firstExpressionNode = LiteralNode(10.0, Position(1, 18), Position(1, 19))
        val secondExpressionNode = LiteralNode(2.0, Position(1, 23), Position(1, 23))
        val operatorNode = OperatorNode(Position(1, 21), Position(1, 21), OperatorType.SUM)
        val expressionNode = SumNode(firstExpressionNode, secondExpressionNode, operatorNode, Position(1, 18), Position(1, 23))
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
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 23))
        val result = astRuleProcessor.format(programNode)
        assertIs<ProgramNode>(result)
        assertIs<VariableDeclarationNode>(result.statements[0])
        assertEquals(expressionNode.getStart(), (result.statements[0] as VariableDeclarationNode).expression?.getStart())
        assertEquals(expressionNode.getEnd(), (result.statements[0] as VariableDeclarationNode).expression?.getEnd())
        assertEquals(
            operatorNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getStart(),
        )
        assertEquals(
            operatorNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getEnd(),
        )
        assertEquals(
            firstExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
        )
        assertEquals(
            firstExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
        )
        assertEquals(
            secondExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
        )
        assertEquals(
            secondExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
        )
    }

    @Test
    fun testShouldChangeTheRightNodeOnly() {
        // example: let a :Number = 10 +    2
        //         12345678901234567890123456
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 9), Position(1, 14))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val firstExpressionNode = LiteralNode(10.0, Position(1, 18), Position(1, 19))
        val secondExpressionNode = LiteralNode(2.0, Position(1, 26), Position(1, 26))
        val operatorNode = OperatorNode(Position(1, 21), Position(1, 21), OperatorType.SUM)
        val expressionNode = SumNode(firstExpressionNode, secondExpressionNode, operatorNode, Position(1, 18), Position(1, 26))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 26),
            )
        val statements = listOf(variableDeclarationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 26))
        val result = astRuleProcessor.format(programNode)
        val newSecondExpressionNode = LiteralNode(2.0, Position(1, 23), Position(1, 23))
        assertIs<ProgramNode>(result)
        assertIs<VariableDeclarationNode>(result.statements[0])
        assertEquals(
            operatorNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getStart(),
        )
        assertEquals(
            operatorNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getEnd(),
        )
        assertEquals(
            firstExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
        )
        assertEquals(
            firstExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
        )
        assertEquals(
            newSecondExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
        )
        assertEquals(
            newSecondExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
        )
    }

    @Test
    fun testShouldMoveEachNodeOneSpaceToTheLeft() {
        // example: let a :Number = 10  + 2
        //         12345678901234567890123456
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 9), Position(1, 14))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val firstExpressionNode = LiteralNode(10.0, Position(1, 18), Position(1, 19))
        val secondExpressionNode = LiteralNode(2.0, Position(1, 24), Position(1, 24))
        val operatorNode = OperatorNode(Position(1, 22), Position(1, 22), OperatorType.SUM)
        val expressionNode = SumNode(firstExpressionNode, secondExpressionNode, operatorNode, Position(1, 18), Position(1, 24))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 24),
            )
        val statements = listOf(variableDeclarationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 24))
        val result = astRuleProcessor.format(programNode)
        val newOperatorNode = OperatorNode(Position(1, 21), Position(1, 21), OperatorType.SUM)
        val newSecondExpressionNode = LiteralNode(2.0, Position(1, 23), Position(1, 23))
        assertIs<ProgramNode>(result)
        assertIs<VariableDeclarationNode>(result.statements[0])
        assertEquals(
            newOperatorNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getStart(),
        )
        assertEquals(
            newOperatorNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getEnd(),
        )
        assertEquals(
            firstExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
        )
        assertEquals(
            firstExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
        )
        assertEquals(
            newSecondExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
        )
        assertEquals(
            newSecondExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
        )
    }

    @Test
    fun testShouldMoveEachNodeOneSpaceToTheRight() {
        // example: let a :Number = 10+2
        //         12345678901234567890123456
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 9), Position(1, 14))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val firstExpressionNode = LiteralNode(10.0, Position(1, 18), Position(1, 19))
        val secondExpressionNode = LiteralNode(2.0, Position(1, 21), Position(1, 21))
        val operatorNode = OperatorNode(Position(1, 20), Position(1, 20), OperatorType.SUM)
        val expressionNode = SumNode(firstExpressionNode, secondExpressionNode, operatorNode, Position(1, 18), Position(1, 21))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 21),
            )
        val statements = listOf(variableDeclarationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 21))
        val result = astRuleProcessor.format(programNode)
        val newOperatorNode = OperatorNode(Position(1, 21), Position(1, 21), OperatorType.SUM)
        val newSecondExpressionNode = LiteralNode(2.0, Position(1, 23), Position(1, 23))
        assertIs<ProgramNode>(result)
        assertIs<VariableDeclarationNode>(result.statements[0])
        assertEquals(
            newOperatorNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getStart(),
        )
        assertEquals(
            newOperatorNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getEnd(),
        )
        assertEquals(
            firstExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
        )
        assertEquals(
            firstExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
        )
        assertEquals(
            newSecondExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
        )
        assertEquals(
            newSecondExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
        )
    }

    @Test
    fun testWithComplexTerms() {
        // example: let a :Number = 1+2  + 3+4
        //         123456789012345678901234567890
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 9), Position(1, 14))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val oneLiteral = LiteralNode(1.0, Position(1, 18), Position(1, 18))
        val twoLiteral = LiteralNode(2.0, Position(1, 20), Position(1, 20))
        val threeLiteral = LiteralNode(3.0, Position(1, 25), Position(1, 25))
        val fourLiteral = LiteralNode(4.0, Position(1, 27), Position(1, 27))
        val firstOperatorNode = OperatorNode(Position(1, 19), Position(1, 19), OperatorType.SUM)
        val secondOperatorNode = OperatorNode(Position(1, 23), Position(1, 23), OperatorType.SUM)
        val thirdOperatorNode = OperatorNode(Position(1, 26), Position(1, 26), OperatorType.SUM)
        val firstExpressionNode = SumNode(oneLiteral, twoLiteral, firstOperatorNode, Position(1, 18), Position(1, 20))
        val secondExpressionNode = SumNode(threeLiteral, fourLiteral, thirdOperatorNode, Position(1, 25), Position(1, 27))
        val expressionNode = SumNode(firstExpressionNode, secondExpressionNode, secondOperatorNode, Position(1, 18), Position(1, 27))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 24),
            )
        val statements = listOf(variableDeclarationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 27))
        val result = astRuleProcessor.format(programNode)
        val newTwoLiteralNode = LiteralNode(2.0, Position(1, 22), Position(1, 22))
        val newThreeLiteralNode = LiteralNode(3.0, Position(1, 26), Position(1, 26))
        val newFourLiteralNode = LiteralNode(4.0, Position(1, 30), Position(1, 30))
        val newFirstOperatorNode = OperatorNode(Position(1, 20), Position(1, 20), OperatorType.SUM)
        val newSecondOperatorNode = OperatorNode(Position(1, 24), Position(1, 24), OperatorType.SUM)
        val newThirdOperatorNode = OperatorNode(Position(1, 28), Position(1, 28), OperatorType.SUM)
        val newFirstExpressionNode = SumNode(oneLiteral, newTwoLiteralNode, newFirstOperatorNode, Position(1, 18), Position(1, 22))
        val newSecondExpressionNode =
            SumNode(newThreeLiteralNode, newFourLiteralNode, newThirdOperatorNode, Position(1, 26), Position(1, 30))
        assertIs<ProgramNode>(result)
        assertIs<VariableDeclarationNode>(result.statements[0])
        assertEquals(
            newSecondOperatorNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getStart(),
        )
        assertEquals(
            newSecondOperatorNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getEnd(),
        )
        assertEquals(
            newFirstExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
        )
        assertEquals(
            newFirstExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
        )
        assertEquals(
            newSecondExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
        )
        assertEquals(
            newSecondExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
        )
    }

    @Test
    fun testWithMoreComplexTerms2() {
        // example: let a :Number = 1+2+3+4
        //         123456789012345678901234567890
        // should change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 7), Position(1, 7))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 9), Position(1, 14))
        val equalsNode = EqualsNode(Position(1, 16), Position(1, 16))
        val oneLiteral = LiteralNode(1.0, Position(1, 18), Position(1, 18))
        val twoLiteral = LiteralNode(2.0, Position(1, 20), Position(1, 20))
        val threeLiteral = LiteralNode(3.0, Position(1, 22), Position(1, 22))
        val fourLiteral = LiteralNode(4.0, Position(1, 24), Position(1, 24))
        val firstOperatorNode = OperatorNode(Position(1, 19), Position(1, 19), OperatorType.SUM)
        val secondOperatorNode = OperatorNode(Position(1, 21), Position(1, 21), OperatorType.SUM)
        val thirdOperatorNode = OperatorNode(Position(1, 23), Position(1, 23), OperatorType.SUM)
        val firstExpressionNode = SumNode(oneLiteral, twoLiteral, firstOperatorNode, Position(1, 18), Position(1, 20))
        val secondExpressionNode = SumNode(threeLiteral, fourLiteral, thirdOperatorNode, Position(1, 22), Position(1, 24))
        val expressionNode = SumNode(firstExpressionNode, secondExpressionNode, secondOperatorNode, Position(1, 18), Position(1, 24))
        val variableDeclarationNode =
            VariableDeclarationNode(
                identifierNode,
                expressionNode,
                keywordNode,
                colonNode,
                typeNode,
                equalsNode,
                Position(1, 1),
                Position(1, 24),
            )
        val statements = listOf(variableDeclarationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 24))
        val result = astRuleProcessor.format(programNode)
        val newTwoLiteralNode = LiteralNode(2.0, Position(1, 22), Position(1, 22))
        val newThreeLiteralNode = LiteralNode(3.0, Position(1, 26), Position(1, 26))
        val newFourLiteralNode = LiteralNode(4.0, Position(1, 30), Position(1, 30))
        val newFirstOperatorNode = OperatorNode(Position(1, 20), Position(1, 20), OperatorType.SUM)
        val newSecondOperatorNode = OperatorNode(Position(1, 24), Position(1, 24), OperatorType.SUM)
        val newThirdOperatorNode = OperatorNode(Position(1, 28), Position(1, 28), OperatorType.SUM)
        val newFirstExpressionNode = SumNode(oneLiteral, newTwoLiteralNode, newFirstOperatorNode, Position(1, 18), Position(1, 22))
        val newSecondExpressionNode =
            SumNode(newThreeLiteralNode, newFourLiteralNode, newThirdOperatorNode, Position(1, 26), Position(1, 30))
        assertIs<ProgramNode>(result)
        assertIs<VariableDeclarationNode>(result.statements[0])
        assertEquals(
            newSecondOperatorNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getStart(),
        )
        assertEquals(
            newSecondOperatorNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getOperator().getEnd(),
        )
        assertEquals(
            newFirstExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getStart(),
        )
        assertEquals(
            newFirstExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getLeft().getEnd(),
        )
        assertEquals(
            newSecondExpressionNode.getStart(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getStart(),
        )
        assertEquals(
            newSecondExpressionNode.getEnd(),
            ((result.statements[0] as VariableDeclarationNode).expression as SumNode).getRight().getEnd(),
        )
    }
}
