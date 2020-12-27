# Overview

The OmegaT gradle plugin allow you to generate translation files from original source text
with TMX translation memory DB.

# Getting Started Using the Plugin to generate translation from OmegaT team project

Please follow the below steps to add the Gradle OmegaT Plugin to your Gradle build script.

### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
plugins {
    id 'org.omegat.gradle' version '1.2.3'
}
```

### Step 2: (optional) `omegat` configuration closure to your `build.gradle` file

With this configuration, you can put build.gradle file on other than project root.

```groovy
omegat {
    projectDir "path/to/omegat/team/project"
}
```

If not specified, the OmegaT plugin assumes project directory `$projectDir` is an omegat project root.


###  Call translation

```bash
$ ./gradlew translate
```

This will generate translation result in OmegaT target directory.

# Getting started with the plugin to develop a custom OmegaT plugin

### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
plugins {
    id 'org.omegat.gradle' version '1.2.3'
}
```

### Step 2: `omegat` configuration closure to your `build.gradle` file

```groovy
omegat {
    version '5.2.0' // available: 5.2.0, 5.4.1
    pluginClass "your.plugin.main.className" // mandatory for plugin development

}
```

The plugin automatically configure gradle project to depend on specified version of OmegaT and
it is set as dependency but not included into plugin jar file.

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
