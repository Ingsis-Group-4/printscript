package parser.utils

/**
 * This function attempts to convert a string to a number.
 * It first tries to convert the string to an integer (Int). If this fails, it tries to convert it to a double (Double).
 * If both conversions fail, it throws an IllegalArgumentException.
 *
 * @param value The string to be converted to a number.
 * @return The converted number, which can be an Int or a Double.
 * @throws IllegalArgumentException if the string cannot be converted to a number.
 */
fun convertStringToNumber(value: String): Number {
    return try {
        value.toInt()
    } catch (e: NumberFormatException) {
        try {
            value.toDouble()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("$value cannot be converted to a number")
        }
    }
}
