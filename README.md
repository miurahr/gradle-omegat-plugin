# Overview

The OmegaT gradle plugin allow you to generate translation files from original source text
with TMX translation memory DB.

# Getting Started 

There are two types of tasks of the plugin.

1. Tasks on translation project

2. Tasks for OmegaT plugin development project


## Generate translation on OmegaT team project

Please follow the below steps to add the Gradle OmegaT Plugin to your Gradle build script.

### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
plugins {
    id 'org.omegat.gradle' version '1.5.4'
}
```
or `build.gradle.kts` in Kotlin;

```kotlin
plugins {
    id("org.omegat.gradle") version "1.5.4"
}
```

### Step 2: (optional) `omegat` configuration closure to your `build.gradle` file

With this configuration, you can put build.gradle file on other than team project root where `omegat.project` file located.

```groovy
omegat {
    projectDir "path/to/omegat/team/project"
}
```
or kotlin
```kotlin
omegat {
    projectDir = "path/to/omegat/project"
}
```

If not specified projectDir, the OmegaT plugin assumes project root is an omegat project.

###  Call translation

```bash
$ ./gradlew translate
```

This will generate translation result in OmegaT target directory.

## Development of a custom OmegaT plugin

### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
plugins {
    id 'org.omegat.gradle' version '1.5.4'
}
```
or in kotlin
```kotlin
plugins {
    id("org.omegat.gralde") version "1.5.4"
}
```

### Step 2: `omegat` configuration closure to your `build.gradle` file

When you want to use the omegat gradle plugin for omegat plugin development, there are several
mandantory configurations. 

for groovy DSL

```groovy
omegat {
    version = "5.4.4" // (optional) Build against OmegaT version;
                      // available versions(as in Jan. 2022): 5.4.4, 5.5.0, 5.6.0, 5.7.0
    debugPort = 5566 // (optional) when you use a java debugger, no need when use IDEs
    pluginClass = "your.plugin.package.and.className" // mandatory for plugin development
    pluginName = "Human readable plugin name"  // when property plugin.name dees not exist this is used
}
```

for kotlin DSL

```kotlin
omegat {
    version = "5.4.4" // (optional) Build against OmegaT version;
                      // available versions(as in Jan. 2022): 5.4.4, 5.5.0, 5.6.0, 5.7.0
    debugPort = 5566 // (optional) when you use a java debugger, no need when use IDEs
    pluginClass = "your.plugin.package.and.className" // mandatory for plugin development
    pluginName = "Human readable plugin name"  // when property plugin.name dees not exist this is used
}
```

The plugin automatically configure gradle project to depend on specified version of OmegaT.
When there is an option `debugPort`, a task named `debugOmegaT` is created which run OmegaT
with a jvm debugger port.

When launching `runOmegaT` or `debugOmegaT`, project will build jar file and place
plugin into temporal configuration folder `build/omegat/plugins` then launch OmegaT
and open OmegaT project at configured as `omegat.projectDir`

### Step 3. Configure dependencies

You can put dependencies with packIntoJar configuration, dependencies are bundled with plugin as Fat-Jar.
Libraries other than packIntoJar such as implementation, compile etc. are used to compile but not bundled.
A following example illustrate how to use, and jackson is going to be bundled, and commons-io library is not.
It is because commons-io is a dependency of OmegaT, so we can use it without bundled.

```groovy
dependencies {
    packIntoJar 'com.fasterxml.jackson.core:jackson:2.13.1'
    implementation 'commons-lang:commons-lang:2.6'
    // ...
}
```

or in kotlin example, it bundles jar files placed in `lib` sub-folder;

```kotlin
dependencies {
    packIntoJar("com.fasterxml.jackson.core:jackson:2.13.1")
    packIntoJar(fileTree("lib") {include("*.jar")})
    implementation("commons-lang:commons-lang:2.6")
    // ...
}
```

### Step 4. Setup plugin properties

There are several plugin information to be managed on OmegaT preference.
You can set it on `gradle.properties` file, and the plugin generate proper manifest records.
Here is an example.

```properties
plugin.name=Plugin Name
plugin.author=Plugin developer name
plugin.category=machinetranslator
plugin.description=A plugin to look up online dictionary
plugin.link=https://github.com/miurahr/omegat-onlinedictionary
plugin.license=GNU General Public License version 3
```

Here is a table how properties becomes manifest record;

|                    | plugin manifest    | gradle.properties    | standard property   | omegat extension |
|--------------------|--------------------|----------------------|---------------------|------------------|
| Name               | Plugin-Name        | `plugin.name`        | n/a                 | `pluginName`     |
| Version            | Plugin-Version     | n/a                  | version             | n/a              |
| Author             | Plugin-Author      | `plugin.author`      | n/a                 | n/a              |
| Description        | Plugin-Description | `plugin.description` | n/a                 | n/a              |
| Website            | Plugin-Link        | `plugin.link`        | n/a                 | n/a              |
| Category           | Plugin-Category    | `plugin.category`    | n/a                 | n/a              |
| Built environment  | Created-By         | n/a                  | n/a                 | n/a              |
| Date               | Plugin-Date        | n/a                  | n/a                 | n/a              |
| Class name         | OmegaT-Plugins     | n/a                  | n/a                 | n/a              |

Plugin Name can be configured with `plugin.name` property. When it is not set,
`rootProject.name` gradle property that is configured in `settings.gradle` is used.
When both properties are not set, project directory name is used as
same as ordinary gradle projects.
Built environment and date records are automatically added to manifest.
Class name is configured by extension `omegat.pluginClass` in `build.gradle`.
