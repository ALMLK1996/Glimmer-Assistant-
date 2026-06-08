# 🛡️ Glimmer Security Standards

**Version:** 1.0-draft  
**Last Updated:** 2025  
**Security Contact:** security@glimmer.app *(placeholder)*

---

> *"Security is not a feature you add. It is a discipline you practice every day."*

---

## 1. Security Architecture

### Encryption Standards

| Layer | Standard | Notes |
|-------|----------|-------|
| Data at rest | AES-256-GCM | Android Keystore / iOS Secure Enclave |
| Data in transit | TLS 1.3 | TLS 1.0 and 1.1 explicitly disabled |
| Key exchange | X25519 / P-256 | Perfect Forward Secrecy enforced |
| Network policy | HSTS | Min. 1 year; preload list submission planned |
| Certificate pinning | Yes | Applied to all Glimmer API endpoints |
| Local storage | Encrypted SharedPreferences / Keychain | No plaintext sensitive data on device |
| Heritage audio | AES-256 with user-derived key | User key never transmitted |
| Soul files | AES-256 + HMAC-SHA256 | Integrity verified on every load |

### Authentication

| Method | Standard |
|--------|----------|
| Primary authentication | Passkeys (WebAuthn Level 2) |
| Fallback authentication | OAuth 2.1 + PKCE |
| Identity layer | OpenID Connect |
| Session tokens | Short-lived JWTs (15 min) + refresh tokens |
| Session management | Device-bound sessions; cross-device revocation |
| Multi-factor | TOTP + hardware key support |

---

## 2. Network Security

### Requirements

- All API endpoints require HTTPS — no HTTP fallback, ever
- Certificate pinning on all Glimmer-operated endpoints
- HSTS with minimum 1-year max-age
- Content Security Policy (CSP) headers enforced
- CORS configured for Glimmer domains only
- Rate limiting on all endpoints
- DDoS protection at infrastructure level
- API inputs validated and sanitized before processing
- SQL injection prevention via parameterized queries only
- Output encoding to prevent XSS

### Monitoring

- Real-time anomaly detection on API traffic
- Authentication failure alerting
- Unusual data access pattern detection
- Geographic anomaly detection for account access

---

## 3. Mobile App Security

### Android

| Control | Implementation |
|---------|---------------|
| Code obfuscation | R8 / ProGuard with aggressive rules |
| Root detection | SafetyNet Attestation / Play Integrity API |
| Emulator detection | Runtime environment checks |
| Debugger detection | Anti-debugging measures in release builds |
| Memory protection | Sensitive data zeroed from memory after use |
| Backup prevention | `android:allowBackup="false"` for sensitive components |
| Screenshot prevention | `FLAG_SECURE` on sensitive screens |
| Deep link validation | Intent validation; no unvalidated redirects |
| Exported components | Minimal; all protected with permissions |

### iOS

| Control | Implementation |
|---------|---------------|
| Jailbreak detection | Integrity checks at runtime |
| Data Protection | Complete protection class for sensitive files |
| Keychain | `kSecAttrAccessibleWhenUnlockedThisDeviceOnly` |
| App Transport Security | Enforced; no exceptions |
| Memory protection | Sensitive data cleared from memory after use |
| Screenshot prevention | Overlay on sensitive screens |
| URL scheme validation | All incoming URLs validated |
| Clipboard access | Only when user explicitly requests it |

---

## 4. Data Security

### Classification

| Classification | Examples | Protection Level |
|---------------|----------|-----------------|
| Critical | Encryption keys · Authentication credentials | Highest — never logged, never transmitted |
| Sensitive | Conversation history · Health data · Heritage audio | AES-256 at rest; E2E where applicable |
| Personal | Language preference · Usage patterns | Encrypted; minimal retention |
| Operational | Crash logs · Error reports | Anonymized; 30-day retention |
| Public | Soul files (public) · Open documentation | No special protection required |

### Retention & Deletion

| Data Type | Retention Period | Deletion Method |
|-----------|-----------------|-----------------|
| Conversation history | User-controlled | Secure deletion (multi-pass) |
| Crash logs | 30 days | Automatic purge |
| Auth tokens | Session duration | Invalidation + deletion |
| Heritage audio | User-controlled | Secure deletion |
| Analytics (opted-in) | 90 days rolling | Automatic purge |
| Account data | Account lifetime | Complete deletion within 30 days of request |

---

## 5. Vulnerability Management

### Vulnerability Disclosure Policy

Glimmer welcomes responsible security research.

**Scope:**
- Glimmer Android and iOS applications
- Glimmer API endpoints
- Glimmer web infrastructure

**How to Report:**
1. Email: security@glimmer.app *(placeholder)*
2. Encrypt with our PGP key *(will be published in SECURITY.md)*
3. Include: description, reproduction steps, impact assessment
4. Optionally: suggested fix

**Our Commitments:**
- Acknowledge receipt within 48 hours
- Provide a status update within 7 days
- Resolve critical vulnerabilities within 30 days
- Credit researchers in our Security Hall of Fame (with permission)
- No legal action against good-faith researchers

**Out of Scope:**
- Social engineering attacks
- Physical device attacks
- Third-party service vulnerabilities

### Patch Response Times

| Severity | Description | Target Resolution |
|----------|-------------|------------------|
| Critical | Active exploitation; user data at immediate risk | 24 hours |
| High | Significant security impact; exploitation likely | 7 days |
| Medium | Moderate impact; exploitation requires conditions | 30 days |
| Low | Minor impact; limited exploitability | 90 days |

---

## 6. Audit Schedule

| Activity | Frequency | Performed By |
|----------|-----------|-------------|
| Automated dependency scanning | Daily | GitHub Dependabot + OWASP Dependency-Check |
| SAST (Static Application Security Testing) | Every pull request | CodeQL + Semgrep |
| DAST (Dynamic Application Security Testing) | Monthly | OWASP ZAP |
| Penetration test — mobile apps | Annually | Independent qualified firm |
| Penetration test — infrastructure | Annually | Independent qualified firm |
| Code security review | Per major release | Internal + community |
| Third-party library audit | Quarterly | Internal |
| Compliance audit | Annually | Independent qualified legal + security firm |

---

## 7. Incident Response

### Severity Classification

| Level | Criteria | Response Time |
|-------|----------|--------------|
| P0 — Critical | Active breach; data exposure; service down | Immediate (24/7) |
| P1 — High | Significant vulnerability; potential exposure | 4 hours (business) |
| P2 — Medium | Limited exposure; no active exploitation | 24 hours |
| P3 — Low | Minor issue; no data at risk | 72 hours |

### Breach Notification

In the event of a confirmed data breach:

1. **Immediate:** Contain the breach and stop ongoing exposure
2. **Within 24 hours:** Internal assessment and escalation
3. **Within 72 hours:** Notify applicable supervisory authorities (GDPR requirement applied globally)
4. **As soon as reasonably possible:** Notify affected users
5. **Within 30 days:** Full incident report published (redacted where legally required)

Notification to users will include:
- What happened
- What data was affected
- What we have done to fix it
- What users should do
- Who to contact with questions

---

## 8. Supply Chain Security

- All dependencies scanned for known vulnerabilities before inclusion
- Prefer actively maintained libraries with security track records
- SBOM (Software Bill of Materials) published with each release
- Reproducible builds: APK/IPA hashes published for verification
- Signed releases: All releases signed with developer keys

---

*Security is a continuous practice, not a destination. This document will be updated to reflect new threats, new standards, and lessons learned.*
