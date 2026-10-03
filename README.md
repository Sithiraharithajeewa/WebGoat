# IE3142 DevSecOps: WebGoat Security Lab

This repository contains the project work for the IE3142 DevSecOps assignment, using [OWASP WebGoat](https://github.com/WebGoat/WebGoat) v2026.4 as its intentionally vulnerable training application.

The project combines a reproducible WebGoat environment with security analysis, remediation evidence, and automated continuous integration (CI) checks.

## Contents

- [Project Scope](#project-scope)
- [Repository Layout](#repository-layout)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Continuous Integration](#continuous-integration)
- [Project Documentation](#project-documentation)
- [Security Notice](#security-notice)
- [License](#license)

## Project Scope

- Build and run WebGoat and WebWolf using Docker Compose.
- Analyze and document selected security issues, including SQL injection, server-side request forgery (SSRF), stored cross-site scripting (XSS), and path traversal.
- Preserve before-and-after evidence and scan results under `evidence/`.
- Run build and security checks through GitHub Actions.

## Repository Layout

| Path | Description |
| --- | --- |
| `application/WebGoat/` | WebGoat v2026.4 source code and Maven wrapper |
| `docker-compose.yml` | Local Compose service definition for WebGoat and WebWolf |
| `.github/workflows/ci.yml` | Build, test, and security scanning workflow |
| `evidence/` | Assignment evidence, scan output, and remediation artifacts |
| `project-notes/` | Application selection, threat model, and evidence log |

## Prerequisites

- Java Development Kit (JDK) 25 to build or run from source
- Docker Desktop with Docker Compose v2 to run the containerized application
- Git to clone or manage the repository

## Getting Started

### Run with Docker Compose

The Dockerfile uses the packaged JAR from `application/WebGoat/target`. Build the application first, then start the containers. Run the following commands from the repository root.

**Windows (PowerShell)**

```powershell
cd application/WebGoat
./mvnw.cmd -B clean package -DskipITs "-P!start-server"
cd ../..
docker compose up --build
```

**Linux or macOS**

```sh
cd application/WebGoat
./mvnw -B clean package -DskipITs '-P!start-server'
cd ../..
docker compose up --build
```

### Access the Applications

Once the service is healthy, open:

| Application | Local URL |
| --- | --- |
| WebGoat | <http://localhost:8080/WebGoat> |
| WebWolf | <http://localhost:9090/WebWolf> |

### Manage the Containers

Useful Compose commands, run from the repository root:

```sh
docker compose ps
docker compose logs -f webgoat
docker compose down
```

### Run from Source

With JDK 25 installed, run the following from `application/WebGoat`.

**Windows (PowerShell)**

```powershell
./mvnw.cmd spring-boot:run
```

**Linux or macOS**

```sh
./mvnw spring-boot:run
```

WebGoat is available at <http://localhost:8080/WebGoat>; WebWolf is available at <http://localhost:9090/WebWolf>.

## Continuous Integration

The workflow in `.github/workflows/ci.yml` runs on pushes and pull requests targeting `main`. It includes:

- Gitleaks secret scanning
- Semgrep Java SAST, including the project SSRF rule
- Trivy filesystem dependency scanning
- Maven build and tests with JDK 25
- Docker image build and Trivy image scanning

Review the workflow results alongside the detailed artifacts in `evidence/`.

## Project Documentation

- [Application selection](project-notes/application-selection.md): rationale for choosing WebGoat.
- [Threat model](project-notes/threat-model.md): documented threats and security considerations.
- [Evidence log](project-notes/evidence-log.md): record of supporting project evidence.
- [Evidence directory](evidence/): scan results and remediation artifacts.

## Security Notice

WebGoat is deliberately vulnerable and is intended only for authorized security education in a controlled lab. The Compose file publishes ports `8080` and `9090` on all host interfaces. Do not run this setup on an untrusted network or expose it to the public internet. Stop the services with `docker compose down` when finished.

## License

WebGoat is distributed under the GNU General Public License v2. Refer to [`application/WebGoat/LICENSE.txt`](application/WebGoat/LICENSE.txt) for the upstream license. Assignment notes and evidence are provided for educational use.
