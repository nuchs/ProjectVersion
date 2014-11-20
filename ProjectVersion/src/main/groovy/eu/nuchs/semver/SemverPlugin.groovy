package eu.nuchs.semver

import org.gradle.api.*

class SemverPlugin implements Plugin<Project> {

  void apply(Project project) {
    project.task('hello') << {
      println 'Hello World!' 
    }
  }
}
