lazy val commonSettings = Seq(
  organization := "de.htwg.se",
  version := "0.14.0",
  scalaVersion := "2.12.7",
  scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8"),
)

lazy val root = (project in file("."))
  .settings(
    name := "juraePuzzSA"
  )
  .aggregate(
    game,
    databaseMS,
    h2database
  )

lazy val game = (project in file("game"))
  .settings(
    commonSettings,
    mainClass in Compile := Some("main"),
    libraryDependencies += "junit" % "junit" % "4.8" % "test",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test",

    libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3",

    libraryDependencies += "com.google.inject" % "guice" % "4.1.0",

    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0",

    libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",

    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6",

    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",

    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",

    libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.3",

    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.8",

    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22",
  ).enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(dockerBaseImage := "zgwmike/akka-sbt")
  .settings(dockerExposedPorts := Seq(9090))

lazy val databaseMS = (project in file("databaseMS"))
  .settings(
    commonSettings,
    mainClass in Compile := Some("main"),
    libraryDependencies += "com.github.etaty" %% "rediscala" % "1.8.0",

    libraryDependencies ++= Seq(
      "net.debasishg" %% "redisreact" % "0.9"
    ),

    libraryDependencies += "junit" % "junit" % "4.8" % "test",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test",

    libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3",

    libraryDependencies += "com.google.inject" % "guice" % "4.1.0",

    libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0",

    libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6",

    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6",

    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",

    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0",

    libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.3",

    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.8",

    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22",

    libraryDependencies ++= Seq("com.github.etaty" %% "rediscala" % "1.8.0", "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8"),

    libraryDependencies += "com.h2database" % "h2" % "1.4.196"
    
  ).enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)

lazy val h2database = (project in file("h2database"))
  .settings(
    commonSettings,
    mainClass in Compile := Some("main"),
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.2.0",
      "org.slf4j" % "slf4j-nop" % "1.7.10",
      "com.h2database" % "h2" % "1.4.187"
    ),
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6",
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.22",
    libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0",
    libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"
  ).enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)

