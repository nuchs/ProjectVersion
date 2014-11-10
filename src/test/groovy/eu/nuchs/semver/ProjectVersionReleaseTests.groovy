package eu.nuchs.semver

import spock.lang.*

class ProjectVersionReleaseTests extends Specification {

  def 'Doing a patch release should reset the tag' () {
    given: 'The version is 0.1.0-bacon.1'  
      def sut = new ProjectVersion(tag:'bacon.1')
    when: 'I do a patch release'
      sut.patchRelease()
    then: 'The version tag should be reset'
      sut.tag() == ''
  }

  def 'Doing a patch release should reset the meta info' () {
    given: 'The version is 0.1.0+bacon.1'  
      def sut = new ProjectVersion(metaInfo:'bacon.1')
    when: 'I do a patch release'
      sut.patchRelease()
    then: 'The version meta info should be reset'
      sut.metaInfo() == ''
  }

  def 'Doing a patch release should increment the patch version' () {
    given: 'The version is 0.1.0'  
      def sut = new ProjectVersion()
    when: 'I do a patch release'
      sut.patchRelease()
    then: 'The patch version should be one'
      sut.patch() == 1
  }

  def 'Doing a minor release should reset the tag' () {
    given: 'The version is 0.1.0-bacon.1'  
      def sut = new ProjectVersion(tag:'bacon.1')
    when: 'I do a minor release'
      sut.minorRelease()
    then: 'The version tag should be reset'
      sut.tag() == ''
  }

  def 'Doing a minor release should reset the meta info' () {
    given: 'The version is 0.1.0+bacon.1'  
      def sut = new ProjectVersion(metaInfo:'bacon.1')
    when: 'I do a minor release'
      sut.minorRelease()
    then: 'The version meta info should be reset'
      sut.metaInfo() == ''
  }

  def 'Doing a minor release should reset the patch version' () {
    given: 'The version is 0.1.1'  
      def sut = new ProjectVersion(patch:1)
    when: 'I do a minor release'
      sut.minorRelease()
    then: 'The version patch version should be zero'
      sut.patch() == 0
  }

  def 'Doing a minor release should increment the minor version' () {
    given: 'The version is 0.1.0'  
      def sut = new ProjectVersion()
    when: 'I do a minor release'
      sut.minorRelease()
    then: 'The minor version should be two'
      sut.minor() == 2
  }

  def 'Doing a major release should reset the tag' () {
    given: 'The version is 0.1.0-bacon.1'  
      def sut = new ProjectVersion(tag:'bacon.1')
    when: 'I do a major release'
      sut.majorRelease()
    then: 'The version tag should be reset'
      sut.tag() == ''
  }

  def 'Doing a major release should reset the meta info' () {
    given: 'The version is 0.1.0+bacon.1'  
      def sut = new ProjectVersion(metaInfo:'bacon.1')
    when: 'I do a major release'
      sut.majorRelease()
    then: 'The version meta info should be reset'
      sut.metaInfo() == ''
  }

  def 'Doing a major release should reset the patch version' () {
    given: 'The version is 0.1.1'  
      def sut = new ProjectVersion(patch:1)
    when: 'I do a major release'
      sut.majorRelease()
    then: 'The version patch version should be zero'
      sut.patch() == 0
  }

  def 'Doing a major release should reset the minor version' () {
    given: 'The version is 0.1.0'  
      def sut = new ProjectVersion()
    when: 'I do a major release'
      sut.majorRelease()
    then: 'The minor version should be zero'
      sut.minor() == 0
  }

  def 'Doing a major release should increment the major version' () {
    given: 'The version is 0.1.0'  
      def sut = new ProjectVersion()
    when: 'I do a major release'
      sut.majorRelease()
    then: 'The major version should be two'
      sut.major() == 1
  }

  def 'Doing a normal build without specifying a tag or meta info should reset both' () {
    given: 'The version is 0.1.0-SNAPSHOT+1.0' 
      def sut = new ProjectVersion(tag:'SNAPSHOT', metaInfo:'1.0')
    when: 'I do a build'
      sut.normalBuild()
    then: 'The tag and meta info should be reset'
      sut.tag() == ''
      sut.metaInfo() == ''
  }

  def 'Specifying a tag during a normal build should add it to the version' () {
    given: 'The version is 0.1.0' 
      def sut = new ProjectVersion()
    when: 'I do a build with the tag SNAPSHOT'
      sut.normalBuild(tag:'SNAPSHOT')
    then: 'the tag should be SNAPSHOT'
      sut.tag() == 'SNAPSHOT'
  }

  def 'Specifying the meta info during a normal build should add it to the version' () {
    given: 'The version is 0.1.0' 
      def sut = new ProjectVersion()
    when: 'I do a build with the meta info BACON'
      sut.normalBuild(metaInfo:'BACON')
    then: 'the meta info should be BACON'
      sut.metaInfo() == 'BACON'
  }

  def 'Doing a normal build should not change the patch version' () {
    given: 'The version is 0.1.2' 
      def sut = new ProjectVersion(patch:2)
    when: 'I do a build'
      sut.normalBuild()
    then: 'Then the patch version should still be two'
      sut.patch() == 2
  }

  def 'Doing a normal build should not change the minor number' () {
    given: 'The version is 0.2.0' 
      def sut = new ProjectVersion(minor:2)
    when: 'I do a build'
      sut.normalBuild()
    then: 'Then the minor version should still be two'
      sut.minor() == 2
  }

  def 'Doing a normal build should not change the major number' () {
    given: 'The version is 2.1.0' 
      def sut = new ProjectVersion(major:2)
    when: 'I do a build'
      sut.normalBuild()
    then: 'Then the major version should still be two'
      sut.major() == 2
  }

  def 'Doing a normal build with a non alphanumeric or dot character in the tag should fail' () {
    given:'The version is 0.1.0' 
      def sut = new ProjectVersion()
    when: 'I do a build with an invalid tag'
      sut.normalBuild(tag:'*')
    then: 'An IllegalArgumentExeption will be thrown'
      thrown(IllegalArgumentException)
  }

  def 'Doing a normal build with a non alphanumeric or dot character in the meta info should fail' () {
    given:'The version is 0.1.0' 
      def sut = new ProjectVersion()
    when: 'I do a build with invalid meta info'
      sut.normalBuild(metaInfo:'*')
    then: 'An IllegalArgumentExeption will be thrown'
      thrown(IllegalArgumentException)
  }
}
