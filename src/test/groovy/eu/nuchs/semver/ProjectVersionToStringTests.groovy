
package eu.nuchs.semver

import spock.lang.*

class ProjectVersionToStringTests extends Specification {

  def 'The string representation of a version should major.minor.patch' () {
    given : 'A project version of 1.2.3' 
      def sut = new ProjectVersion(major:1, minor:2, patch:3)
    expect : 'toString should print 1.2.3'
      assert sut.toString() == "1.2.3"
  }

  def 'The tag should be appended to the string representation after a dash' () {
    given : 'A project version with a tag of BACON' 
      def sut = new ProjectVersion(tag:"BACON")
    expect : 'toString should print 0.1.0-BACON'
      assert sut.toString() == "0.1.0-BACON"
  }

  def 'The meta info should be appended to the string representation after a plus' () {
    given : 'A project version meta info BACON' 
      def sut = new ProjectVersion(metaInfo:"BACON")
    expect : 'toString should print 0.1.0+BACON'
      assert sut.toString() == "0.1.0+BACON"
  }

  def 'In a version with both a tag and meta info the tag should appear first' () {
    given : 'A project version with tag BACON and meta info SAUSAGE'
      def sut = new ProjectVersion(tag:'BACON', metaInfo:'SAUSAGE')
    expect : 'toString should print 0.1.0-BACON+SAUSAGE'
      assert sut.toString() == '0.1.0-BACON+SAUSAGE'
  }
}
