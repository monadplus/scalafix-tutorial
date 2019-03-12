/*
rule = NoLiteralArguments
 */
package test

class NoLiteralArguments {
  def complete(isSuccess: Boolean): Unit = ()
  complete(true) // assert: NoLiteralArguments
  complete(false) /* assert: NoLiteralArguments
           ^^^^^
  Used named arguments for literals such as 'parameterName = false'
  */
}
