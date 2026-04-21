# JWT Security 구현 요약

## 결과 요약

> 정상 토큰은 통과, 잘못된 토큰은 401 반환

---

## 흐름

```
클라이언트                    서버
   |                           |
   |--- POST /api/auth/login -->|
   |                           | username/password 검증
   |<-- 200 OK + JWT 토큰 ------|
   |                           |
   |--- GET /api/rest1/users -->|
   |    Authorization: Bearer  | 토큰 검증 (JwtFilter)
   |    {token}                | ✅ 유효 → 요청 처리
   |<-- 200 OK -----------------|
   |                           |
   |--- GET /api/rest1/users -->|
   |    Authorization: Bearer  | 토큰 검증 (JwtFilter)
   |    {invalid_token}        | ❌ 무효 → 401 반환
   |<-- 401 Unauthorized -------|
```

---

## 핵심 컴포넌트

### 1. JwtUtil

JWT 토큰 생성 및 검증 유틸리티

| 메서드 | 설명 |
|--------|------|
| `generateToken(username)` | 로그인 성공 시 토큰 발급 |
| `extractUsername(token)` | 토큰에서 사용자명 추출 |
| `isTokenValid(token)` | 토큰 유효성 검증 |

**토큰 구조 (JWT Claims)**

| 필드 | 설명 |
|------|------|
| sub | 사용자명 |
| iat | 발급 시각 |
| exp | 만료 시각 (기본 24시간) |

---

### 2. JwtFilter (OncePerRequestFilter)

모든 요청에서 실행되는 JWT 검증 필터

```
요청 수신
  ↓
Authorization 헤더 확인
  ↓
"Bearer " 접두사 제거 → 토큰 추출
  ↓
토큰 유효성 검증
  ├── 유효 → SecurityContext에 인증 정보 저장 → 다음 필터 통과
  └── 무효/만료/없음 → 401 Unauthorized 반환
```

---

### 3. AuthController

| 메서드 | URI | 설명 |
|--------|-----|------|
| POST | `/api/auth/login` | username/password로 JWT 발급 |

**요청**
```json
{
  "username": "hong",
  "password": "1234"
}
```

**응답 (200 OK)**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 4. SecurityConfig

| 경로 | 인증 여부 |
|------|-----------|
| `POST /api/auth/login` | 허용 (인증 불필요) |
| 그 외 모든 요청 | JWT 토큰 필수 |

---

## 토큰 검증 시나리오

| 시나리오 | 결과 | HTTP 상태 |
|----------|------|-----------|
| 정상 토큰 | 요청 처리 | 200 OK |
| 토큰 없음 | 거부 | 401 Unauthorized |
| 만료된 토큰 | 거부 | 401 Unauthorized |
| 서명 불일치 토큰 | 거부 | 401 Unauthorized |
| 변조된 토큰 | 거부 | 401 Unauthorized |

---

## 의존성

```groovy
implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'
```

---

## 코드 구조

```
rest2/
├── controller/
│   └── AuthController.java        ← POST /api/auth/login
├── filter/
│   └── JwtFilter.java             ← 토큰 검증 필터
├── util/
│   └── JwtUtil.java               ← 토큰 생성/검증
├── config/
│   └── SecurityConfig.java        ← 필터 체인 설정
├── service/
│   └── AuthService.java           ← 로그인 처리
└── dto/
    ├── LoginRequestDto.java
    └── LoginResponseDto.java
```
