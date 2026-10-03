# IE3142 DevOps Security – STRIDE Threat Model

## Selected Application

Application: OWASP WebGoat v2026.4

Technology: Java / Spring Boot

Deployment: Docker container

Primary components:
- WebGoat web application
- Embedded HSQLDB database
- WebGoat/WebWolf functionality within the application

## Trust Boundaries

TB-01: External user/browser -> WebGoat application

TB-02: WebGoat application -> embedded database

## STRIDE Threats

### T01 – Authentication Spoofing

STRIDE Category: Spoofing

Threat:
An attacker may attempt to bypass or abuse WebGoat authentication mechanisms to
impersonate another user or access an authenticated account.

Affected component:
WebGoat authentication/session functionality.

Likelihood: High (3)

Impact: High (3)

Risk Score: 9

Justification:
WebGoat contains authenticated users and protected lesson functionality. A weakness
in authentication or session handling could allow an attacker to act as another user.

Planned Control:
Strong authentication and secure session handling.

Control Location:
To be confirmed after source-code inspection.

---

### T02 – Database Tampering / Injection

STRIDE Category: Tampering

Threat:
An attacker may provide malicious input to a vulnerable database operation and
manipulate the resulting query or database interaction.

Affected component:
WebGoat lesson/application database functionality.

Likelihood: High (3)

Impact: High (3)

Risk Score: 9

Justification:
WebGoat intentionally contains SQL-injection training scenarios and uses HSQLDB.
Unsafe database handling could allow attacker-controlled input to alter query
behaviour or access/modify unauthorized data.

Planned Control:
Parameterized queries and safe database input handling.

Control Location:
To be confirmed after source-code inspection.

---

### T03 – Information Disclosure

STRIDE Category: Information Disclosure

Threat:
An attacker may exploit vulnerable application functionality or logging behaviour
to obtain credentials, sensitive application information, database information,
or other data that should not be exposed.

Affected component:
WebGoat application and lesson functionality.

Likelihood: High (3)

Impact: High (3)

Risk Score: 9

Justification:
The application intentionally contains security lessons involving information
exposure and logging. Sensitive information exposed through these functions could
be disclosed to an unauthorized user.

Planned Control:
Sensitive-data protection, secure logging, output handling and removal of
credential disclosure.

Control Location:
To be confirmed after source-code inspection.

---

### T04 – Privilege Elevation

STRIDE Category: Elevation of Privilege

Threat:
A low-privileged or normal WebGoat user may attempt to access functionality or
resources intended for an administrator or another privileged role.

Affected component:
WebGoat authorization and administrative functionality.

Likelihood: Medium (2)

Impact: High (3)

Risk Score: 6

Justification:
WebGoat contains authenticated users and administrative functionality. An
authorization weakness could allow a user to perform actions beyond their
intended privileges.

Planned Control:
Server-side authorization and role-based access control.

Control Location:
To be confirmed after source-code inspection.

---

## Risk Summary

| ID | STRIDE | Likelihood | Impact | Risk |
|----|--------|------------|--------|------|
| T01 | Spoofing | High (3) | High (3) | 9 |
| T02 | Tampering | High (3) | High (3) | 9 |
| T03 | Information Disclosure | High (3) | High (3) | 9 |
| T04 | Elevation of Privilege | Medium (2) | High (3) | 6 |

## Note

The threats above are initial application-specific threat hypotheses.
The affected source-code locations and final controls will be confirmed during
the subsequent source-code and vulnerability analysis.
