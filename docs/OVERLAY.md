# Overlay Layer

The Light Being lives as a system-level overlay. This is the most delicate part of the project.

## Goals

- Stay above all apps without breaking gesture navigation or the keyboard
- Appear and disappear cleanly
- Survive process death and configuration changes
- Work from Android 8 upward with as few special cases as possible

## Current Approach

We use a combination of:

- `SYSTEM_ALERT_WINDOW` for the floating view
- Accessibility Service for reliable context and limited system actions
- A foreground service with a low-priority notification so the system does not kill us aggressively

The visual form itself is rendered with Compose + Canvas for now. A heavier rendering path (Filament or custom GL) can be added later for devices that can handle it.

## Appearance Rules

These are non-negotiable product rules:

1. When the user is actively using the phone, the Light Being stays hidden.
2. When the user starts speaking or gives a clear command, it materializes.
3. The moment the user touches the screen again to browse, it fades out within roughly half a second.
4. Voice continues even after the visual form is gone.
5. On true idle, it may come back and perform light sequences.

## Things to Watch

- Battery drain from constant window updates
- Conflicts with other overlay apps
- Behavior on foldables and large screens
- How different manufacturers handle `TYPE_APPLICATION_OVERLAY`

This file will grow as we hit real devices and edge cases.
