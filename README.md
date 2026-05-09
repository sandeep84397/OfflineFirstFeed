# OfflineFirstFeed

KMP feed app. Android + iOS, one codebase.

The DB is the source of truth. Network seeds it. UI never waits on the network.

## Stack

Kotlin Multiplatform, Compose Multiplatform, SQLDelight, Paging 3 (androidx), Ktor, Koin.

## Run

Android:

```
./gradlew :composeApp:installDebug
```

iOS: open `iosApp/iosApp.xcodeproj` in Xcode, pick a simulator, run.

## Status

Read path works. Write path + sync is next.

API is stubbed for now (`FakeFeedApi`). Plug in a real backend by swapping the Koin binding.

## Writeup

I'm writing about the design as I go: [Medium series](https://medium.com/@sandeepdhami).
