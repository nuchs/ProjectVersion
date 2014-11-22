package eu.nuchs.semver

import org.gradle.api.*
import groovy.json.*

class SemverPlugin implements Plugin<Project> {

  void apply(Project project) {
    config = new SemVerPluginConvention()
    project.convention.plugins.SemVer = config

    project.task('startVersioning') << {
      loadedVersion = new ProjectVersion()
      persistVersion()
    }

    project.task('printVersion') << {
      println version()
    }

    project.task('majorRelease') << {
      version().incrementMajorVersion()
      persistVersion()
    }

    project.task('minorRelease') << {
      version().incrementMinorVersion()
      persistVersion()
    }

    project.task('patchRelease') << {
      version().incrementPatchVersion()
      persistVersion()
    }
  }

  private ProjectVersion version () {
    loadedVersion ?: loadVersion()
  }

  private def persistVersion () {
    def file = new File(config.file)
    file.text = json.toJson(version())
  }

  private def loadVersion () {
    def file = new File(config.file)
    loadedVersion = json.fromJson(file.text)
  }

  private def config
  private def json = new JsonSerialiser()
  private ProjectVersion loadedVersion = null;
}

class SemVerPluginConvention {
  String file = 'version.json';
  String tag = 'SNAPSHOT'
  String metaInfo = ''

  def SemVer(Closure closure) {
    closure.delegate = this
    closure()
  }
}
