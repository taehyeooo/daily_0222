# Swagger (OpenAPI) 구현 요약

## 의존성 추가

**build.gradle**
```groovy
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'
```

---

## Swagger 설정 코드

**SwaggerConfig.java**
```java
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User CRUD API")
                        .description("RESTful API 문서")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
```

---

## 엔드포인트 문서화 코드

**UserController.java**
```java
@Tag(name = "User", description = "사용자 CRUD API")
@RestController
@RequestMapping("/api/rest1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "username 또는 email 중복")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @Operation(summary = "전체 사용자 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "단건 사용자 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "사용자 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @Operation(summary = "사용자 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "사용자 없음")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## API 문서화 화면 요약

### 접속 URL
```
http://localhost:8080/swagger-ui/index.html
```

### 화면 구성

```
┌─────────────────────────────────────────┐
│  User CRUD API  v1.0.0                  │
│  RESTful API 문서                        │
├─────────────────────────────────────────┤
│  User - 사용자 CRUD API                  │
│                                         │
│  POST   /api/rest1/users   사용자 생성   │
│  GET    /api/rest1/users   전체 조회     │
│  GET    /api/rest1/users/{id}  단건 조회 │
│  PUT    /api/rest1/users/{id}  수정      │
│  DELETE /api/rest1/users/{id}  삭제      │
└─────────────────────────────────────────┘
```

### 주요 기능
| 기능 | 설명 |
|------|------|
| Try it out | 브라우저에서 직접 API 요청 실행 |
| Authorize | JWT 토큰 입력 후 인증 테스트 |
| 요청/응답 스키마 | Request/Response JSON 구조 자동 표시 |
| 상태 코드 | 각 API별 응답 코드 자동 문서화 |

---

## 주요 어노테이션

| 어노테이션 | 위치 | 설명 |
|------------|------|------|
| `@Tag` | 클래스 | API 그룹명 지정 |
| `@Operation` | 메서드 | API 설명 |
| `@ApiResponse` | 메서드 | 응답 코드별 설명 |
| `@Schema` | DTO 필드 | 필드 설명 |
