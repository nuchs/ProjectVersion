package eu.nuchs.semver

import spock.lang.*

class ProjectVersionComparisonTests extends Specification {

  def 'The Version With The Largest Major Version Is Most Recent' () {
    given : 'Two versions 1.3.3 and 2.0.0' 
      def sut1 = new ProjectVersion(major:1, minor:3, patch:3)
      def sut2 = new ProjectVersion(major:2, minor:0, patch:0)
    expect :
      assert sut1 < sut2
      assert sut2 > sut1
  }

  def 'If Major Versions Are Equal The Version With The Largest Minor Version Is Most Recent' () {
    given : 'Two versions 0.3.0 and 0.2.3' 
      def sut1 = new ProjectVersion(minor:3)
      def sut2 = new ProjectVersion(minor:2, patch:3)
    expect :
      assert sut1 > sut2
      assert sut2 < sut1
  }

  def 'If Major And Minor Versions Are Equal The Version With The Largest Patch Version Is Most Recent' () {
    given : 'Two versions 0.1.0 and 0.1.3' 
      def sut1 = new ProjectVersion()
      def sut2 = new ProjectVersion(patch:3)
    expect :
      assert sut1 < sut2
      assert sut2 > sut1
  }

  def 'If Numeric Versions Are Equal The Version Without A Tag Is More Recent' () {
    given : 'Two versions 0.1.0 and 0.1.0-ALPHA' 
      def sut1 = new ProjectVersion()
      def sut2 = new ProjectVersion(tag:'ALPHA')
    expect :
      assert sut1 > sut2
      assert sut2 < sut1
  }

  def 'Identical Versions Are Considered Equal' () {
    given : 'Two versions 1.2.3-ALPHA and 1.2.3-ALPHA' 
      def sut1 = new ProjectVersion(major:1, minor:2, patch:3, tag:'ALPHA')
      def sut2 = new ProjectVersion(major:1, minor:2, patch:3, tag:'ALPHA')
    expect :
      assert sut1 >= sut2
      assert sut1 <= sut2
  }
  
  def 'Meta info does not affect which build is considered most recent' () {
    given : 'Two versions 0.1.0 and 0.1.0-ALPHA' 
      def sut1 = new ProjectVersion()
      def sut2 = new ProjectVersion(metaInfo:'ALPHA')
    expect :
      assert sut1 >= sut2
      assert sut1 <= sut2
  }

  def 'Version tags are compared left to right' () {
    given : 'Two versions 0.1.0-alpha.1.2 and 0.1.0-alpha.2.1' 
      def sut1 = new ProjectVersion(tag:'alpha.1.2')
      def sut2 = new ProjectVersion(tag:'alpha.2.1')
    expect :
      assert sut1 < sut2
      assert sut2 > sut1
  }
}
