# Clear TV Islamic — Android TV launcher

Personal working repository for the Android TV launcher based on the MIT-licensed upstream project [Pokka99/cleartv-Islamic](https://github.com/Pokka99/cleartv-Islamic).

## Features

- Android TV home screen and app launcher
- JAKIM e-Solat prayer times and azan support
- Islamic screensaver with Hijri date and Quran verse
- Theme configuration and supporting tools

## Build and test

Run from the repository root:

```sh
cd launcher
./gradlew --no-daemon testDebugUnitTest assembleDebug
./gradlew --no-daemon testReleaseUnitTest assembleRelease
```

Build outputs are written under `launcher/app/build/outputs/apk/`. GitHub Actions runs formatting checks, unit tests, and Debug/Release builds. The workflow does not install an APK on a TV.

## Prayer theme

The checked-in `themes/islamik/config.json` is set to prayer zone `WLY01`, with `azan` and `reduced_motion` enabled. Review the local theme/device configuration before applying a theme; do not assume it should overwrite settings already working on a TV.

## Device safety

Builds are for verification only. Do not reinstall or upgrade the installed TV app until the APK signing identity has been verified against the installed package. No TV system settings, launcher mappings, Wi-Fi settings, accessibility services, or debloat packages should be changed as part of these UI enhancements.

## License

See [LICENSE](LICENSE).