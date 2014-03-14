import sbt._
import sbt.ExclusionRule
import sbt.Keys._
import net.virtualvoid.sbt.graph.{Plugin => Dep}
import scala._

object Util {
  implicit def dependencyFilterer(deps: Seq[ModuleID]) = new Object {
    def excluding(group: String, artifactId: String) =
      deps.map(_.exclude(group, artifactId))
    def excluding(rules: ExclusionRule*) =
      deps.map(_.excludeAll(rules :_*))
  }
}

object Compiler {
  val defaultSettings = Seq(
    scalacOptions in Compile ++= Seq("-target:jvm-1.6", "-deprecation", "-unchecked", "-feature",
      "-language:postfixOps", "-language:implicitConversions"),
    javacOptions in Compile ++= Seq("-source", "1.6", "-target", "1.6"),
    resolvers ++= Seq(
      Resolver.url("octo47 repo", url("http://octo47.github.com/repo/"))({
        val patt = Resolver.mavenStylePatterns.artifactPatterns
        new Patterns(patt, patt, true)
      })
    ),
    ivyXML :=
      <dependencies>
        <exclude org="log4j"/>
        <exclude org="org.slf4j" name="slf4j-log4j12"/>
      </dependencies>
  )
}

object Tests {

  val defaultSettings = Seq(
    parallelExecution in Test := false,
    publishTo    := Some(Resolver.file("octo47.github.com", file(Path.userHome + "/Projects/github/octo47.github.com/repo"))),
    publishMavenStyle := false
  )
}

object Version {
  val Scala = "2.10.1"
  val ScalaTest = "1.9.1"
  val EasyMock = "3.2"
  val CGLIB = "2.2.2"
  val Slf4j = "1.7.5"
  val JUnit = "4.10"
  val Logback = "1.0.7"

  val slf4jDependencies: Seq[ModuleID] = Seq(
    "org.slf4j" % "jcl-over-slf4j" % Version.Slf4j,
    "org.slf4j" % "log4j-over-slf4j" % Version.Slf4j,
    "ch.qos.logback" % "logback-classic" % Version.Logback
  )

  val commonExclusions = Seq(
    ExclusionRule(name = "jline")
  )

  val slf4jExclusions = Seq(
    ExclusionRule(name = "slf4j-log4j12"),
    ExclusionRule(name = "slf4j-simple")
  )
}

object HBase {

  import Util._

  val Hadoop = "2.2.0-cdh5.0.0-beta-2"
  val HBase = "0.96.1.1-cdh5.0.0-beta-2"

  val settings = Seq(
    resolvers ++= Seq(
      "cloudera" at "http://repository.cloudera.com/artifactory/cloudera-repos"
    ),
    libraryDependencies ++= Seq(
      "org.apache.hadoop" % "hadoop-common" % Hadoop,
      "org.apache.hbase" % "hbase-client" % HBase,
      "org.apache.hbase" % "hbase-common" % HBase
    ).excluding(
      ExclusionRule(name = "commons-daemon"),
      ExclusionRule(name = "commons-cli"),
      ExclusionRule(name = "commons-logging"),
      ExclusionRule(name = "jsp-api"),
      ExclusionRule(name = "servlet-api"),
      ExclusionRule(name = "kfs"),
      ExclusionRule(name = "mockito-all"),
      ExclusionRule(organization = "org.jruby"),
      ExclusionRule(organization = "tomcat"),
      ExclusionRule(organization = "org.apache.thrift"),
      ExclusionRule(organization = "com.jcraft"),
      ExclusionRule(organization = "org.mortbay.jetty"),
      ExclusionRule(organization = "com.sun.jersey")
    ).excluding(Version.slf4jExclusions :_*)
     .excluding(Version.commonExclusions :_*)
  )
}

object FakeHBaseBuild extends Build {

  import Util._

  lazy val defaultSettings =
    Defaults.defaultSettings ++
      Compiler.defaultSettings ++
      Tests.defaultSettings ++
      Dep.graphSettings ++ Seq(
        organization in ThisBuild := "org.kiji.testing", 
	version in ThisBuild := "0.96-0.1.5",
	scalaVersion in ThisBuild := Version.Scala
      )

  lazy val project = Project(
    id = "fake-hbase",
    base = file("."),
    settings = defaultSettings ++ HBase.settings)
   .settings(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % Version.ScalaTest % "test",
      "org.easymock" % "easymock" % Version.EasyMock ,
      "cglib" % "cglib-nodep" % Version.CGLIB,
      "org.slf4j" % "jcl-over-slf4j" % Version.Slf4j,
      "junit" % "junit" % Version.JUnit % "test"
    ).excluding(Version.slf4jExclusions :_*)
     .excluding(Version.commonExclusions :_*)
    )
}

