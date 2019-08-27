addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3")
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")

libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0-RC1"
libraryDependencies += "com.lihaoyi" %% "utest" % "0.7.1" % Test
libraryDependencies += "com.lihaoyi" %% "fastparse" % "2.1.3"

testFrameworks += new TestFramework("utest.runner.Framework")

lazy val root = (project in file("."))
  .settings(
    organization := "fp",
    version := "1.0",
    name := "bowling",
    scalaVersion := "2.13.0"
  )
