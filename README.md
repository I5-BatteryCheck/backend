# I5 backend 파트입니다.

## 사용 기술
Framework : **Spring boot**
ORM : **Jpa**
Build : **Gradle**
RDBMS : **MySQL**
file upload and download : **AWS S3**
api docs : **Swagger**

## package 구조
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

## 도메인
Battery
Picture
Defect
Pulse
File
