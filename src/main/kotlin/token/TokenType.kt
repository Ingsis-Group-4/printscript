package org.example.token

enum class TokenType() {
    //keywords
    LET,
    PRINTLN,
    NUMBERTYPE,
    STRINGTYPE,

    //literal
    STRING,
    NUMBER,

    //operators
    COLON,
    ASSIGNATION,
    CLOSEPARENTHESIS,
    DIVISION,
    MULTIPLICATION,
    OPENPARENTHESIS,
    SEMICOLON,
    SUBTRACTION,
    SUM,

    //identifiers
    IDENTIFIER

}