# FamilyTrust
## Sequenzdiagramm

```mermaid
sequenceDiagram
    participant User
    participant Frontend
    participant API
    participant DB
    User->>Frontend: Client makes a request to either record a stock purchase or a stock sell
    Frontend->>API: Send Request to backend.
    API->>DB: Query Data
    DB-->>API: Return Data
    API-->>User: Send Response
