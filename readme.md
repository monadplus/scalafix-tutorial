# Scalafix rules for named-literal-arguments

To develop rule:
```
sbt ~tests/test
# edit rules/src/main/scala/fix/Namedliteralarguments_v1.scala
```
Usage of implemented rules:
* From local machine: 

```sbtshell
scalafix file:/Users/arnau/IdeaProjects/named-literal-arguments/scalafix/rules/src/main/scala/fix/NoLiteralArguments.scala
```

* From github:

```sbtshell
scalafix github:monadplus/scalafix-tutorial/NoLiteralArguments
```

* From mavel central:
https://scalacenter.github.io/scalafix/docs/developers/tutorial.html#publish-the-rule-to-maven-central