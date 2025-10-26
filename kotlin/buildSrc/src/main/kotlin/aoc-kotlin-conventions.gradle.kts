plugins { kotlin("jvm") }

kotlin { jvmToolchain(21) }

tasks.test {
  useJUnitPlatform()
  testLogging { events("passed", "skipped", "failed") }
}

repositories { mavenCentral() }

dependencies {
  // https://github.com/h0tk3y/better-parse
  implementation("com.github.h0tk3y.betterParse:better-parse:0.4.4")

  // https://docs.junit.org/current/user-guide/
  testImplementation(platform("org.junit:junit-bom:6.0.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  // https://kotlinlang.org/api/latest/kotlin.test/kotlin.test/
  testImplementation(kotlin("test"))

  // https://kotest.io/docs/assertions/assertions.html
  testImplementation("io.kotest:kotest-assertions-core:6.0.4")

  // https://mockk.io/
  testImplementation("io.mockk:mockk:1.14.6")
}
