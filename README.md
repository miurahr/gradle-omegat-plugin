# Overview

The OmegaT gradle plugin allow you to generate translation files from original source text
with TMX translation memory DB.

# Getting Started Using the Plugin

Please follow the below steps to add the Gradle OmegaT Plugin to your Gradle build script.

#### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
plugins {
    id 'org.omegat.gradle' version '1.0.0'
}
```

- As of 19, Dec. 2020, the project waits an approval by gradle portal. 

#### Step 2: (optional) `omegat` configuration closure to your `build.gradle` file

```groovy
omegat {
    projectDir project.rootDir
}
```

If not specified, the OmegaT plugin assumes project directory `$projectDir` is an omegat project root.

### (optional) your custom OmegaT task

You can make your own custom task using `OmegatTask` type.
Here is an example to run Java properties' alignment from gradle task.

```groovy
import org.omegat.gradle.OmegatTask

task runOmegaT(type: OmegatTask) {
    options = [projectDir]
}

```

###  Call translation

```bash
$ ./gradlew translate
```

This will generate translation result in OmegaT target directory.


###  Clean targets

```bash
$ ./gradlew cleanTranslation
```
