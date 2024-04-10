package formatter

import formatter.configurer.LineBreakBeforePrintLnConfigurer
import formatter.configurer.SpaceBetweenColonConfigurer
import formatter.configurer.SpaceBetweenEqualSignConfigurer

private const val configPath = "path"

data class FormattingRule(
    val whiteSpaceBeforeAndAfterOperation: Boolean = true,
    val lineBreakAfterSemicolon: Boolean = true,
    val whiteSpaceBetweenTokens: Boolean = true,
    val spaceBetweenColon: Boolean = SpaceBetweenColonConfigurer(configPath).getRule(),
    val spaceBetweenEqualSign: Boolean = SpaceBetweenEqualSignConfigurer(configPath).getRule(),
    val lineBreakBeforePrintLn: Number = LineBreakBeforePrintLnConfigurer(configPath).getRule(),
)


