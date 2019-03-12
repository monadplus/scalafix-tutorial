package fix

import scalafix.v1._
import scala.meta._

case class LiteralArgument(literal: Lit) extends Diagnostic {
  override def position: Position = literal.pos
  override def message: String =
    s"Used named arguments for literals such as 'parameterName = $literal'"
}

class NoLiteralArguments extends SyntacticRule("NoLiteralArguments") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case Term.Apply(_, args) =>
        args.collect {
          case t @ Lit.Boolean(_) =>
            Patch.lint(LiteralArgument(t))
        }
    }.flatten.asPatch
}
