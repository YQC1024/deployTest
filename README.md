# Store Signature Confirmation Demo

Minimal end-to-end deployment practice project:

- Scan QR code to open page
- Show agreement text
- Input name and phone
- Draw signature
- Submit
- View submission result

Tech stack:

- Frontend: Vue 3 + Vite
- Backend: Spring Boot 3
- Database: MySQL 8
- Deployment: Docker + Docker Compose + Nginx

Architecture:

```text
Browser
  -> Nginx
      -> static frontend
      -> /api proxy to Spring Boot
  -> Spring Boot
  -> MySQL
```

## Quick Start

```bash
docker compose up --build -d
```

Open:

- http://localhost
- http://localhost/api/health

Stop:

```bash
docker compose down
```

Reset database:

```bash
docker compose down -v
```

Example QR URL:

- http://localhost/?storeCode=STORE-001

## API

1. Submit

- `POST /api/signatures`

```json
{
  "storeCode": "STORE-001",
  "customerName": "Zhang San",
  "phone": "13800138000",
  "agreementVersion": "v1.0.0",
  "signatureDataUrl": "data:image/png;base64,..."
}
```

2. Detail

- `GET /api/signatures/{id}`

