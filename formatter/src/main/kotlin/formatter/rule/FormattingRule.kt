package formatter.rule

import formatter.configurer.IfBlockIndentConfigurer
import formatter.configurer.LineBreakBeforePrintLnConfigurer
import formatter.configurer.SpaceBetweenColonConfigurer
import formatter.configurer.SpaceBetweenEqualSignConfigurer

data class FormattingRule(
    val configPath: String,
    val hasWhiteSpaceBeforeAndAfterOperation: Boolean = true,
    val hasLineBreakAfterSemicolon: Boolean = true,
    val hasWhiteSpaceBetweenTokens: Boolean = true,
    val hasSpaceBetweenColon: Boolean = SpaceBetweenColonConfigurer(configPath).getRule(),
    val hasSpaceBetweenEqualSign: Boolean = SpaceBetweenEqualSignConfigurer(configPath).getRule(),
    val lineBreakBeforePrintLn: Int = LineBreakBeforePrintLnConfigurer(configPath).getRule(),
    val ifBlockIndent: Int = IfBlockIndentConfigurer(configPath).getRule(),
)
