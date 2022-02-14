pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Todo"

enableFeaturePreview("VERSION_CATALOGS")

include(
    ":app", ":feature_todo", ":model-todo", ":feature_details", ":feature_edit"
)
