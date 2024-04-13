import ast.ColonNode
import ast.DeclarationNode
import ast.EqualsNode
import ast.FunctionStatementNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.OperatorNode
import ast.OperatorType
import ast.PrintLnNode
import ast.ProgramNode
import ast.ReadInputNode
import ast.SumNode
import ast.VariableType
import ast.VariableTypeNode
import org.junit.jupiter.api.Test
import position.Position
import sca.StaticCodeAnalyzer
import sca.rule.PrintLnArgumentRule
import sca.rule.ReadInputArgumentRule
import sca.rule.VariableNamingRule
import kotlin.test.assertEquals

class StaticCodeAnalyzerTest {
    private val camelCasePattern = "^[a-z]+(?:[A-Z][a-z]*)*$"
    private val snakeCasePattern = "^[a-z]+(?:_[a-z]*)*$"

    @Test
    fun testSuccessfulCamelCaseRule() {
        val sca =
            StaticCodeAnalyzer(
                listOf(VariableNamingRule(camelCasePattern)),
            )

        // let aBc: Number = 1;
        val input =
            DeclarationNode(
                identifier = IdentifierNode("a", start = Position(1, 5), end = Position(1, 5)),
                expression = LiteralNode(1, start = Position(1, 17), end = Position(1, 17)),
                LetNode(start = Position(1, 1), end = Position(1, 3)),
                ColonNode(start = Position(1, 7), end = Position(1, 15)),
                VariableTypeNode(VariableType.NUMBER, start = Position(1, 9), end = Position(1, 15)),
                EqualsNode(start = Position(1, 17), end = Position(1, 17)),
                start = Position(1, 1),
                end = Position(1, 18),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(0, result.size)
    }

    @Test
    fun testFailingCamelCaseRule() {
        val sca =
            StaticCodeAnalyzer(
                listOf(VariableNamingRule(camelCasePattern)),
            )

        // let ABc: Number = 1;
        val input =
            DeclarationNode(
                identifier = IdentifierNode("A", start = Position(1, 5), end = Position(1, 5)),
                expression = LiteralNode(1, start = Position(1, 17), end = Position(1, 17)),
                LetNode(start = Position(1, 1), end = Position(1, 3)),
                ColonNode(start = Position(1, 7), end = Position(1, 15)),
                VariableTypeNode(VariableType.NUMBER, start = Position(1, 9), end = Position(1, 15)),
                EqualsNode(start = Position(1, 17), end = Position(1, 17)),
                start = Position(1, 1),
                end = Position(1, 18),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(1, result.size)
        val failure = result[0]
        assertEquals(failure.position, Position(1, 5))
    }

    @Test
    fun testSuccessfulSnakeCaseRule() {
        val sca =
            StaticCodeAnalyzer(
                listOf(VariableNamingRule(snakeCasePattern)),
            )

        // let a_b: Number = 1;
        val input =
            DeclarationNode(
                identifier = IdentifierNode("a_b", start = Position(1, 5), end = Position(1, 7)),
                expression = LiteralNode(1, start = Position(1, 19), end = Position(1, 19)),
                LetNode(start = Position(1, 1), end = Position(1, 3)),
                ColonNode(start = Position(1, 7), end = Position(1, 17)),
                VariableTypeNode(VariableType.NUMBER, start = Position(1, 9), end = Position(1, 17)),
                EqualsNode(start = Position(1, 19), end = Position(1, 19)),
                start = Position(1, 1),
                end = Position(1, 20),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(0, result.size)
    }

    @Test
    fun testFailingSnakeCaseRule() {
        val sca =
            StaticCodeAnalyzer(
                listOf(VariableNamingRule(snakeCasePattern)),
            )

        // let aB: Number = 1;
        val input =
            DeclarationNode(
                identifier = IdentifierNode("aB", start = Position(1, 5), end = Position(1, 6)),
                expression = LiteralNode(1, start = Position(1, 18), end = Position(1, 18)),
                LetNode(start = Position(1, 1), end = Position(1, 3)),
                ColonNode(start = Position(1, 7), end = Position(1, 17)),
                VariableTypeNode(VariableType.NUMBER, start = Position(1, 9), end = Position(1, 17)),
                EqualsNode(start = Position(1, 18), end = Position(1, 18)),
                start = Position(1, 1),
                end = Position(1, 19),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(1, result.size)
        val failure = result[0]
        assertEquals(failure.position, Position(1, 5))
    }

    @Test
    fun testSuccessWithLiteralPrintLnArgumentRule() {
        val sca =
            StaticCodeAnalyzer(
                listOf(PrintLnArgumentRule()),
            )

        // println(1);
        val input =
            PrintLnNode(
                expression = LiteralNode(1, start = Position(1, 9), end = Position(1, 9)),
                start = Position(1, 1),
                end = Position(1, 10),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(0, result.size)
    }

    @Test
    fun testSuccessWithIdentifier() {
        val sca =
            StaticCodeAnalyzer(
                listOf(PrintLnArgumentRule()),
            )

        // println(a);
        val input =
            PrintLnNode(
                expression = IdentifierNode("a", start = Position(1, 9), end = Position(1, 9)),
                start = Position(1, 1),
                end = Position(1, 10),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(0, result.size)
    }

    @Test
    fun testFailingPrintLnArgumentRule() {
        val sca =
            StaticCodeAnalyzer(
                listOf(PrintLnArgumentRule()),
            )

        // println(1+1);
        val input =
            FunctionStatementNode(
                Position(1, 1),
                Position(1, 12),
                PrintLnNode(
                    expression =
                        SumNode(
                            left = LiteralNode(1, start = Position(1, 9), end = Position(1, 9)),
                            right = LiteralNode(1, start = Position(1, 11), end = Position(1, 11)),
                            OperatorNode(Position(1, 10), Position(1, 10), OperatorType.SUM),
                            start = Position(1, 9),
                            end = Position(1, 11),
                        ),
                    start = Position(1, 1),
                    end = Position(1, 12),
                ),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(1, result.size)
        val failure = result[0]
        assertEquals(failure.position, Position(1, 9))
    }

    @Test
    fun testFailingProgramWithMultipleRules() {
        val sca =
            StaticCodeAnalyzer(
                listOf(
                    VariableNamingRule(camelCasePattern),
                    PrintLnArgumentRule(),
                ),
            )

        // let B: Number = 1;
        // print(1+1);
        val input =
            ProgramNode(
                statements =
                    listOf(
                        DeclarationNode(
                            identifier = IdentifierNode("B", start = Position(1, 5), end = Position(1, 5)),
                            expression = LiteralNode(1, start = Position(1, 17), end = Position(1, 17)),
                            LetNode(start = Position(1, 1), end = Position(1, 3)),
                            ColonNode(start = Position(1, 7), end = Position(1, 15)),
                            VariableTypeNode(VariableType.NUMBER, start = Position(1, 9), end = Position(1, 15)),
                            EqualsNode(start = Position(1, 17), end = Position(1, 17)),
                            start = Position(1, 1),
                            end = Position(1, 19),
                        ),
                        FunctionStatementNode(
                            Position(2, 1),
                            Position(2, 2),
                            PrintLnNode(
                                expression =
                                    SumNode(
                                        left = LiteralNode(1, start = Position(2, 7), end = Position(2, 7)),
                                        right = LiteralNode(1, start = Position(2, 9), end = Position(2, 9)),
                                        OperatorNode(Position(2, 8), Position(2, 8), OperatorType.SUM),
                                        start = Position(2, 7),
                                        end = Position(2, 9),
                                    ),
                                start = Position(2, 1),
                                end = Position(2, 8),
                            ),
                        ),
                    ),
                start = Position(1, 1),
                end = Position(2, 8),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(2, result.size)
        val failure1 = result[0]
        val failure2 = result[1]

        assertEquals(Position(1, 5), failure1.position)

        assertEquals(Position(2, 7), failure2.position)
    }

    @Test
    fun testOneSuccessOneFailingProgramWithMultipleRules() {
        val sca =
            StaticCodeAnalyzer(
                listOf(
                    VariableNamingRule(camelCasePattern),
                    PrintLnArgumentRule(),
                ),
            )

        // let a: Number = 1;
        // print(1+1);
        val input =
            ProgramNode(
                statements =
                    listOf(
                        DeclarationNode(
                            identifier = IdentifierNode("a", start = Position(1, 5), end = Position(1, 5)),
                            expression = LiteralNode(1, start = Position(1, 17), end = Position(1, 17)),
                            LetNode(start = Position(1, 1), end = Position(1, 3)),
                            ColonNode(start = Position(1, 7), end = Position(1, 15)),
                            VariableTypeNode(VariableType.NUMBER, start = Position(1, 9), end = Position(1, 15)),
                            EqualsNode(start = Position(1, 17), end = Position(1, 17)),
                            start = Position(1, 1),
                            end = Position(1, 18),
                        ),
                        FunctionStatementNode(
                            Position(2, 1),
                            Position(2, 2),
                            PrintLnNode(
                                expression =
                                    SumNode(
                                        left = LiteralNode(1, start = Position(2, 7), end = Position(2, 7)),
                                        right = LiteralNode(1, start = Position(2, 9), end = Position(2, 9)),
                                        OperatorNode(Position(2, 8), Position(2, 8), OperatorType.SUM),
                                        start = Position(2, 7),
                                        end = Position(2, 9),
                                    ),
                                start = Position(2, 1),
                                end = Position(2, 8),
                            ),
                        ),
                    ),
                start = Position(1, 1),
                end = Position(2, 8),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(1, result.size)
        val failure = result[0]

        assertEquals(Position(2, 7), failure.position)
    }

    @Test
    fun failingReadInputFailingTest() {
        val sca =
            StaticCodeAnalyzer(
                listOf(ReadInputArgumentRule()),
            )

        // readInput(1);
        val input =
            FunctionStatementNode(
                Position(1, 1),
                Position(1, 12),
                ReadInputNode(
                    expression =
                        ReadInputNode(
                            start = Position(1, 10),
                            end = Position(1, 12),
                            expression = LiteralNode(1, start = Position(1, 10), end = Position(1, 10)),
                        ),
                    start = Position(1, 1),
                    end = Position(1, 12),
                ),
            )

        val result = sca.analyze(input).ruleFailures

        assertEquals(1, result.size)
        val failure = result[0]
        assertEquals(Position(1, 10), failure.position)
    }
}
