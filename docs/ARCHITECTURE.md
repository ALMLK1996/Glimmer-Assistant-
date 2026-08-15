# Glimmer Architecture — Light Being Core

## High-Level Vision

Glimmer is an **Ambient AI Companion Layer** that lives above the Android system as a pure Light Being.

```
┌─────────────────────────────────────────────────────────────┐
│                     Android System                          │
│  ┌───────────────────────────────────────────────────────┐  │
│  │              Light Being Overlay Layer                │  │
│  │  (Floating, morphing, context-aware light entity)     │  │
│  └───────────────────────────────────────────────────────┘  │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ AI Core     │  │ Memory &    │  │ System Control      │  │
│  │ (On-device  │  │ Personality │  │ (Accessibility +    │  │
│  │  + optional │  │ Evolution   │  │  Intents)           │  │
│  │  cloud)     │  │             │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

---

## Module Structure (Target)

```
:app
:core          → Domain models, use-cases, pure Kotlin
:data          → Room + DataStore + encrypted storage + WorkManager
:overlay       → WindowManager + AccessibilityService + Light Being renderer
:character     → Light Being engine (morphing, physics, performances)
:ai            → Local LLM abstraction + Gemini / on-device models
:system-control→ Accessibility actions + Intent router + permission flow
:voice         → SpeechRecognizer + TTS + audio focus
:memory        → Long-term memory + personality traits + manifestation
:ui            → Jetpack Compose settings, onboarding, transparency centers
```

---

## Key Living Systems

### 1. Screen Awareness
Observes current app + window state and feeds context into the Light Being’s behavior engine.

### 2. Idle Performance System
State machine that selects and escalates intentional light performances when the device is idle.

### 3. Memory Manifestation
Visual feedback system that briefly materializes symbolic light forms when recalling important memories.

### 4. Split & Merge
Ability to temporarily split the light entity into multiple threads for parallel work, then merge elegantly.

### 5. Presence Preference Learning
Continuous learning of the user’s preferred visual presence intensity and movement style.

---

## Rendering Strategy

- Primary: Jetpack Compose + Canvas for maximum compatibility and ease of development.
- Advanced path: Optional Filament / OpenGL ES layer for true volumetric light when device performance allows.
- Always prioritize battery and thermal safety.

---

## Compatibility Goal

Android 8.0 (API 26) → Android 16+ with progressive enhancement and graceful degradation.

---

## Privacy by Architecture

All personal data and personality evolution stay on-device by default.  
Any cloud enhancement is strictly opt-in, encrypted, and minimized.
