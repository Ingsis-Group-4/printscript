import ast.IdentifierNode
import ast.LiteralNode
import ast.VariableDeclarationNode
import org.example.provider.StaticCodeAnalyzerConfigurer
import org.example.provider.PrintLnArgumentNonExpressionRuleConfigurer
import org.example.provider.VariableNamingRuleConfigurer
import org.example.rule.RuleFailure
import position.Position
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class StaticCodeAnalyzerConfigurerTest {
    @Test
    fun testSCAProviderWithCamelCase() {
        val provider = StaticCodeAnalyzerConfigurer(
            mapOf(
                "variableNamingRule" to VariableNamingRuleConfigurer(),
                "printLnArgumentNonExpressionRule" to PrintLnArgumentNonExpressionRuleConfigurer()
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