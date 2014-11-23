package eu.nuchs.semver

import org.gradle.api.*
import groovy.json.*

class SemverPlugin implements Plugin<Project> {

  void apply(Project aProject) {
    project = aProject
    config = project.convention.plugins.SemVer = new SemVerPluginConvention()

    project.task('startVersioning') << {
      loadedVersion = new ProjectVersion()
      persistVersion()
    }

    project.task('printVersion') << {
      println version()
    }

    project.task('majorRelease') << {
      release(version().&incrementMajorVersion)
    }

    project.task('minorRelease') << {
      release(version().&incrementMinorVersion)
    }

    project.task('patchRelease') << {
      release(version().&incrementPatchVersion)
    }

    project.task('majorPreRelease') << {
      release(version().&incrementMajorVersion, tag(), metaInfo())
    }

    project.task('minorPreRelease') << {
      release(version().&incrementMinorVersion, tag(), metaInfo())
    }

    project.task('patchPreRelease') << {
      release(version().&incrementPatchVersion, tag(), metaInfo())
    }
  }

  private void release(Closure releaseType, String tag='', String metaInfo='') {
    releaseType()
    version().addPreReleaseDetails([tag:tag, metaInfo:metaInfo])
    persistVersion()
  }

  private String tag() {
    project.hasProperty('tag') ? project.tag : config.tag
  }

  private String metaInfo() {
    project.hasProperty('metaInfo') ? project.metaInfo : config.metaInfo
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
  private def project
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
