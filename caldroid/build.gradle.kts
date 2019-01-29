plugins {
  id("com.android.library")
}

android {
  buildToolsVersion("28.0.3")
  compileSdkVersion(28)

  defaultConfig {
    minSdkVersion(21)
    targetSdkVersion(28)
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = false
      isUseProguard = false
    }
  }
}

dependencies {
  api("com.android.support:support-v4:28.0.0")
  api("com.darwinsys:hirondelle-date4j:1.5.1")
}
