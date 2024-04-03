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
import formatter.Formatter
import formatter.rule.SpaceAfterEqualSignRule
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SpaceAfterEqualSignRuleTest {
    @Test
    fun testNoSpaceAndShouldNotMoveVariableDeclarationNode() {
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
        val rule = SpaceAfterEqualSignRule(false)
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
    fun testNoSpaceAndShouldMoveVariableDeclarationNode() {
        // example: let a:Number= 2+3
        //          12345678901234567
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 13), Position(1, 13))
        val firstExpressionNode = LiteralNode(2.0, Position(1, 15), Position(1, 15))
        val secondExpressionNode = LiteralNode(3.0, Position(1, 17), Position(1, 17))
        val expressionNode =
            SumNode(
                firstExpressionNode,
                secondExpressionNode,
                OperatorNode(Position(1, 16), Position(1, 16), OperatorType.SUM),
                Position(1, 15),
                Position(1, 17),
            )
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
        val rule = SpaceAfterEqualSignRule(false)
        val formatter = Formatter(listOf(rule))
        val result = formatter.format(programNode)

        val newFirstExpressionNode = LiteralNode(2.0, Position(1, 14), Position(1, 14))
        val newSecondExpressionNode = LiteralNode(3.0, Position(1, 16), Position(1, 16))
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.keywordNode == keywordNode)
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
        }
    }

    @Test
    fun testNoSpaceAndShouldMoveMoreThanOneSpaceVariableDeclarationNode() {
        // example: let a:Number=    2+3
        //          12345678901234567890
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 13), Position(1, 13))
        val firstExpressionNode = LiteralNode(2.0, Position(1, 18), Position(1, 18))
        val secondExpressionNode = LiteralNode(3.0, Position(1, 20), Position(1, 20))
        val expressionNode =
            SumNode(
                firstExpressionNode,
                secondExpressionNode,
                OperatorNode(Position(1, 19), Position(1, 19), OperatorType.SUM),
                Position(1, 18),
                Position(1, 20),
            )
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

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 20))
        val rule = SpaceAfterEqualSignRule(false)
        val formatter = Formatter(listOf(rule))
        val result = formatter.format(programNode)

        val newFirstExpressionNode = LiteralNode(2.0, Position(1, 14), Position(1, 14))
        val newSecondExpressionNode = LiteralNode(3.0, Position(1, 16), Position(1, 16))
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.keywordNode == keywordNode)
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
        }
    }

    @Test
    fun testSpaceAndShouldNotMoveVariableDeclarationNode() {
        // example: let a:Number= 10
        //          12345678901234567
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 13), Position(1, 13))
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
        val rule = SpaceAfterEqualSignRule(true)
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
    fun testSpaceAndShouldMoveOneSpaceToTheRight() {
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
                Position(1, 16),
            )
        val statements = listOf(variableDeclarationNode)
        val formatter = Formatter(listOf(SpaceAfterEqualSignRule(true)))
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 15))
        val result = formatter.format(programNode)
        val newExpressionNode = LiteralNode(10.0, Position(1, 15), Position(1, 16))

        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.keywordNode == keywordNode)
                (result.statements[0] as VariableDeclarationNode).expression?.let {
                    assertEquals(
                        it.getStart(),
                        newExpressionNode.getStart(),
                    )
                }
                (result.statements[0] as VariableDeclarationNode).expression?.let { assertEquals(it.getEnd(), newExpressionNode.getEnd()) }
            }
        }
    }

    @Test
    fun testSpaceAndShouldMoveOneSpaceToTheLeft() {
        // example: let a:Number=  2+3
        //          123456789012345678
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 13), Position(1, 13))
        val firstExpressionNode = LiteralNode(2.0, Position(1, 16), Position(1, 16))
        val secondExpressionNode = LiteralNode(3.0, Position(1, 18), Position(1, 18))
        val expressionNode =
            SumNode(
                firstExpressionNode,
                secondExpressionNode,
                OperatorNode(Position(1, 17), Position(1, 17), OperatorType.SUM),
                Position(1, 16),
                Position(1, 18),
            )
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
        val rule = SpaceAfterEqualSignRule(true)
        val formatter = Formatter(listOf(rule))
        val result = formatter.format(programNode)

        val newFirstExpressionNode = LiteralNode(2.0, Position(1, 15), Position(1, 15))
        val newSecondExpressionNode = LiteralNode(3.0, Position(1, 17), Position(1, 17))
        val newExpressionNode =
            SumNode(
                firstExpressionNode,
                secondExpressionNode,
                OperatorNode(Position(1, 15), Position(1, 15), OperatorType.SUM),
                Position(1, 14),
                Position(1, 16),
            )
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.keywordNode == keywordNode)
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
        }
    }

    @Test
    fun testSpaceAndShouldMoveSeveralSpacesToTheLeft() {
        // example: let a:Number=    2+3
        //          12345678901234567890
        // should not change node
        val keywordNode = LetNode(Position(1, 1), Position(1, 3))
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 5), Position(1, 5))
        val colonNode = ColonNode(Position(1, 6), Position(1, 6))
        val typeNode = VariableTypeNode(VariableType.NUMBER, Position(1, 7), Position(1, 12))
        val equalsNode = EqualsNode(Position(1, 13), Position(1, 13))
        val firstExpressionNode = LiteralNode(2.0, Position(1, 18), Position(1, 18))
        val secondExpressionNode = LiteralNode(3.0, Position(1, 20), Position(1, 20))
        val expressionNode =
            SumNode(
                firstExpressionNode,
                secondExpressionNode,
                OperatorNode(Position(1, 19), Position(1, 19), OperatorType.SUM),
                Position(1, 18),
                Position(1, 20),
            )
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

        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 20))
        val rule = SpaceAfterEqualSignRule(true)
        val formatter = Formatter(listOf(rule))
        val result = formatter.format(programNode)

        val newFirstExpressionNode = LiteralNode(2.0, Position(1, 15), Position(1, 15))
        val newSecondExpressionNode = LiteralNode(3.0, Position(1, 17), Position(1, 17))
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is VariableDeclarationNode) {
                assert(statementResult.colonNode == colonNode)
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.typeNode == typeNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.keywordNode == keywordNode)
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
        }
    }

    @Test
    fun testNoSpaceAndShouldMoveAssignationNode() {
        // example: a =  10
        //          1234567
        // should change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 3), Position(1, 3))
        val expressionNode = LiteralNode(10.0, Position(1, 6), Position(1, 7))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 7),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 7))
        val formatter = Formatter(listOf(SpaceAfterEqualSignRule(false)))
        val result = formatter.format(programNode)
        val newExpressionNode = LiteralNode(10.0, Position(1, 4), Position(1, 5))
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.expression.getStart() == newExpressionNode.getStart())
                assert(statementResult.expression.getEnd() == newExpressionNode.getEnd())
            }
        }
    }

    @Test
    fun testSpaceAndShouldMoveOneSpaceAssignationNode() {
        // example: a =10
        //          1234567
        // should change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 3), Position(1, 3))
        val expressionNode = LiteralNode(10.0, Position(1, 4), Position(1, 5))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 5),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 5))
        val formatter = Formatter(listOf(SpaceAfterEqualSignRule(true)))
        val result = formatter.format(programNode)
        val newExpressionNode = LiteralNode(10.0, Position(1, 5), Position(1, 6))
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.expression.getStart() == newExpressionNode.getStart())
                assert(statementResult.expression.getEnd() == newExpressionNode.getEnd())
            }
        }
    }

    @Test
    fun testSpaceAndShouldMoveSeveralSpacesAssignationNode() {
        // example: a =    10
        //          123456789
        // should change node
        val identifierNode = IdentifierNode("a", VariableType.NUMBER, Position(1, 1), Position(1, 1))
        val equalsNode = EqualsNode(Position(1, 3), Position(1, 3))
        val expressionNode = LiteralNode(10.0, Position(1, 8), Position(1, 9))
        val assignationNode =
            AssignationNode(
                identifierNode,
                expressionNode,
                equalsNode,
                Position(1, 1),
                Position(1, 9),
            )
        val statements = listOf(assignationNode)
        val programNode = ProgramNode(statements, Position(1, 1), Position(1, 9))
        val formatter = Formatter(listOf(SpaceAfterEqualSignRule(true)))
        val result = formatter.format(programNode)
        val newExpressionNode = LiteralNode(10.0, Position(1, 5), Position(1, 6))
        if (result is ProgramNode) {
            val statementResult = result.statements.get(0)
            if (statementResult is AssignationNode) {
                assert(statementResult.identifier == identifierNode)
                assert(statementResult.equalsNode == equalsNode)
                assert(statementResult.expression.getStart() == newExpressionNode.getStart())
                assert(statementResult.expression.getEnd() == newExpressionNode.getEnd())
            }
        }
    }
}
