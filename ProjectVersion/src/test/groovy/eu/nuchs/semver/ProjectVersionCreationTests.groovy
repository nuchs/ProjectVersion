package eu.nuchs.semver

import spock.lang.*

class ProjectVersionCreationTests extends Specification {

  def 'Versioning should start 0.1.0' () {
    given: 'That we have just started recording the version'
      def sut = new ProjectVersion()
    expect: 'That the version should be 0.1.0'
      sut.major() == 0
      sut.minor() == 1
      sut.patch() == 0
      sut.tag() == ''
      sut.metaInfo() == ''
  }

  def 'major should return the major version number of the version' () {
    given: 'The version is 1.0.0'
      def sut = new ProjectVersion(major:1)
    expect: 'That the major version is 1'
      sut.major() == 1
  }

  def 'minor should return the minor version number of the version' () {
    given: 'The version is 0.2.0'
      def sut = new ProjectVersion(minor:2)
    expect: 'That the minor version is 2'
      sut.minor() == 2
  }

  def 'patch should return the patch version number of the version' () {
    given: 'The version is 0.0.1'
      def sut = new ProjectVersion(patch:1)
    expect: 'That the patch version is 1'
      sut.patch() == 1
  }

  def 'tag should return the build tag of the version' () {
    given: 'The version is 0.1.0-bacon'
      def sut = new ProjectVersion(tag:'bacon')
    expect: 'That the build tag is bacon'
      sut.tag() == 'bacon'
  }

  def 'metaInfo should return the build meta information of the version' () {
    given: 'The version is 0.1.0+wibble'
      def sut = new ProjectVersion(metaInfo:'wibble')
    expect: 'That the build meta information is wibble'
      sut.metaInfo () == 'wibble'
  }

  def 'Creating a version with a non numeric major version should fail' () {
    when: 'Creating version bacon.1.0'
      def sut = new ProjectVersion(major:'bacon')
    then: 'A NumberFromatException will be thrown'
      thrown(NumberFormatException)
  }

  def 'Creating a version with a negative major version should fail' () {
    when: 'Creating version -1.1.0'
      def sut = new ProjectVersion(major:-1)
    then: 'A NumberFromatException will be thrown'
      thrown(NumberFormatException)
  }

  def 'Creating a version with a non numeric minor version should fail' () {
    when: 'Creating version 0.bacon.0'
      def sut = new ProjectVersion(minor:'bacon')
    then: 'A NumberFromatException will be thrown'
      thrown(NumberFormatException)
  }

  def 'Creating a version with a negative minor version should fail' () {
    when: 'Creating version 0.-1.0'
      def sut = new ProjectVersion(minor:-1)
    then: 'A NumberFromatException will be thrown'
      thrown(NumberFormatException)
  }

  def 'Creating a version with a non numeric patch version should fail' () {
    when: 'Creating version 0.1.bacon'
      def sut = new ProjectVersion(patch:'bacon')
    then: 'A NumberFromatException will be thrown'
      thrown(NumberFormatException)
  }

  def 'Creating a version with a negative patch version should fail' () {
    when: 'Creating version 0.1.-1'
      def sut = new ProjectVersion(patch:-1)
    then: 'A NumberFromatException will be thrown'
      thrown(NumberFormatException)
  }

  def 'Creating a version with a tag containing a non alphanumeric or dot character should fail' () {
    when: 'Creating version 0.1.0-?'
      def sut = new ProjectVersion(tag:'?')
    then: 'An IllegalArgumentExeption will be thrown'
      thrown(IllegalArgumentException)
  }

  def 'Creating a version with meta info containing a non alphanumeric or dot character should fail' () {
    when: 'Creating version 0.1.0+?'
      def sut = new ProjectVersion(metaInfo:'?')
    then: 'An IllegalArgumentExeption will be thrown'
      thrown(IllegalArgumentException)
  }
}
