package utils

import token.Token
import kotlin.test.assertEquals

fun assertTokenListEquals(
    expected: List<Token>,
    actual: List<Token>,
) {
    assertEquals(expected.size, actual.size)
    for (i in expected.indices) {
        assertEquals(expected[i].type, actual[i].type)
        assertEquals(expected[i].start.line, actual[i].start.line)
        assertEquals(expected[i].start.column, actual[i].start.column)
        assertEquals(expected[i].end.line, actual[i].end.line)
        assertEquals(expected[i].end.column, actual[i].end.column)
        assertEquals(expected[i].value, actual[i].value)
    }
}
