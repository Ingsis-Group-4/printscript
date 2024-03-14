package org.example.parser

import org.example.ast.AST
import org.example.ast.ProgramNode
import org.example.ast.StatementNode
import org.example.parser.result.ErrorResult
import org.example.parser.result.ParserResult
import org.example.parser.result.SuccessResult
import org.example.parser.status.ErrorStatus
import org.example.parser.status.SuccessStatus
import org.example.token.Token
import org.example.token.TokenType


class ProgramParser(private val parserSelector: Map<TokenType, Parser>) : Parser {
    override fun parse(tokens: List<Token>, currentIndex: Int): ParserResult {
        val initialStatus = SuccessStatus()
        val initialState = ParserState(initialStatus, currentIndex)
        val (parsedStatements, finalState) = parseStatements(tokens, initialState)
        val ast = ProgramNode(parsedStatements)
        return Pair(ast, finalState)
    }



    private fun parseStatements(tokens: List<Token>, currentState: ParserState): ParserResult {
        var currentIndex = currentState.lastValidatedIndex
        val statements = emptyList<StatementNode>().toMutableList()

        while (!isEndOfProgram(tokens, currentIndex)) {
            val (statement, newState) = parseNextStatement(tokens, currentIndex)
            if (newState.status is ErrorStatus) {
                return Pair(statements, newState)
            }
            statements += statement as StatementNode
            currentIndex = newState.lastValidatedIndex + 1
        }
        val successStatus = SuccessStatus()
        return Pair(statements, ParserState(successStatus, currentIndex))
    }

    private fun parseNextStatement(tokens: List<Token>, currentIndex: Int): Pair<AST?, ParserState> {
        val token = at(tokens, currentIndex)
        return getNestedParserResult(token, tokens, currentIndex, parserSelector)
    }

    fun parser2(tokens: List<Token>, currentIndex: Int): ParserResult{
        val statementTokens = divideIntoStatements(tokens)
        return parseStatements2(statementTokens, currentIndex)
    }

    private fun divideIntoStatements(tokens: List<Token>): List<List<Token>> {
        val statements = mutableListOf<List<Token>>()
        var currentStatement = mutableListOf<Token>()
        for (token in tokens) {
            if (token.type == TokenType.SEMICOLON) {
                statements.add(currentStatement)
                currentStatement = mutableListOf()
            } else {
                currentStatement.add(token)
            }
        }
        return statements
    }

    private fun parseNextStatement2(tokens: List<Token>, currentIndex: Int): ParserResult{}

    private fun parseStatements2(statementTokens: List<List<Token>>, currentIndex: Int): ParserResult{
        val asts = mutableListOf<StatementNode>()

        var lastIndexedValue = currentIndex;

        for (statement in statementTokens) {
            when (val statementResult = parseNextStatement2(statement, currentIndex)) {
                is SuccessResult -> {
                    when (statementResult.value) {
                        is StatementNode -> {
                            lastIndexedValue = statementResult.lastValidatedIndex
                            asts.add(statementResult.value)
                        }
                        else -> return ErrorResult("Error parsing statement", statementResult.lastValidatedIndex)
                    }
                }

                is ErrorResult -> return statementResult
            }
        }

        return SuccessResult(ProgramNode(asts), lastIndexedValue)
    }
}






//class ProgramParser(private val parserSelector: Map<TokenType, Parser>): Parser {
//    override fun parse(tokens: List<Token>, currentIndex: Int): Pair<AST, ParserState> {
//        val token = at(tokens, currentIndex)
//        //Puede ser mutable la lista no?
//        val statements = mutableListOf<StatementNode>()
//        val firstStatement = parseNextStatement(tokens, currentIndex)
//        val firstStatementAST = firstStatement.first as StatementNode
//        val firstStatementStatus = firstStatement.second
//        while (!isEndOfProgram(tokens, currentIndex)) {
//            val (statement, newState) = parseStatement(tokens, currentIndex)
//            statements.add(statement as StatementNode)
//            currentIndex = newState.currentIndex
//        }
////        return parserSelector[token.type]?.parse(tokens, currentIndex) ?:
////        throw Exception("Invalid token at position ${token.start}")
////        return when(token.type) {
////            TokenType.LET -> VariableDeclarationParser().parse(tokens, currentIndex)
////            TokenType.IDENTIFIER -> AssignationParser().parse(tokens, currentIndex)
//////            TokenType.PRINTLN -> {}
////            else -> throw Exception("Invalid program at position ${token.start}")
////        }
//
//
//    }
//
//    private fun parseNextStatement(tokens: List<Token>, currentIndex: Int): Pair<AST, ParserState> {
//        val token = at(tokens, currentIndex)
//        return parserSelector[token.type]?.parse(tokens, currentIndex) ?:
//        throw Exception("Invalid token at position ${token.start}")
//    }
//
//}