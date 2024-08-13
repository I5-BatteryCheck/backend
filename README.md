# I5 Backend 파트입니다.

## 사용 기술
- **Framework**: Spring Boot
- **ORM**: JPA
- **Build Tool**: Gradle
- **RDBMS**: MySQL
- **File Upload and Download**: AWS S3
- **API Documentation**: Swagger

## package 구조

```plaintext
domain/
├── Battery
│   ├── controller
│   ├── entity
│   ├── repository
│   ├── dto
│   └── service
├── Picture
│   ├── controller
│   ├── entity
│   ├── repository
│   ├── dto
│   └── service
├── Defect
│   ├── controller
│   ├── entity
│   ├── repository
│   ├── dto
│   └── service
└── Pulse
    ├── controller
    ├── entity
    ├── repository
    ├── dto
    └── service

global/
├── common
│   └── file/
│       ├── controller
│       ├── entity
│       ├── repository
│       ├── dto
│       └── service
├── config
└── util
```

## 도메인

- **Battery**
- **Picture**
- **Defect**
- **Pulse**
- **File**
