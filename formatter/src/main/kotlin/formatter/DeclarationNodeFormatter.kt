package formatter

import ast.AST
import ast.KeywordNode
import kotlin.reflect.KClass

class DeclarationNodeFormatter(private val keywordMap : Map<KClass<out KeywordNode>, String>):Formatter {
    override fun format(node: AST, rule: FormattingRule): String {
        val declarationNode = node as ast.DeclarationNode
        var resultString = ""
        resultString += "let"
        resultString = addWhiteSpace(resultString)
        resultString += declarationNode.identifier.variableName
        if (rule.spaceBetweenColon){
            resultString = addWhiteSpace(resultString)
            resultString += ":"
            resultString = addWhiteSpace(resultString)
        }
        else{
            resultString += ":"
        }
        resultString += declarationNode.typeNode.variableType.name
        if (declarationNode.equalsNode!=null){
            if (rule.spaceBetweenEqualSign){
                resultString = addWhiteSpace(resultString)
                resultString += "="
                resultString = addWhiteSpace(resultString)
            }
            else{
                resultString += "="
            }
            resultString += getExpression(declarationNode.expression!!, rule)
        }
        return resultString
    }

}