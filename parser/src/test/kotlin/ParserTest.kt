package parser

import org.example.parser.factory.ProgramParserFactory
import org.example.parser.result.SuccessResult
import position.Position
import token.Token
import token.TokenType

import kotlin.test.Test

class ParserTest {
    @Test
    fun testAssignation() {
        val parser = ProgramParserFactory.create()
        val input = listOf(
            Token(
                TokenType.IDENTIFIER,
                Position(1, 1),
                Position(1, 1),
                "a"
            ),
            Token(
                TokenType.ASSIGNATION,
                Position(1, 2),
                Position(1, 2),
                "="
            ),
            Token(
                TokenType.NUMBER,
                Position(1, 3),
                Position(1, 3),
                "1"
            ),
            Token(
                TokenType.SEMICOLON,
                Position(1, 4),
                Position(1, 4),
                ";"
            )
        )

        val result = parser.parse(input, 0)
        assert(result is SuccessResult)
    }

    @Test
    fun testProgramParser() {
    }

    @Test
    fun testVariableAssignationParser() {
    }
}
