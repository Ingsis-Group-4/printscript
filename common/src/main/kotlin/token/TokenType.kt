package token

enum class TokenType() {
    // keywords
    LET,
    CONST,
    PRINTLN,
    READINPUT,
    READENV,
    IF,
    ELSE,
    NUMBERTYPE,
    STRINGTYPE,
    BOOLEANTYPE,

    // literal
    STRING,
    NUMBER,
    BOOLEAN,

    // operators
    COLON,
    ASSIGNATION,
    CLOSEPARENTHESIS,
    DIVISION,
    MULTIPLICATION,
    OPENPARENTHESIS,
    SEMICOLON,
    SUBTRACTION,
    SUM,
    OPENCURLY,
    CLOSECURLY,

    // identifiers
    IDENTIFIER,

    // unknown
    UNKNOWN,
}
