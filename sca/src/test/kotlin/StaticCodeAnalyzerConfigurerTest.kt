import ast.ColonNode
import ast.EqualsNode
import ast.IdentifierNode
import ast.LetNode
import ast.LiteralNode
import ast.VariableDeclarationNode
import ast.VariableType
import ast.VariableTypeNode
import position.Position
import sca.StaticCodeAnalyzer
import sca.factory.DefaultSCARuleFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class StaticCodeAnalyzerConfigurerTest {
    @Test
    fun testSCAProviderWithCamelCase() {
        val rules = DefaultSCARuleFactory().getRules("src/test/resources/sca.config.json")
        val sca = StaticCodeAnalyzer(rules)

        val result =
            sca.analyze(
                VariableDeclarationNode(
                    identifier = IdentifierNode("B", start = Position(1, 5), end = Position(1, 5)),
                    expression = LiteralNode(1, start = Position(1, 17), end = Position(1, 17)),
                    LetNode(start = Position(1, 1), end = Position(1, 3)),
                    ColonNode(start = Position(1, 7), end = Position(1, 15)),
                    VariableTypeNode(VariableType.NUMBER, start = Position(1, 9), end = Position(1, 15)),
                    EqualsNode(start = Position(1, 17), end = Position(1, 17)),
                    start = Position(1, 1),
                    end = Position(1, 18),
                ),
            )

        assertEquals(1, result.ruleFailures.size)
        assertEquals("Variable 'B' does not follow naming rule", (result.ruleFailures[0].message))
    }
}
