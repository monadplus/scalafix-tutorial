/*
rule = NoLiteralArguments
 */
package test

class NoLiteralArguments {
  def complete(isSuccess: Boolean): Unit = ()
  complete(true) // assert: NoLiteralArguments
}
