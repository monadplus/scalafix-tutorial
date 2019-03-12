package fix

import metaconfig.Configured
import scalafix.v1._

import scala.meta._

case class LiteralArgument(literal: Lit) extends Diagnostic {
  override def position: Position = literal.pos
  override def message: String =
    s"Used named arguments for literals such as 'parameterName = $literal'"
}

// Hold the `.scalafix.conf` configuration
case class NoLiteralArgumentsConfig(/*matches .scalafix.conf*/disabledLiterals: List[String] = List("Boolean")) {
  def isDisabled(literal: Lit): Boolean = {
    val kind = literal.productPrefix.stripPrefix("Lit.")
    disabledLiterals.contains(kind)
  }
}

object NoLiteralArgumentsConfig {
  def default = NoLiteralArgumentsConfig()
  implicit val surface =
    metaconfig.generic.deriveSurface[NoLiteralArgumentsConfig]
  implicit val decoder =
    metaconfig.generic.deriveDecoder(default)
}

class NoLiteralArguments(config: NoLiteralArgumentsConfig)
  extends SyntacticRule("NoLiteralArguments") {

  def this() = this(NoLiteralArgumentsConfig.default)

  // Called once after the rule is loaded
  override def withConfiguration(config: Configuration): Configured[Rule] =
    config.conf
      .getOrElse(/*matches .scalafix.conf*/"NoLiteralArguments")(this.config/*Implicit decoder from NoLiteralArgumentsConfig*/)
    .map { newConfig => new NoLiteralArguments(newConfig) }

  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case Term.Apply(_, args) =>
        args.collect {
          case t: Lit if config.isDisabled(t)=>
            Patch.lint(LiteralArgument(t))
        }
    }.flatten.asPatch
}
