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

  // https://junit.org/junit5/docs/current/user-guide/
  testImplementation(platform("org.junit:junit-bom:5.11.4"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  // https://kotlinlang.org/api/latest/kotlin.test/kotlin.test/
  testImplementation(kotlin("test"))

  // https://kotest.io/docs/assertions/assertions.html
  testImplementation("io.kotest:kotest-assertions-core:5.9.1")

  // https://mockk.io/
  testImplementation("io.mockk:mockk:1.13.14")
}
