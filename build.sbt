name          := "juraePuzzSA"
organization  := "de.htwg.se"
version       := "0.14.0"
scalaVersion  := "2.12.7"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")


libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3"

libraryDependencies += "com.google.inject" % "guice" % "4.1.0"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.0"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.3.0"

libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.3"

libraryDependencies += "com.h2database" % "h2" % "1.4.192"

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.8"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22"

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8"

/*

resolvers += Resolver.jcenterRepo

libraryDependencies ++= {
  val scalaTestV       = "3.0.1"
  val scalaMockV       = "3.2.2"
  Seq(
    "org.scalatest" %% "scalatest"                   % scalaTestV       % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockV       % "test"
  )
}

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11+"

libraryDependencies += "com.google.inject" % "guice" % "3.0"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.15"

*/