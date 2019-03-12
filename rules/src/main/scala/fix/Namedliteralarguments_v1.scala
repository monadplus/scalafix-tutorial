package fix

import scalafix.v1._
import scala.meta._

class Namedliteralarguments_v1
    extends SemanticRule("Namedliteralarguments_v1") {

  override def fix(implicit doc: SemanticDocument): Patch = {
//    println(s"Tree.syntax: " + doc.tree.syntax)
//    println(s"Tree.structure: " + doc.tree.structure)
//    println(s"Tree.structureLabeled: " + doc.tree.structureLabeled)
    doc.tree
      .collect {
        case Term.Apply(fun, args) =>
          args.zipWithIndex.collect {
            case (t @ Lit.Boolean(_), i) =>
              fun.symbol.info match {
                case Some(info) =>
                  info.signature match {
                    case method: MethodSignature
                        if method.parameterLists.nonEmpty =>
                      val parameter = method.parameterLists.head(i)
                      val parameterName = parameter.displayName
                      Patch.addLeft(t, s"$parameterName = ")
                    case _ =>
                      Patch.empty
                  }
                case None =>
                  Patch.empty
              }
          }
      }
      .flatten
      .asPatch
  }

}