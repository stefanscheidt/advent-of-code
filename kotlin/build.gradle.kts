plugins { id("com.diffplug.spotless") version "8.0.0" }

repositories { mavenCentral() }

spotless {
  format("misc") {
    target("**/*.md", "**/*.xml", "**/*.yml", "**/*.yaml", "**/*.html", "**/*.css", ".gitignore")
    targetExclude("**/build/**/*", "**/.idea/**")
    trimTrailingWhitespace()
    endWithNewline()
    leadingTabsToSpaces(2)
  }

  kotlin {
    target("**/*.kt")
    targetExclude("**/build/**/*")
    ktfmt().googleStyle()
  }

  kotlinGradle {
    target("**/*.gradle.kts")
    targetExclude("**/build/**/*")
    ktfmt().googleStyle()
  }

  freshmark { target("*.md") }
}
