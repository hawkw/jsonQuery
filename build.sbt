name := "jsonQuery"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.json" % "json" % "20160212"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

enablePlugins(VersionEyePlugin)

// VersionEyePlugin.projectSettings
existingProjectId in versioneye := "579242c7b7463b003b181878"
baseUrl in versioneye := "https://www.versioneye.com"
apiPath in versioneye := "/api/v2"
publishCrossVersion in versioneye := true
