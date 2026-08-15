# Decision Log

Short record of decisions that matter so we do not revisit them every few weeks.

## 2026-08 — Single Light Being

We dropped the multi-character / multi-soul system completely.

Reason: it was diluting the core idea. One living light entity is clearer, more distinctive, and harder to get right. Better to do one thing extremely well.

The Light Being has no fixed face, no fixed color, and no “cute” personality. It should feel intelligent and a little mysterious.

## Appearance rules

The being must not stay on screen while the user is browsing. This is a product rule, not a preference.

If the visual form becomes noise, people will turn the whole thing off.

## Rendering

Start with Compose + Canvas. Revisit Filament or custom OpenGL only if the visual quality on mid-range devices is not good enough.

## Privacy

On-device first. Any cloud feature is opt-in and must be justified. This is not negotiable.

## Scope

We are not building another chatbot with a floating bubble. The ambient, always-present-but-not-annoying nature is the whole point.
