lazy val commonSettings = Seq(
  version := "1.0.0-SNAPSHOT",
  organization := "jp.ed.nnn",  //開発組織
  scalaVersion := "2.13.10"  //使用するScalaのバージョン
)

val osName: SettingKey[String] = SettingKey[String]("osName")

osName := (System.getProperty("os.name") match {
  case name if name.startsWith("Linux") => "linux"
  case name if name.startsWith("Mac") => "mac"
  case name if name.startsWith("Windows") => "win"
  case _ => throw  new Exception("Unknown platform")
})

libraryDependencies += "org.openjfx" % "javafx-base" % "20.0.1" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-controls" % "20.0.1" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-fxml" % "20.0.1" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-graphics" % "20.0.1" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-web" % "20.0.1" classifier osName.value
libraryDependencies += "org.openjfx" % "javafx-media" % "20.0.1" classifier osName.value