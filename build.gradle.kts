buildscript {
  repositories {
    google()
    mavenCentral()
    jcenter()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:3.3.0")
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    jcenter()
  }
}

tasks.wrapper {
  gradleVersion = "5.1.1"
  distributionType = Wrapper.DistributionType.ALL
}

plugins {
  id("com.github.ben-manes.versions") version "0.20.0"
}
