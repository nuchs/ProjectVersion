package eu.nuchs.semver

import groovy.json.*

class JsonSerialiser {

  String toJson(ProjectVersion version) {
    new JsonBuilder(
      major:version.major(),
      minor:version.minor(),
      patch:version.patch(),
      tag:version.tag(),
      metaInfo:version.metaInfo(),
    )
  }

  ProjectVersion fromJson(String json) {
    def slurper = new JsonSlurper()
    new ProjectVersion(slurper.parseText(json))
  }
}
