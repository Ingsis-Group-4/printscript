package org.example.interpreter

interface Value {}
class NumberValue(val value: Double) : Value
class StringValue(val value: String) : Value
class NullValue : Value
class VoidValue : Value
