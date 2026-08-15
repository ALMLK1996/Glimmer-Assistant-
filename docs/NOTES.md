# Working Notes

Random things that came up while building. Not polished documentation, just notes so we do not forget.

## Rendering

Compose Canvas is fine for the first version. Soft glow is expensive if done naively. Need to be careful with full-screen invalidation.

On mid-range devices we should drop to a simpler shape and lower frame rate when the being is only floating in idle mode.

## Idle behavior

The performances should feel intentional, not random. Better to have a small set of well-crafted sequences than a lot of noise.

Long idle sequences can escalate, but they must cut instantly when the user touches the screen.

## Memory manifestation

Keep this subtle. If it becomes obvious or cartoonish it will ruin the whole feeling of the Light Being.

## Presence learning

Start very conservative. Most people will prefer less visual presence than we think. Make it easy to turn the idle performances down or off.

## Permissions

The onboarding flow for overlay + accessibility needs to be extremely clear. A lot of users get scared by accessibility permission screens. Explain why we need it in plain language.

## Battery

Measure early. Overlay + continuous light animation can get expensive fast. WorkManager for anything that is not real-time.
