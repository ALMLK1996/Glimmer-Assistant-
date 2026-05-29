<p align="center">
  <img src="1000648650.png" alt="Glimmer AI - Happy State" width="260" style="margin-right: 20px;"/>
  <img src="1000648576.jpg" alt="Glimmer AI - Sad/Guardian State" width="260" />
</p>

<p align="center">
  <b>Active Companion States:</b> Ambient & Responsive Dynamic Expressions
</p>

# Glimmer AI 🌟

> **"Not just an AI assistant... but a living creature residing inside your phone."**
> — *“ليس مساعداً ذكياً… بل كائن يعيش داخل هاتفك.”*

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Platform: Android](https://img.shields.io/badge/Platform-Android-green.svg)]()
[![Privacy Focus](https://img.shields.io/badge/Privacy-Guaranteed-brightgreen)]()

**Glimmer** is an open-source, privacy-first Ambient AI Companion Layer for Android. It breaks the boundaries of traditional, isolated chat applications by introducing a living, breathing, and context-aware digital entity that exists across your entire mobile operating system. 

Unlike conventional AI tools that wait for user invocation, Glimmer lives alongside your daily digital workflow via an interactive screen overlay—learning your habits, managing tasks, and evolving emotionally as a true digital companion.

---

## ✨ Key Features

*   **🎙️ Ambient Overlay Companion:** A lightweight, non-intrusive overlay companion that moves freely on your screen, expressing emotions, speech bubbles, and real-time contextual interactions without breaking your workflow.
*   **🧠 Local & Hybrid Intelligence:** Optimized to run state-of-the-art small language models (SLMs) directly on-device for absolute privacy, with encrypted hybrid capabilities.
*   **💾 Long-Term Memory Engine:** A dedicated local memory system allowing the companion to remember past interactions, learn user-specific habits, and grow alongside you.
*   **🛡️ System Agent & Guard:** An automation engine capable of managing system tasks (opening apps, handling notifications, executing routines) while actively monitoring screen patterns to protect your digital wellbeing.
*   **🔌 Modular Plugin API:** Fully decentralized architecture allowing developers to build custom skills, automation triggers, and third-party tools.
*   **🎨 Open Skin Engine:** Supports user-generated aesthetic layouts, custom themes, and full visual customization of the companion's appearance.

---

## 📂 Project Structure

```text
Glimmer-ai/glimmer/
├── .gitignore                ← Prevents leaking build files & local keys
├── 📄 README.md              ← Main landing page (You are here)
├── 📄 CONTRIBUTING.md        ← How the community contributes
├── 📄 SECURITY.md            ← Security policy
├── 📄 CODE_OF_CONDUCT.md     ← Community code of conduct
├── 📄 LICENSE                ← GPL-3.0
├── 📄 CHANGELOG.md           ← Evolution and changelog tracking
├── 📁 app/                   ← Main Android code
│   ├── src/main/kotlin/
│   │   ├── core/             ← Shared utilities, DI (Koin/Hilt), & Theme setup
│   │   ├── overlay/          ← Overlay companion system (WindowManager)
│   │   ├── ai/               ← LLM integration & prompt engineering
│   │   ├── memory/           ← Long-term memory engine (Local Room/Vector DB)
│   │   ├── tasks/            ← Task execution & OS automation agent
│   │   ├── plugins/          ← Decentralized plugin architecture
│   │   └── guardian/         ← Digital wellbeing & screen-pattern monitor
│   └── src/test/             ← Unit & Integration Tests
├── 📁 docs/                  ← Full documentation
│   ├── architecture.md
│   ├── plugin-api.md
│   └── privacy-design.md
├── 📁 skins/                 ← Open-source skin templates & assets
│   ├── default/
│   └── spec.md
├── 📁 .github/
│   ├── workflows/
│   │   ├── build.yml         ← CI/CD (GitHub Actions)
│   │   └── release.yml       ← Automated release & APK signing
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── pull_request_template.md
└── 📁 grant/
    └── nlnet-proposal.md     ← Official proposal for NLnet funding

🛠️ Tech Stack & Architecture
​Language: 100% Kotlin
​UI Framework: Jetpack Compose (Modern, declarative UI components)
​Core Windowing: Advanced Android WindowManager APIs for persistent, high-performance overlay rendering.
​Local Inference: Hardware-accelerated local SLMs (ONNX Runtime / MediaPipe / Whisper).
​Local Storage: Encrypted SQLite / Room Database for the long-term memory engine.
​For a deep dive into our architectural design, please refer to docs/architecture.md.
​🚀 Getting Started (For Developers)
​Prerequisites
​Android Studio Jellyfish (or newer)
​Android SDK 34+
​JDK 17
​Installation & Build
​Clone the repository:




   git clone [https://github.com/Glimmer-ai/glimmer.git](https://github.com/Glimmer-ai/glimmer.git)
   cd glimmer




​1. Open the project in Android Studio.
2. ​Build the project using Gradle:


   ./gradlew assembleDebug
🤝 Contributing
​We stand for digital freedom, user sovereignty, and a truly open web. Check out our CONTRIBUTING.md to learn how you can report bugs, suggest features, or submit pull requests.
​📄 License
​Glimmer AI is a free software project, licensed under the GNU General Public License v3.0 (GPL-3.0). See the LICENSE file for more details.
