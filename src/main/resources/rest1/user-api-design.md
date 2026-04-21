# User API 설계 문서

## 개요

| 항목 | 내용 |
|------|------|
| 기본 URL | `/api/rest1/users` |
| 데이터 형식 | JSON |
| 인증 | 없음 |

---

## 리소스 구조

### User

| 필드 | 타입 | 설명 | 필수 |
|------|------|------|------|
| id | Long | 고유 식별자 (자동 생성) | - |
| username | String | 사용자 이름 (고유) | O |
| email | String | 이메일 주소 | O |
| password | String | 비밀번호 | O |

---

## API 목록

### 1. 사용자 생성

| 항목 | 내용 |
|------|------|
| 메서드 | POST |
| URI | `/api/rest1/users` |

**요청**
```json
{
  "username": "hong",
  "email": "hong@example.com",
  "password": "1234"
}
```

**응답 (201 Created)**
```json
{
  "id": 1,
  "username": "hong",
  "email": "hong@example.com"
}
```

---

### 2. 전체 사용자 조회

| 항목 | 내용 |
|------|------|
| 메서드 | GET |
| URI | `/api/rest1/users` |

**응답 (200 OK)**
```json
[
  {
    "id": 1,
    "username": "hong",
    "email": "hong@example.com"
  },
  {
    "id": 2,
    "username": "kim",
    "email": "kim@example.com"
  }
]
```

---

### 3. 단건 사용자 조회

| 항목 | 내용 |
|------|------|
| 메서드 | GET |
| URI | `/api/rest1/users/{id}` |

**응답 (200 OK)**
```json
{
  "id": 1,
  "username": "hong",
  "email": "hong@example.com"
}
```

---

### 4. 사용자 수정

| 항목 | 내용 |
|------|------|
| 메서드 | PUT |
| URI | `/api/rest1/users/{id}` |

**요청**
```json
{
  "email": "newhong@example.com",
  "password": "5678"
}
```

**응답 (200 OK)**
```json
{
  "id": 1,
  "username": "hong",
  "email": "newhong@example.com"
}
```

---

### 5. 사용자 삭제

| 항목 | 내용 |
|------|------|
| 메서드 | DELETE |
| URI | `/api/rest1/users/{id}` |

**응답 (204 No Content)**

응답 바디 없음

---

## HTTP 상태 코드

| 코드 | 설명 | 발생 상황 |
|------|------|-----------|
| 200 OK | 요청 성공 | 조회, 수정 성공 |
| 201 Created | 생성 성공 | 사용자 생성 성공 |
| 204 No Content | 삭제 성공 | 사용자 삭제 성공 |
| 400 Bad Request | 잘못된 요청 | username 또는 email 중복 |
| 404 Not Found | 리소스 없음 | 해당 ID의 사용자 없음 |
| 500 Internal Server Error | 서버 오류 | 예상치 못한 서버 오류 |

---

## 에러 응답 형식

```json
{
  "status": 404,
  "message": "User not found with id: 99"
}
```
