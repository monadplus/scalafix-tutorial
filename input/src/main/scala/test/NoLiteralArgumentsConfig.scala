/*
rule = NoLiteralArguments
NoLiteralArguments.disabledLiterals = [
  Int
  Boolean
]
 */
package test

class NoLiteralArgumentsConfig {
  def complete(message: String): Unit = ()
  def complete(count: Int): Unit = ()
  def complete(isSuccess: Boolean): Unit = ()
  complete("done") // ok, no error message
  complete(42) // assert: NoLiteralArguments
  complete(true) // assert: NoLiteralArguments
}
