package eu.nuchs.semver

import spock.lang.*

class JsonSerialiserTests extends Specification {

  def 'The major version should be serialised as JSON' () {
    given: 'The version is 1.1.0'  
      def version = new ProjectVersion(major:1)
    when: 'I convert the version to JSON'
      def sut = new JsonSerialiser()
      def result = sut.toJson(version)
    then: 'The major version should be in the JSON'
      result.contains('"major":1') 
  }

  def 'The minor version should be serialised as JSON' () {
    given: 'The version is 0.2.0'  
      def version = new ProjectVersion(minor:2)
    when: 'I convert the version to JSON'
      def sut = new JsonSerialiser()
      def result = sut.toJson(version)
    then: 'The minor version should be in the JSON'
      result.contains('"minor":2') 
  }

  def 'The patch version should be serialised as JSON' () {
    given: 'The version is 0.1.1'  
      def version = new ProjectVersion(patch:1)
    when: 'I convert the version to JSON'
      def sut = new JsonSerialiser()
      def result = sut.toJson(version)
    then: 'The patch version should be in the JSON'
      result.contains('"patch":1') 
  }

  def 'The version tag should be serialised as JSON' () {
    given: 'The version is 0.1.0-BACON'  
      def version = new ProjectVersion(tag:'BACON')
    when: 'I convert the version to JSON'
      def sut = new JsonSerialiser()
      def result = sut.toJson(version)
    then: 'The tag version should be in the JSON'
      result.contains('"tag":"BACON"') 
  }

  def 'The version metaInfo should be serialised as JSON' () {
    given: 'The version is 0.1.0+SAUSAGE'  
      def version = new ProjectVersion(metaInfo:'SAUSAGE')
    when: 'I convert the version to JSON'
      def sut = new JsonSerialiser()
      def result = sut.toJson(version)
    then: 'The metaInfo version should be in the JSON'
      result.contains('"metaInfo":"SAUSAGE"') 
  }

  def 'The major version should match the json version' () {
    given: 'a json string {"major":1}'
      def json = '{"major":1}'
    when: 'I convert the JSON to a projectVersion'
      def sut = new JsonSerialiser()
      def result = sut.fromJson(json)
    then: 'The major version should be the value parsed from the json'
      result.major() == 1
  }

  def 'The minor version should match the json version' () {
    given: 'a json string {"minor":2}'
      def json = '{"minor":2}'
    when: 'I convert the JSON to a projectVersion'
      def sut = new JsonSerialiser()
      def result = sut.fromJson(json)
    then: 'The minor version should be the value parsed from the json'
      result.minor() == 2
  }

  def 'The patch version should match the json version' () {
    given: 'a json string {"patch":1}'
      def json = '{"patch":1}'
    when: 'I convert the JSON to a projectVersion'
      def sut = new JsonSerialiser()
      def result = sut.fromJson(json)
    then: 'The patch version should be the value parsed from the json'
      result.patch() == 1
  }

  def 'The version tag should match the json version' () {
    given: 'a json string {"tag":"BACON"}'
      def json = '{"tag":"BACON"}'
    when: 'I convert the JSON to a projectVersion'
      def sut = new JsonSerialiser()
      def result = sut.fromJson(json)
    then: 'The tag version should be the value parsed from the json'
      result.tag() == 'BACON'
  }

  def 'The metaInfo version should match the json version' () {
    given: 'a json string {"metaInfo":"SAUSAGE"}'
      def json = '{"metaInfo":"SAUSAGE"}'
    when: 'I convert the JSON to a projectVersion'
      def sut = new JsonSerialiser()
      def result = sut.fromJson(json)
    then: 'The metaInfo version should be the value parsed from the json'
      result.metaInfo() == 'SAUSAGE'
  }
}
