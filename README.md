# Overview

The OmegaT gradle plugin allow you to generate translation files from original source text
with TMX translation memory DB.

# Getting Started Using the Plugin

Please follow the below steps to add the Gradle OmegaT Plugin to your Gradle build script.

#### Step 1: Apply the plugin to your Gradle script

To apply the plugin, please add one of the following snippets to your `build.gradle` file:

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'org.omegat.gradle:gradle-omegat-plugin:0.9.0'
    }
}
apply plugin: 'com.omegat.gradle'
```

#### Step 2: Add the `translate` task closure to your `build.gradle` file

```groovy
translate{
    projectDir "$projectDir"
}
```

If not specified, the OmegaT plugin assumes project directory `$projectDir` is an omegat project root.

### Step 3: Add your own custom OmegaT task

You can make your own custom task using `OmegatTask` type.
Here is an example to run Java properties alignment from gradle task.

```groovy
import org.omegat.gradle.OmegatTask

task runOmegaT(type: OmegatTask) {
    options = [projectDir]
}

task alignProperties(type: OmegatTask) {
    options = [projectDir, "--mode=console-align", "--alignDir=/translatedFiles/"]
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
