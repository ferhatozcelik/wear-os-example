# Wear OS App Example

A three-module sample project that shows how to build a companion Wear OS experience
around a phone app:

| Module | Type | Package | What it does |
| --- | --- | --- | --- |
| `:mobile` | phone app | `com.ferhatozcelik.mycodes` | Lets you collect codes (QR/barcode scans, gallery images or manual entries) and syncs them to the watch over the Wearable Data Layer. |
| `:wear` | Wear OS app | `com.ferhatozcelik.mycodeswear` | Receives the synced list and renders any entry as a QR code on the watch. |
| `:face` | watch face | `com.ferhatozcelik.qrcodeface` | A simple digital watch face demonstrating `CanvasWatchFaceService`. |

The phone and watch apps share data with `DataClient` from **Google Play services for
Wear OS**, using the `/data_path` data item. The phone APK bundles the watch APK via the
`wearApp` configuration, so installing the phone app also installs the watch app on a
paired device.

## Screenshots

_Add your own screenshots here._

## Architecture

```
wear-os-example/
├── mobile/   # :mobile — companion phone application (Java)
├── wear/     # :wear   — Wear OS application (Java)
│   └── src/main/java/com/ferhatozcelik/mycodeswear/
│       ├── MainActivity.java              # list of synced codes
│       ├── CodeDetails.java               # renders a QR code for one entry
│       └── ListRecyclerViewAdapter.java
└── face/     # :face   — watch face (Kotlin)
    └── src/main/java/com/ferhatozcelik/qrcodeface/WatchFace.kt
```

### Data flow

```
Mobile app  ──putDataItem("/data_path")──▶  Wearable Data Layer  ──▶  Wear app
   ▲                                                                     │
   └──────────────  DataClient.OnDataChangedListener ◀───────────────────┘
```

Code lists are persisted on both sides with `SharedPreferences` (Gson JSON) so they
survive process death and can be re-synced at any time.

## Requirements

| Tool | Version |
| --- | --- |
| Gradle | 8.14.5 |
| Android Gradle Plugin | 8.13.2 |
| Kotlin | 2.2.21 |
| compileSdk / targetSdk | 36 |
| minSdk | mobile 24 · wear 23 · face 30 |
| JDK | 17 |

## Building

```bash
./gradlew :mobile:assembleDebug
./gradlew :wear:assembleDebug
./gradlew :face:assembleDebug
```

To install the phone app and its bundled watch app on a paired device/emulator:

```bash
./gradlew :mobile:installDebug
```

## Notes

- The `:face` and `:wear` modules use the `com.google.android.support:wearable` APIs
  (`WearableActivity`, `CanvasWatchFaceService`). These are still AndroidX-based and work
  on current SDKs, but Google now recommends the Jetpack Watch Face library
  (`androidx.wear:wear-watchface`) for new watch faces.
- The project uses the JitPack repository for the `CarouselPicker` widget.

## Author

**Ferhat OZCELIK**

- GitHub: [@ferhatozcelik](https://github.com/ferhatozcelik)
- LinkedIn: [ferhatozcelik](https://www.linkedin.com/in/ferhatozcelik/)

## License

Apache License 2.0 — see [LICENSE](LICENSE).
