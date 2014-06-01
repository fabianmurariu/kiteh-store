name := "tevinzi2"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra" % "1.5.2",
  "joda-time" % "joda-time" % "2.3",
  "org.joda" % "joda-convert" % "1.2",
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % "2.3.3",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.3.3",
  "com.fasterxml.jackson.module" % "jackson-module-jsonSchema" % "2.3.3",
  "net._01001111" % "jlorem" % "1.3",
  "com.typesafe" % "config" % "1.2.1",
  "org.mockito" % "mockito-core" % "1.9.5",
  "org.apache.httpcomponents" % "httpclient" % "4.3.3",
  "org.mongodb" %% "casbah" % "2.7.2"
)

resolvers +=
  "Twitter" at "http://maven.twttr.com"