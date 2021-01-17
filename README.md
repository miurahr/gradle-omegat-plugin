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
    id 'org.omegat.gradle' version '1.3.2'
}
```
or `build.gradle.kts` in Kotlin;

```kotlin
plugins {
    id("org.omegat.gradle") version "1.3.2"
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
    id 'org.omegat.gradle' version '1.3.2'
}
```
or in kotlin
```kotlin
plugins {
    id("org.omegat.gralde") version "1.3.2"
}
```


### Step 2: `omegat` configuration closure to your `build.gradle` file

```groovy
omegat {
    version '5.2.0' // available: 5.2.0, 5.4.1: default
    pluginClass "your.plugin.main.className" // mandatory for plugin development
    debugPort = 5566 // specify when you use a debugger
    projectDir = File(project.projectDir, "test-omt-project").toString()
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
A following example illustrate how to use, and slf4j-api is going to be bundled, and commons-io library is not.
It is because commons-io is dependency of OmegaT, so we can use it without bundled.

```groovy
dependencies {
    packIntoJar 'org.slf4j:slf4j-api:1.7.25'
    compile 'commons-io:commons-io:2.5'
    compile 'commons-lang:commons-lang:2.6'
    // ...
}
```

### Step 4. Setup plugin properties

There are several plugin information to be managed on OmegaT preference.
You can set it on `gradle.properties` file, and the plugin generate proper manifest records.
Here is an example.

```properties
plugin.author="Plugin developer name"
plugin.description="A plugin to look up online dictionary"
plugin.link=https://github.com/miurahr/omegat-onlinedictionary
```

Here is a table how properties becomes manifest record;


| Data | plugin manifest | gradle.properties | gradle standard property |
| ---- | --------------- | ----------------- | ------------------------ |
| Name | Plugin-Name     | n/a               | rootProject.name         |
| Version | Plugin-Version | n/a             | version                  |
| Author | Plugin-Author | `plugin.author`   | n/a                      |
| Description | Plugin-Description | `plugin.description` | n/a         |
| Website     | Plugin-Link | `plugin.link`  | n/a                      |


