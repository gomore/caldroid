// Based on https://medium.com/@StefMa/its-time-to-ditch-the-buildscript-block-a1ab12e0d9ce
pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
  }

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id.startsWith("com.android")) {
        useModule("com.android.tools.build:gradle:${requested.version}")
      }
    }
  }
}

include(":caldroid")
