# 💼 Custom Portfolio CMS (SaaS Enterprise Developer Platform)

[![React](https://img.shields.io/badge/React-19.0-61DAFB?style=for-the-badge&logo=react&logoColor=black)](https://react.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.x-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java 21](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-4.0-06B6D4?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
[![Vite](https://img.shields.io/badge/Vite-6.x-646CFF?style=for-the-badge&logo=vite&logoColor=white)](https://vite.dev/)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)

An enterprise-grade, full-stack Content Management System and developer portfolio showcase. This platform is custom-architected with a decoupled split-architecture: a high-performance **Java 21 & Spring Boot 3.x REST API** as the backend engine, paired with a fluid, responsive **React 19 + Vite + Tailwind CSS** client application.

Designed for modern software engineers, this platform replaces static JSON files or hardcoded HTML templates with an dynamic administrative console featuring authentic secure login sessions, granular CRUD operations, database persistence, and system-wide custom styling.

---

## 🗺️ Project Architecture & Data Flow

The system topology decouples the client application from the backend services. The React client is pre-compiled and served via high-speed global Edge CDNs, while the Spring Boot REST API handles state management, JWT validation, and transactional communications with the relational MySQL cluster.

### 🏗️ Split-Architecture Topology

```
                  +-----------------------------------------+
                  |           Client Browser                |
                  +--------------------+--------------------+
                                       |
                    Public Portfolio   |   Admin JWT Operations
                    HTTP & Static      |   & Content Management
                                       v
            +--------------------------+--------------------------+
            |                    Vercel Edge CDN                  |
            |  (Serves Pre-compiled React 19 + Vite Static Assets)|
            +--------------------------+--------------------------+
                                       |
                                       |  Proxies /api/* requests
                                       |  (Configured in vercel.json)
                                       v
            +-----------------------------------------------------+
            |                     Render PaaS                     |
            |     (Spring Boot 3 REST API in JRE 21 Container)    |
            +--------------------------+--------------------------+
                                       |
                                       |  Spring Data JPA & JDBC
                                       |  (MySQL Connection Pool)
                                       v
            +-----------------------------------------------------+
            |                 Cloud MySQL Instance                |
            |         (Hosted on Aiven / TiDB / Clever Cloud)     |
            +-----------------------------------------------------+
```

### 🔁 Clean Path Routing & Network Proxying
1. **Frontend Request Dispatch**: The React single-page application initiates fetches using standard relative routes (e.g., `fetch('/api/projects')`).
2. **Dynamic Reverse Proxy**: In development, Vite redirects these API requests to the running backend application. In production, Vercel Core Routing handles the path matching cleanly without manual origin headers, solving cross-origin resource sharing (CORS) preflight blocks and ensuring native browser cookie transmission.
3. **Database Connectivity**: The Java Spring Boot container integrates a highly-performant connection pool (HikariCP) that automatically scales active database connections on demand.

---

## ✨ Features List

### 🔒 Enterprise Security & Administration
*   **Role-Based Access Control (RBAC)**: Fine-grained administrator authorization limits endpoint queries.
*   **JWT Token Architecture**: Uses cryptographically signed (HS512), short-lived JSON Web Tokens paired with a rotating DB-stored refresh token mechanism for secure session survival.
*   **Security Logs & Audits**: The administration suite captures user authentication location telemetry, active operating systems, and modification timestamps for real-time compliance review.

### 📊 Comprehensive Content & Media Managers (CRUD)
*   **Interactive Bento Grid (Projects CRUD)**: Create, read, update, and delete active projects. Toggle project visibility, set featured project priorities, and tag technologies dynamically.
*   **Skill Matrix Layout**: Manage technical disciplines and proficiencies across customizable groupings (Frontend, Backend, DevOps, Mobile).
*   **Work History & Education Planners**: Record corporate histories, collegiate milestones, GPAs, and dates.
*   **Certification Registry**: Capture verification links, issuing authorities, credentials, and verification logos.
*   **Resume Center & Document Manager**: Supports direct PDF file uploads, historical document archival, rollbacks, and active CV selection.

### 📈 Modern UI/UX & Native Telemetry
*   **Polished Adaptive Designs**: Fully mobile-responsive interface utilizing Tailwind CSS, sleek hover states, glassmorphism card highlights, and lightweight entrance animations.
*   **Zero-Dependency Analytics**: Proprietary real-time visitor tracker capturing page paths, unique IP geolocation insights (Country, City, Client OS), referrers, and duration benchmarks without invading cookies or breaching privacy rules.
*   **Dynamic Theme Overrides**: Modify CSS styles, brand layouts, copyrights, and contact descriptions globally with a single click inside the settings panel.

---

## 📸 Screenshots & UI Walkthrough

| UI Panel | Description | Viewport Mockup |
| :--- | :--- | :--- |
| **Public Developer Bento Grid** | Minimalist high-contrast dark layout highlighting interactive skills, featured systems, and experiences. | `[ [===] [=] [=] ]` |
| **Admin Control Dashboard** | Tabbed analytics dashboard summarizing message lists, visitor metrics, and active session history. | `[ Menu ┃ Analytics [📊] ]` |
| **Content CRUD Editors** | Drag-and-drop file uploaders, modal text editors, tag organizers, and real-time form validation. | `[ Title: [____] Tag: [+] ]` |

---

## 🛠️ Complete Local Installation & Configuration

Follow these step-by-step instructions to configure and execute the application locally.

### 📋 Prerequisites
*   **Java Development Kit (JDK) 21** (e.g., OpenJDK, Amazon Corretto).
*   **Node.js 18+** with NPM.
*   **MySQL Server 8.0+** (running locally or in Docker).

---

### ☕ 1. Configure the Spring Boot 3 Backend

1.  **Change directory** into the backend suite:
    ```bash
    cd backend
    ```
2.  **Establish a local database schema**:
    Open your MySQL administrative CLI and run:
    ```sql
    CREATE DATABASE portfolio_cms;
    ```
3.  **Adjust your Environment Configurations**:
    Open `src/main/resources/application.properties` and map your database connection credentials:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/portfolio_cms?serverTimezone=UTC&useSSL=false
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    spring.jpa.hibernate.ddl-auto=update
    ```
4.  **Boot the backend server**:
    ```bash
    ./mvnw spring-boot:run
    ```
    *The Java Spring Boot API will run and bind to `http://localhost:8080`.*

---

### ⚛️ 2. Configure the React Frontend Client

1.  **Return to the workspace root**:
    ```bash
    cd ..
    ```
2.  **Install the client-side dependencies**:
    ```bash
    npm install
    ```
3.  **Setup the environment variables**:
    Create a local `.env` configuration file in your root folder:
    ```env
    VITE_API_URL=http://localhost:8080
    ```
4.  **Launch the development client**:
    ```bash
    npm run dev
    ```
    *The Vite React development server will start on port `3000` (e.g., `http://localhost:3000`). Accessing this link will serve the hot-reloading client pointing directly to your local Java API.*

---

## 🔌 API Documentation Interface

All administrative requests (POST, PUT, DELETE, PATCH) are guarded and require the JWT Token inside the request headers:
`Authorization: Bearer <your_jwt_access_token>`

### 🔑 Auth Controller Enpoints
*   `POST /api/auth/login` - Authenticates user credentials. Returns JWT Token, expiration limits, and refresh token IDs.
*   `POST /api/auth/refresh` - Accepts a valid, non-expired refresh token to dynamically regenerate a new JWT access key.
*   `POST /api/auth/register` - Custom setup endpoint for creating the primary administrator record.

### 🎨 Portfolio Content Enpoints
All content modules (Projects, Experience, Skills, Education, Certificates, Achievements) implement clean RESTful CRUD layouts.
*   `GET /api/projects` - Fetches all projects, sorted by custom weighting parameters. (No Auth)
*   `POST /api/projects` - Persists a new project entry to the relational database.
*   `PUT /api/projects/{id}` - Updates properties of the target project record.
*   `DELETE /api/projects/{id}` - Purges the selected project entry.
*   `PATCH /api/projects/order` - Reorders display weights via batch payloads.

### 📬 Message Center Endpoints
*   `POST /api/messages` - Captures portfolio contact form submissions. (No Auth)
*   `GET /api/messages` - Returns all inbox message threads sorted by date.
*   `PATCH /api/messages/{id}/read` - Toggles read status indicators.
*   `DELETE /api/messages/{id}` - Purges a message from the database.

---

## 🗄️ Database Entity Schema

The MySQL relational database uses the following fields in its core structure:

```
  admins: {
    id: INT (PK, AUTO_INC),
    username: VARCHAR(50) (UNIQUE),
    password: VARCHAR(255) (BCRYPT_HASH),
    email: VARCHAR(100) (UNIQUE),
    role: VARCHAR(20)
  }

  projects: {
    id: INT (PK, AUTO_INC),
    title: VARCHAR(100),
    description: TEXT,
    imageUrl: VARCHAR(255),
    demoUrl: VARCHAR(255),
    githubUrl: VARCHAR(255),
    tags: VARCHAR(255),
    displayOrder: INT,
    isVisible: BOOLEAN,
    featured: BOOLEAN
  }

  visitor: {
    id: INT (PK, AUTO_INC),
    ipAddress: VARCHAR(45),
    country: VARCHAR(100),
    city: VARCHAR(100),
    userAgent: VARCHAR(255),
    createdAt: TIMESTAMP
  }
```

---

## 🔮 Future Architecture Improvements
*   **AWS S3 / Cloudinary File Upload Integration**: Move base64 document and media persistence to decentralized cloud storage providers to reduce MySQL storage footprint.
*   **Two-Factor Authentication (2FA)**: Integrate time-based OTP (TOTP) protocols using authenticator apps to maximize secure administrative controls.
*   **Server-Side Rendering (SSR)**: Port the public portfolio layout to Next.js or Spring Boot Thymeleaf engine to maximize SEO crawlers indexing velocity.

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

## 👥 Contributing Guide

Contributions are welcome! Please follow these standards:
1.  **Fork** the repository.
2.  Create your feature branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your modifications following conventional commit messages (`git commit -m 'feat: add interactive canvas animations'`).
4.  Push your updates (`git push origin feature/AmazingFeature`).
5.  Open a **Pull Request** detailing your architectural updates.
