package org.example.cli.functions

interface CLIFunction {
    fun run(args: Map<String, String>)
}
