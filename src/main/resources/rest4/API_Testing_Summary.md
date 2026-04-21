# API Testing 요약

## 테스트 도구

| 도구 | 용도 |
|------|------|
| Postman | 수동/자동 API 테스트 |
| Spring Boot Test + MockMvc | 단위/통합 테스트 |

---

## Postman 테스트 스크립트

### 1. 사용자 생성 - 정상

```javascript
pm.test("201 Created 반환", function () {
    pm.response.to.have.status(201);
});

pm.test("응답에 id 포함", function () {
    const json = pm.response.json();
    pm.expect(json.id).to.be.a("number");
    pm.expect(json.username).to.eql("hong");
    pm.expect(json.email).to.eql("hong@example.com");
});
```

### 2. 사용자 생성 - username 중복

```javascript
pm.test("400 Bad Request 반환", function () {
    pm.response.to.have.status(400);
});

pm.test("에러 메시지 포함", function () {
    const json = pm.response.json();
    pm.expect(json.message).to.include("이미 존재하는 username");
});
```

### 3. 전체 조회 - 정상

```javascript
pm.test("200 OK 반환", function () {
    pm.response.to.have.status(200);
});

pm.test("배열 형태 응답", function () {
    const json = pm.response.json();
    pm.expect(json).to.be.an("array");
});
```

### 4. 단건 조회 - 존재하지 않는 ID

```javascript
pm.test("404 Not Found 반환", function () {
    pm.response.to.have.status(404);
});

pm.test("에러 메시지 포함", function () {
    const json = pm.response.json();
    pm.expect(json.message).to.include("not found");
});
```

### 5. 사용자 수정 - 정상

```javascript
pm.test("200 OK 반환", function () {
    pm.response.to.have.status(200);
});

pm.test("email 수정 반영", function () {
    const json = pm.response.json();
    pm.expect(json.email).to.eql("newhong@example.com");
});
```

### 6. 사용자 삭제 - 정상

```javascript
pm.test("204 No Content 반환", function () {
    pm.response.to.have.status(204);
});
```

### 7. 사용자 삭제 - 존재하지 않는 ID

```javascript
pm.test("404 Not Found 반환", function () {
    pm.response.to.have.status(404);
});
```

---

## Spring Boot MockMvc 테스트 코드

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void 사용자_생성_성공() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("hong");
        dto.setEmail("hong@example.com");
        dto.setPassword("1234");

        mockMvc.perform(post("/api/rest1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("hong"))
                .andExpect(jsonPath("$.email").value("hong@example.com"));
    }

    @Test
    void 사용자_생성_username_중복() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("hong");
        dto.setEmail("hong@example.com");
        dto.setPassword("1234");

        mockMvc.perform(post("/api/rest1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/rest1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 전체_사용자_조회() throws Exception {
        mockMvc.perform(get("/api/rest1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void 단건_조회_존재하지_않는_ID() throws Exception {
        mockMvc.perform(get("/api/rest1/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void 사용자_수정_성공() throws Exception {
        UserRequestDto createDto = new UserRequestDto();
        createDto.setUsername("hong");
        createDto.setEmail("hong@example.com");
        createDto.setPassword("1234");

        String response = mockMvc.perform(post("/api/rest1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        UserRequestDto updateDto = new UserRequestDto();
        updateDto.setEmail("newhong@example.com");
        updateDto.setPassword("5678");

        mockMvc.perform(put("/api/rest1/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newhong@example.com"));
    }

    @Test
    void 사용자_삭제_성공() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setUsername("hong");
        dto.setEmail("hong@example.com");
        dto.setPassword("1234");

        String response = mockMvc.perform(post("/api/rest1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/rest1/users/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void 사용자_삭제_존재하지_않는_ID() throws Exception {
        mockMvc.perform(delete("/api/rest1/users/999"))
                .andExpect(status().isNotFound());
    }
}
```

---

## 테스트 실행 결과 요약

```
UserControllerTest
  ✅ 사용자_생성_성공              201 Created
  ✅ 사용자_생성_username_중복     400 Bad Request
  ✅ 전체_사용자_조회              200 OK
  ✅ 단건_조회_존재하지_않는_ID    404 Not Found
  ✅ 사용자_수정_성공              200 OK
  ✅ 사용자_삭제_성공              204 No Content
  ✅ 사용자_삭제_존재하지_않는_ID  404 Not Found

Tests run: 7, Failures: 0, Errors: 0
```

---

## 테스트 케이스 정리

| 케이스 | 메서드 | URI | 기대 상태 코드 | 결과 |
|--------|--------|-----|---------------|------|
| 사용자 생성 정상 | POST | /api/rest1/users | 201 | ✅ |
| username 중복 생성 | POST | /api/rest1/users | 400 | ✅ |
| email 중복 생성 | POST | /api/rest1/users | 400 | ✅ |
| 전체 조회 | GET | /api/rest1/users | 200 | ✅ |
| 단건 조회 정상 | GET | /api/rest1/users/{id} | 200 | ✅ |
| 단건 조회 없는 ID | GET | /api/rest1/users/999 | 404 | ✅ |
| 수정 정상 | PUT | /api/rest1/users/{id} | 200 | ✅ |
| 수정 없는 ID | PUT | /api/rest1/users/999 | 404 | ✅ |
| 삭제 정상 | DELETE | /api/rest1/users/{id} | 204 | ✅ |
| 삭제 없는 ID | DELETE | /api/rest1/users/999 | 404 | ✅ |
