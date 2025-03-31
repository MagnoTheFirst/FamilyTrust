# FamilyTrust
## Sequenzdiagramm

```mermaid
sequenceDiagram
    participant User
    participant API
    participant DB
    User->>API: Send Request
    API->>DB: Query Data
    DB-->>API: Return Data
    API-->>User: Send Response
