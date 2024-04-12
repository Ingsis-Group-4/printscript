package interpreter

sealed interface Value {
    override fun toString(): String
}

class NumberValue(val value: Double) : Value {
    override fun toString(): String {
        return value.toString()
    }
}

class StringValue(val value: String) : Value {
    override fun toString(): String {
        return value
    }
}

class BooleanValue(val value: Boolean) : Value {
    override fun toString(): String {
        return value.toString()
    }
}

object NullValue : Value {
    override fun toString(): String {
        return "null"
    }
}
