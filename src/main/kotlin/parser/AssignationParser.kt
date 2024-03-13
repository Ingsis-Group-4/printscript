package org.example.parser

import org.example.ast.AST
import org.example.ast.AssignationNode
import org.example.ast.ExpressionNode
import org.example.ast.IdentifierNode
import org.example.parser.status.ErrorStatus
import org.example.token.Token
import org.example.token.TokenType

class AssignationParser(private val parserSelector: Map<TokenType, Parser>): Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): Pair<AST, ParserState> {
        val token = next(tokens, currentIndex)
        val nestedParseResult = getNestedParserResult(token, tokens, currentIndex, parserSelector)
        val ast = AssignationNode(IdentifierNode(token.value), nestedParseResult.first as ExpressionNode)
        val finalState = nestedParseResult.second
        return Pair(ast, finalState)


//        when(token.type){
//            TokenType.ASSIGNATION->{return parseAssignation(tokens, currentIndex)}
//            else -> throw Exception("Invalid assignation at position ${token.start}")
//        }

    }
//    private fun parseAssignation(tokens:List<Token>, currentIndex:Int):AST{
//        val variableName = at(tokens, currentIndex).value
//        val expression = ExpressionParser().parse(tokens, currentIndex+1)
//        return AssignationNode(IdentifierNode(variableName), expression)
//    }
}