package formatter.rule

import formatter.configurer.IfBlockIndentConfigurer
import formatter.configurer.LineBreakBeforePrintLnConfigurer
import formatter.configurer.SpaceBetweenColonConfigurer
import formatter.configurer.SpaceBetweenEqualSignConfigurer

data class FormattingRule(
    val configString: String,
    val hasWhiteSpaceBeforeAndAfterOperation: Boolean = true,
    val hasLineBreakAfterSemicolon: Boolean = true,
    val hasWhiteSpaceBetweenTokens: Boolean = true,
    val hasSpaceBetweenColon: Boolean = SpaceBetweenColonConfigurer(configString).getRule(),
    val hasSpaceBetweenEqualSign: Boolean = SpaceBetweenEqualSignConfigurer(configString).getRule(),
    val lineBreakBeforePrintLn: Int = LineBreakBeforePrintLnConfigurer(configString).getRule(),
    val ifBlockIndent: Int = IfBlockIndentConfigurer(configString).getRule(),
)
