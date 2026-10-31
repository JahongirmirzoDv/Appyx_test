# Appyx Navigation Experiment

A Kotlin Android sample exploring Appyx node-based navigation inside Jetpack Compose. It uses a Spotlight routing source to switch between three screens.

## What it demonstrates

- A root node resolves a sealed set of screen routes.
- Previous and Next controls drive Spotlight navigation.
- Compose animations reflect navigation availability.

## Explore the code

- `app/src/main/java/com/mobiledv/appyx_test/RootNode.kt` — Spotlight routing and navigation controls.
- `app/src/main/java/com/mobiledv/appyx_test/Screen1.kt` — first demonstration screen.
- `app/src/main/java/com/mobiledv/appyx_test/Screen2.kt` — second demonstration screen.
- `app/src/main/java/com/mobiledv/appyx_test/Screen3.kt` — third demonstration screen.

## Open and build

Open the repository in Android Studio and use a Gradle JDK compatible with its checked-in Android Gradle Plugin. Install Android SDK Platform 32 and let the Gradle wrapper resolve the project dependencies.

```bash
./gradlew :app:assembleDebug
```

Run the `app` configuration on a device or emulator. On Windows, use `gradlew.bat`.

## Project status

This repository is a learning project. Its original dependency versions are retained; dependency resolution and behavior on current Android versions may require maintenance. The build references early Appyx artifacts, including `main-SNAPSHOT`; their availability and APIs may differ from current Appyx releases.

