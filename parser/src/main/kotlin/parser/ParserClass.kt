package parser

interface ParserClass {
    fun create(baseParser: Parser): Parser
}

object ParserClassRegistry {
    private val parserClasses = mutableListOf<ParserClass>()

    fun register(parserClass: ParserClass) {
        parserClasses.add(parserClass)
    }

    fun getParserClasses(): List<ParserClass> {
        return parserClasses
    }
}

object SumParserClass : ParserClass {
    override fun create(baseParser: Parser): Parser {
        return SumParser(baseParser)
    }
}
