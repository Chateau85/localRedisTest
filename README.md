# localRedisTest

Jedis로 Redis의 문자열, 집합, 목록, 해시 명령을 실행하는 Java 17 예제입니다. Redis 호출과 예제 로직을 분리해 기본 테스트는 Redis 서버 없이 실행됩니다.

## 요구 사항

- Java 17
- Maven Wrapper 포함
- 애플리케이션을 직접 실행할 때만 Redis 7.2 이상

## 검증

다음 명령은 Redis 서버에 연결하지 않고 단위 테스트, 패키징, SpotBugs/FindSecBugs 및 SBOM 생성을 수행합니다.

```shell
./mvnw clean verify
```

SpotBugs는 MEDIUM 이상 항목이 발견되면 빌드를 실패시킵니다. CycloneDX JSON SBOM은 `target/bom.json`에 생성됩니다.

## 선택적 Redis 실행

실제 Redis 연결은 자동 테스트에 포함되지 않습니다. Redis가 준비된 환경에서만 다음 값을 설정하고 `App`을 실행하세요.

| 환경 변수 | 기본값 | 설명 |
| --- | --- | --- |
| `REDIS_HOST` | `127.0.0.1` | Redis 호스트 |
| `REDIS_PORT` | `6379` | Redis 포트 |
| `REDIS_TIMEOUT_MS` | `3000` | 연결 및 소켓 제한 시간(밀리초) |
| `REDIS_PASSWORD` | 빈 값 | Redis 인증 비밀번호 |

비밀번호는 소스나 명령 기록에 넣지 말고 환경 변수로만 전달합니다.
