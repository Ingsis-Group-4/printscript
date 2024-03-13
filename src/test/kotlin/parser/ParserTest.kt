package parser

import org.example.factory.AssignationParserFactory
import org.example.factory.PrintScriptParser
import org.example.parser.ProgramParser
import org.example.parser.status.SuccessStatus
import org.example.position.Position
import org.example.token.Token
import org.example.token.TokenType
import kotlin.test.Test

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
    }

    @Test
    fun testProgramParser(){}

    @Test
    fun testVariableAssignationParser(){}
}