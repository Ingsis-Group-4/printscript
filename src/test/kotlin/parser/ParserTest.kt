package parser

import org.example.ast.AssignationNode
import org.example.ast.ProgramNode
import org.example.ast.StatementNode
import org.example.parser.factory.PrintScriptParser
import org.example.parser.status.SuccessStatus
import org.example.position.Position
import org.example.token.Token
import org.example.token.TokenType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ParserTest {
    @Test
    fun testAssignation(){
        val parser = PrintScriptParser.create()
        val input = listOf(
            Token(
                TokenType.IDENTIFIER,
                Position(1, 1),
                Position(1,1),
                "a"
            ),
            Token(
                TokenType.ASSIGNATION,
                Position(1, 2),
                Position(1,2),
                "="
            ),
            Token(
                TokenType.NUMBER,
                Position(1, 3),
                Position(1,3),
                "1"
            ),
            Token(
                TokenType.SEMICOLON,
                Position(1, 4),
                Position(1,4),
                ";"
            )
        )

        val result = parser.parse(input, 0)
        assert(result.second.status is SuccessStatus)
        assertEquals(4, result.second.lastValidatedIndex)
        assertIs<ProgramNode>(result.first)

        val statements = (result.first as ProgramNode).statements

        assertEquals(statements.size, 1)
        assertIs<AssignationNode>(statements.get(0))
    }

    @Test
    fun testProgramParser(){}

    @Test
    fun testVariableAssignationParser(){}
}