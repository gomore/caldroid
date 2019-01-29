plugins {
  id("com.android.library") version "3.3.0"
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
  api("androidx.fragment:fragment:1.0.0")
  api("com.darwinsys:hirondelle-date4j:1.5.1")
}
