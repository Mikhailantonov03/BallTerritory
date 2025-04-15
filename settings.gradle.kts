enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BallTerritory"

include(":app")
include(":core:model")

include(":core:database")
include(":core:network")

include(":features:auth")
include(":core:designsystem")

include(":core:navigation")
include(":core:data")
include(":features:main")
include(":core:ui")
include(":features:schedule")
include(":features:coaches")
include(":features:rent")
include(":features:profile")
