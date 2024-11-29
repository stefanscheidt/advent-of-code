plugins { id("com.diffplug.spotless") version "6.25.0" }

repositories { mavenCentral() }

spotless {
  format("misc") {
    target("**/*.md", "**/*.xml", "**/*.yml", "**/*.yaml", "**/*.html", "**/*.css", ".gitignore")
    targetExclude("**/build/**/*", "**/.idea/**")
    trimTrailingWhitespace()
    endWithNewline()
    indentWithSpaces(2)
  }

  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt("0.53").googleStyle()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*.gradle.kts")
    ktfmt("0.53").googleStyle()
  }

  freshmark { target("*.md") }
}
