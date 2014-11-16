package eu.nuchs.semver

class ProjectVersion implements Comparable<ProjectVersion> {

  def ProjectVersion (Map version=[:]) {
    versionInfo << version
    ['major', 'minor', 'patch'].each { part -> validateNumber(part) }
    ['tag', 'metaInfo'].each { part -> validateString(part) } 
  }

  def normalBuild (Map buildInfo=[:]) {
    versionInfo.tag = buildInfo.get('tag', '')
    versionInfo.metaInfo = buildInfo.get('metaInfo', '')
    ['tag', 'metaInfo'].each { part -> validateString(part) } 
  }

  def patchRelease () {
    versionInfo.patch++
    resetParts([], ['tag', 'metaInfo'])
  }

  def minorRelease () {
    versionInfo.minor++
    resetParts(['patch'], ['tag', 'metaInfo'])
  }

  def majorRelease () {
    versionInfo.major++
    resetParts(['patch', 'minor'], ['tag', 'metaInfo'])
  }

  int major() { versionInfo.major }
  int minor() { versionInfo.minor }
  int patch() { versionInfo.patch }
  String tag() { versionInfo.tag }
  String metaInfo() { versionInfo.metaInfo }

  String toString() {
    String version = major() + "." + minor()+ "." + patch() 
    version += (tag() == "") ? tag() : "-" + tag()
    version += (metaInfo() == "") ? metaInfo() : "+" + metaInfo()
  }

  int compareTo (ProjectVersion other) {
    return major() <=> other.major() ?:
           minor() <=> other.minor() ?:
           patch() <=> other.patch() ?:
           tag() == '' && other.tag() != '' ?  1 :
           tag() != '' && other.tag() == '' ? -1 :
           tag().compareTo(other.tag())
  }

  /* ----- Private Implementation ----- */

  private def resetParts (List numberParts, List stringParts) {
    numberParts.each { part -> versionInfo[part] = 0 }
    stringParts.each { part -> versionInfo[part] = ''}
  }

  private def validateNumber (String part) {
    if (isNotANumber(part) || isNegative(part)) {
      throw new NumberFormatException(part + ' version must be a non-negative integer')
    }
  }

  private def validateString (String part) {
    if(isNotAString(part) || containsInvalidCharacters(part)) {
      throw new IllegalArgumentException('Version ' + part + ' must be a dot separated set of alphanumeric characters')
    }
  }

  private def isNotANumber (String part) {
    !(versionInfo[part] instanceof Integer)
  }

  private def isNotAString (String part) {
    !(versionInfo[part] instanceof String)
  }

  private def isNegative (String part) {
    versionInfo[part] < 0
  }

  private def containsInvalidCharacters (String part) {
    !(versionInfo[part] ==~ /([a-zA-Z0-9\.]+)?/)
  }

  private Map versionInfo = [
    major:0,
    minor:1,
    patch:0,
    tag:'',
    metaInfo:''
  ]
}
