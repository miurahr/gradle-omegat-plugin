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
Note:
As in 20, Auguest, 2016, the plugin is located my bintray repository and not in jcenter nor
official plugin repository.

#### Step 2: Add the `omegat` configuration closure to your `build.gradle` file

```groovy
omegat {
    projectDir project.rootDir
}
```

If not specified, the OmegaT plugin assumes project directory `$projectDir` is an omegat project root.

### Step 3: Add your own custom OmegaT task

You can make your own custom task using `OmegatTask` type.
Here is an example to run Java properties' alignment from gradle task.

```groovy
repositories {
    jcenter()
}
import org.omegat.gradle.OmegatTask

task runOmegaT(type: OmegatTask) {
    options = [projectDir]
}

```

### Step 4: Call translation

```bash
$ ./gradlew translate
```

This will generate translation result in OmegaT target directory.


### Step 5: Clean targets

```bash
$ ./gradlew cleanTranslation
```

# Get involved

## Send patch

It is recommended to send a pull request to github project as usual.

## Discussion

There is a gitter chat room for this project.

## IDE

Original author uses JetBrains IntelliJ IDEA community edition
for development. Thanks JetBrains for providing great IDE for
OSS community.


## Release procedure

### Update version and dependency

Edit `gradle.properties` for artifact version increment.
Also edit `src/main/resources/org/omegat/gradle/omegat.properties` for
dependency versions. This text is used both Gradle build script and
plugin source.

### Test artifact

Run
```
$ ./gradlew publishToMavenLocal
```
then gradle installs an artifact into your local maven repository.
This effects your development environment to support new test version
is accessible from your test project.

### Upload artifact

After you satisfy test result, you can upload the artifact onto bintray
maven repository.
```
$ ./graldew bintrayUpload
```

