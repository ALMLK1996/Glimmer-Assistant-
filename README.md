<table align="center" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="middle">
      <img src="1000648650.png" width="210" alt="Glimmer AI - Happy State" />
    </td>
    <td width="30"></td>
    <td align="center" valign="middle">
      <img src="1000648576.jpg" width="210" alt="Glimmer AI - Guardian State" />
    </td>
  </tr>
  <tr>
    <td colspan="3" align="center">
      <br />
      <b>Active Companion States:</b> Ambient & responsive dynamic expressions that evolve with your workflow.
    </td>
  </tr>
</table>

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

*   **🎙️ Ambient Overlay Companion:** A lightweight, non-intrusive screen overlay system that moves freely, expressing real-time contextual interactions, speech bubbles, and emotional feedback.
*   **🧠 Local & Hybrid LLM Engine:** Optimized to run state-of-the-art small language models (SLMs) directly on-device for 100% offline privacy, with optional encrypted hybrid capabilities.
*   **💾 Long-Term Memory:** Built-in secure local vector/relational storage, allowing the companion to remember past conversations, adapt to user-specific habits, and grow over time.
*   **🛡️ System Agent & Guard:** A robust automation engine capable of orchestrating system tasks (opening apps, executing routines, handling notifications) while protecting your digital wellbeing by monitoring screen patterns.
*   **🔌 Modular Plugin Architecture:** Fully decentralized system allowing third-party developers to build custom skills, automation triggers, and extended capabilities seamlessly.
*   **🎨 Open Skin Engine:** Supports user-generated aesthetic layouts, custom themes, and complete visual transformation of the companion's appearance.

---

## 📂 Project Structure

```text
Glimmer-ai/glimmer/
├── .gitignore                ← Prevents leaking build files & local environment keys
├── 📄 README.md              ← Main landing page (You are here)
├── 📄 CONTRIBUTING.md        ← Comprehensive community contribution guide
├── 📄 SECURITY.md            ← Security and vulnerability reporting policy
├── 📄 CODE_OF_CONDUCT.md     ← Community code of conduct
├── 📄 LICENSE                ← GPL-3.0 Open Source License
├── 📄 CHANGELOG.md           ← Detailed release and version evolution tracking
├── 📁 app/                   ← Core Android Application Source
│   ├── src/main/kotlin/
│   │   ├── core/             ← Shared utilities, Dependency Injection (Koin/Hilt), & Theme base
│   │   ├── overlay/          ← Overlay companion window management system (WindowManager)
│   │   ├── ai/               ← LLM processing, local inference, & prompt engineering
│   │   ├── memory/           ← Long-term local memory database (Room / Encrypted SQLite)
│   │   ├── tasks/            ← Background execution engine & OS automation agent
│   │   ├── plugins/          ← Decoupled extensible plugin interface
│   │   └── guardian/         ← Digital wellbeing, usage analyzer, & guard patterns
│   └── src/test/             ← Unit and Integration test suites
├── 📁 docs/                  ← Full technical documentation
│   ├── architecture.md       ← Deep dive into subsystem data flows
│   ├── plugin-api.md         ← Developer guide for creating Glimmer plugins
│   └── privacy-design.md     ← Data encryption and privacy-by-design proof
├── 📁 skins/                 ← Asset packages & specifications for visual models
│   ├── default/              ← Default ghost companion asset configurations
│   └── spec.md               ← Technical guidelines for rendering custom skins
├── 📁 .github/               ← GitHub infrastructure and CI/CD pipelines
│   └── workflows/
│       ├── build.yml         ← Continuous Integration automated build checks
│       └── release.yml       ← Automated CD deployment & production APK signing
└── 📁 grant/
    └── nlnet-proposal.md     ← Official grant application blueprint for NLnet funding
🛠️ Tech Stack & Subsystems
​Language: 100% Modern Kotlin.
​UI Framework: Jetpack Compose (Declarative, reactive UI components with custom Neumorphic / Hand-drawn rendering layers).
​Window Management: Advanced Android WindowManager layout architectures for persistent, high-performance, memory-leak-safe overlays.
​Local Inference: Hardware-accelerated on-device AI integration via ONNX Runtime / MediaPipe LLM Inference / Whisper.
​Data Persistence: Encrypted Room Database for secure contextual logging and analytical habit modeling.
​For comprehensive architectural flows, please refer to docs/architecture.md.
​🚀 Getting Started (For Developers)
​Prerequisites
​Android Studio Jellyfish (or newer)
​Android SDK 34+
​JDK 17
​Build & Run
1. ​Clone the project locally:
   git clone [https://github.com/Glimmer-ai/glimmer.git](https://github.com/Glimmer-ai/glimmer.git)
   cd glimmer



2. ​Open the project folder within Android Studio.
3. ​Synchronize Gradle and execute a debug build:

🤝 Contributing & Community
​We stand firmly for digital freedom, user sovereignty, and a truly open web. Check out our CONTRIBUTING.md to learn how you can submit bug reports, suggest feature requests, or contribute code to Glimmer's core layer.
​📄 License
​Glimmer AI is free software, distributed under the GNU General Public License v3.0 (GPL-3.0). We respect your digital rights. See the LICENSE file for comprehensive legal details.


