# Publishing (JitPack)

This project is set up so the `:toolkit` module can be consumed as a remote Gradle dependency via JitPack.

## Prereqs
- Repo pushed to GitHub
- A git tag (JitPack uses tags as versions)

## 1) Create and push a version tag

```bash
git tag v0.1.1
git push origin v0.1.1
```

## 2) Wait for JitPack to build

Open:
- `https://jitpack.io/#jsonjuliane/aat`

Select the tag and trigger a build if needed.

## 3) Consume from another project

### Add the repository

In `settings.gradle.kts` (recommended):

```kotlin
dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
  }
}
```

### Add the dependency

```kotlin
dependencies {
  implementation("com.github.jsonjuliane:aat:v0.1.1")
}
```

## Troubleshooting

- **Build can’t resolve dependency**: confirm the JitPack build for that tag succeeded.
- **Wrong coordinates**: ensure you’re using the exact `groupId:artifactId:tag` shown on the JitPack page.
- **Android Gradle/SDK mismatch**: JitPack builds with its own environment; keep the library’s Gradle setup simple and avoid depending on local-only paths.

