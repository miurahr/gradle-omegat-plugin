# Change Log
All notable changes to this project will be documented in this file.

## [Unreleased]

## [1.5.4] - 2021-01-09

### Changed
- Does not use project.name as Plugin-Name. 
- plugin.name` property or `omegat { pluginName }` is mandatory.
- Bump Versions
  - gradle@7.3.3
    - plugin-publish@0.19.0
    - kotlin@1.6.10
  - actions
    - setup-java@2.5.0
      - temurin opnejdk 8
    - upload-artifact@2.3.1
    - checkout@2.4.0
- Drop dependency for Dokka
 
## [1.5.3] - 2021-06-08

### FIXED
- CI: release script affected by changing to setup-java@2.1.0

### Added
- Enable dependabot

### Removed
- Remove Plugin-Id property.

### Changed
- Drop configuration of jCenter
- Bump Gralde@6.9
- Bump Kotlin(JVM)@1.5.0
- Bump gradle-plugin-publish@0.14.0
- Bump dokka@1.4.32
- CI: Bump setup-java@2.1.0
- CI: Bump upload-artifact@2.2.3
- CI: Bump checkout@2.3.4


## [1.5.0] - 2021-05-18

### Added
- Add Plugin-Id property.

### Changed
- Retrieve OmegaT and related packages from MavenCentral(moved).

## [1.4.2] - 2021-01-25

### Added
- Add more manifest property keys: Plugin-Link, Plugin-Category, Created-By, and Plugin-Date

### Changed
- Signature of OmegatManifest constructor.


## [1.4.1] - 2021-01-18

### Fixed
- Fix typo in manifest key Plugin-Name

## [1.4.0] - 2021-01-18

### Added
- Support plugins properties information in jar manifest(#5)

## [1.3.2] - 2021-01-13

### Changed
- Open OmT project When `omegat.projectDir` specified for `runOmegaT` task 

## [1.3.1] - 2021-01-10

### Fixed
- Suppress annotation error

## [1.3.0] - 2021-01-10

### Changed
- Use Kotlin to implement the plugin.
- Support launching OmegaT tasks `runOmegaT` and `debugOmegaT`
- Add dependency for vldocking.

## [1.2.3] - 2020-12-27

### Changed
- Use Kotlin for build script

## [1.2.2] - 2020-12-26

### Fixed
- Fix plugin load error

### Added
- Add manifest configuration
- Introduce 'packIntoJar' configuration

## [1.1.1] - 2020-12-23

### Added
- Support OmegaT plugin development
- make OmegaT version configurable
- Add dependency as compileOnly and testImplementation

### Deleted
- Drop signatory from build script

## Fixed
- Fix publish automation
- Fix vcsUrl for publish

## [1.0.0] - 2020-12-18

### Changed
- Reboot the project.
- Change plugin name 'org.omegat.gradle'
- Use Gradle-publish plugin
- Drop signing when publish
- Bump up OmegaT 5.4.1
- Bump up Gradle 6.7.1
- CI: Github actions

## [0.9.5] - 2017-4-29
## [0.9.4] - 2017-2-22
## [0.9.1] - 2016-9-25


[Unreleased]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.5.4...HEAD
[1.5.4]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.5.3...v1.5.4
[1.5.3]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.5.0...v1.5.3
[1.5.0]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.4.2...v1.5.0
[1.4.2]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.4.1...v1.4.2
[1.4.1]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.4.0...v1.4.1
[1.4.0]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.3.2...v1.4.0
[1.3.2]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.3.1...v1.3.2
[1.3.1]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.3.0...v1.3.1
[1.3.0]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.2.0...v1.3.0
[1.2.0]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.1.1...v1.2.0
[1.1.1]: https://github.com/miurahr/omegat-textra-plugin/compare/v1.0.0...v1.1.1
[1.0.0]: https://github.com/miurahr/omegat-textra-plugin/compare/v0.9.5...v1.0.0
[0.9.5]: https://github.com/miurahr/omegat-textra-plugin/compare/v0.9.4...v0.9.5
[0.9.4]: https://github.com/miurahr/omegat-textra-plugin/compare/v0.9.1...v0.9.4
[0.9.1]: https://github.com/miurahr/omegat-textra-plugin/compare/v0.9.0...v0.9.1
