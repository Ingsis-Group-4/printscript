import ast.IdentifierNode
import ast.LiteralNode
import ast.VariableDeclarationNode
import org.example.provider.StaticCodeAnalyzerProvider
import org.example.provider.PrintLnArgumentNonExpressionRuleProvider
import org.example.provider.VariableNamingRuleProvider
import org.example.rule.RuleFailure
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StaticCodeAnalyzerProviderTest {
    @Test
    fun testSCAProviderWithCamelCase() {
        val provider = StaticCodeAnalyzerProvider(
            mapOf(
                "variableNamingRule" to VariableNamingRuleProvider(),
                "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleProvider()
            )
        )
        val sca = provider.createStaticCodeAnalyzer("src/test/resources/sca.config.json")

        val result = sca.analyze(
            VariableDeclarationNode(
                identifier = IdentifierNode("B", start = Position(1, 5), end = Position(1, 5)),
                expression = LiteralNode(1, start = Position(1, 17), end = Position(1, 17)),
                start = Position(1, 1),
                end = Position(1, 18)
            )
        )

        assertEquals(1, result.ruleResults.size)
        assertIs<RuleFailure>(result.ruleResults[0])
        assertEquals("Variable 'B' does not follow naming rule", (result.ruleResults[0] as RuleFailure).message)
    }
}