# Engineering task: Red prayer-time values + animated next-prayer countdown

## Repository status
The personal repository originally contained only a README. The Android launcher source, resources, Gradle wrapper, tests, themes and supporting tools have now been imported from the MIT-licensed upstream repository `Pokka99/cleartv-Islamic` into this feature branch. The old prebuilt APK was intentionally excluded.

Importing the upstream source does **not** independently prove that it is byte-for-byte identical to the APK currently installed on the physical Mi TV Stick. Keep the existing device baseline untouched, and do not install or upgrade any APK until package identity, signing certificate and upgrade compatibility have been verified.

## Objective
Implement both UX enhancements throughout applicable prayer-time screens:
1. Render prayer-time values in red.
2. Animate the countdown to the next upcoming prayer.

## Mandatory safety and change-control rules
- Work only on branch `feat/red-prayer-times-animated-countdown`; do not commit directly to `main`.
- Inspect the full source tree, git status/history, existing theme/config behavior, Gradle setup, and tests before changes.
- Preserve existing user changes and current working behavior. Do not reset, overwrite, or delete configuration or cache data.
- Do not change launcher/Home handling, accessibility service, screensaver defaults, Wi-Fi/network, power settings, device packages, or unrelated system settings.
- Do not uninstall/reinstall on the TV, and do not install an APK automatically. Existing device APK signing certificate mismatch is a known risk; any device installation requires a separate explicit approval and a verified signing strategy.
- Preserve the active prayer zone and actual device configuration. Do not change `WLY01` / other configured zone values based on upstream defaults.
- Keep reduced-motion behavior: when `reduced_motion=true`, avoid continuous flashing/pulsing or unnecessary animation; use a stable countdown or subtle non-animated refresh while keeping values correct.
- Do not claim a test passed unless its command completed successfully and its output was captured.

## Implementation requirements
### A. Red prayer-time values
- Use a high-contrast red for the prayer-time *time values* across applicable screens, including the main home screen prayer row and Islamic screensaver prayer cards/values.
- Keep prayer names, current/next state semantics, and existing typography/layout unless an intentional minimal adjustment is needed.
- Check contrast/readability at 1080p TV viewing distance; do not recolor unrelated labels or status text.
- Retain the existing visual distinction for the next prayer. If existing theme configuration supports colors, follow its pattern; avoid hard-coding duplicate conflicting styles.

### B. Animated next-prayer countdown
- Display countdown to the nearest upcoming prayer as `HH:MM:SS` where the current UI presents a countdown.
- Update once per second while the relevant view is active, with lifecycle-safe scheduling (no duplicate runnables/timers after resume, no background leaks).
- Compute remaining time from absolute current time and target prayer time rather than decrementing an internal counter, so delayed frames, sleep/resume, or clock adjustment do not cause drift.
- Handle prayer-time boundaries, date rollover/midnight, cached schedule availability, and the period after Isyak according to the existing product semantics. Do not invent a next-day prayer schedule if it cannot be resolved.
- Ensure the displayed next-prayer name and countdown are refreshed when the next prayer changes.
- In reduced-motion mode, keep an accurate once-per-second text refresh if permitted but disable decorative animation/pulsing. If the setting means motion must be entirely suppressed, preserve correct non-animated display.
- Stop callbacks when Activity/screensaver is paused/destroyed and resume cleanly.
- Preserve existing azan behavior and scheduling; do not introduce duplicate notifications or change azan triggers.

## Testing requirements
Once actual source is present:
1. Extend existing unit tests (including PrayerTimes tests if present) for remaining-seconds formatting, next-prayer selection, exact prayer boundary, one second before/after boundary, midnight rollover, missing/invalid prayer data, and date changes.
2. Add lifecycle/scheduler tests where the existing test architecture allows it; otherwise provide a reproducible manual QA checklist and explain the limitation.
3. Run all repository-prescribed checks, at minimum:
   - `git diff --check`
   - Gradle unit tests for the app module (use the actual project wrapper/module names)
   - Android lint/static checks where configured
   - Debug/release build as applicable, without installing it on the TV
4. Review complete diff for unintended config changes, removed features, timer leaks, accessibility issues, or unrelated refactors.
5. Ensure existing screensaver, prayer schedule/cache, azan, launcher/Home, network/Wi-Fi and other previously working functions remain unchanged.

## Visual / device QA
- Provide a reproducible screenshot or emulator/device capture if permitted by the project workflow.
- Verify the main screen and Islamic screensaver at 1920x1080: red times are readable; next prayer is correct; countdown ticks smoothly and rolls over correctly.
- Do not perform TV key presses, screenshots, settings changes, package changes, or app installation unless the user explicitly approves that specific device action.
- A signing-certificate mismatch has occurred previously. Do not attempt an install unless package/signing identity and upgrade compatibility have been verified.

## Delivery
- Keep changes on this feature branch and open a pull request to `main` only after source has been imported, changes implemented, and checks run.
- Report exact commit/PR links, changed files, test commands and raw outcomes.
- Mark checks honestly as PASS / FAIL / BLOCKED / NOT RUN. At the current implementation revision, formatting checks, Debug/Release unit tests and Debug/Release builds have passed in GitHub Actions. Visual QA on a 1920x1080 emulator or the physical TV remains a separate pending step; no physical TV interaction or APK installation has been performed.
