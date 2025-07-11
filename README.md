# Project Pathbook
프로젝트 패스북(Project Pathbook)은 지도 API를 사용하여 사용자가 직접 경로를 그리고, 그린 경로를 저장하고 공유하는 기능을 제공하는 커뮤니티 플랫폼입니다.  

본 리포지토리는 프로젝트 패스북의 백엔드를 담당합니다.

## 개발환경 설정, 빌드 및 실행
아래 환경 설정 방법은 Windows 11을 기준으로 합니다.  
Linux 사용시, 사용중인 Distro에 대응하는 명령어로 진행하시길 바랍니다.
### 필수 요구사항
 - Java 21 이상
 - Gradle 8.13 이상
 - MySQL 8.0 이상

### 빌드 방법
빌드는 Gradle을 사용합니다.  
프로젝트 디렉토리에서 아래 명령어를 실행하여 빌드합니다.
```
./gradlew build
```

### 실행 방법
프로젝트 디렉토리에서 아래 명령어를 실행하여 실행합니다.
```
./gradlew bootRun
```

### (선택 사항) Docker 실행 방법
**실행 전 Docker Desktop이 설치되어 있는지 확인합니다.**  

프로젝트 디렉토리에서 아래 명령어를 실행하여 컨테이너를 구동합니다.
```
docker compose up
```

## 프로젝트 구조
```ansi
main/
├── java/com/pathbook/
│   ├── config/         # 어플리케이션 콘픽 설정
│   ├── controller/     # Controller 코드 (Presentation Layer)
│   ├── dto/            # DTO 코드
│   │   ├── request/    # HTTP 요청 데이터를 매핑한 DTO 코드
│   │   └── response/   # HTTP 응답 데이터를 매핑한 DTO 코드
│   ├── entity/         # Entity 코드
│   │   └── id/         # Entity 키를 매핑한 코드
│   ├── exception/      # 예외 정의
│   ├── jwt/            # JWT 기능 코드
│   ├── repository/     # Repository 코드
│   ├── service/        # Service 코드
│   └── PathbookApiApplication.java # 어플리케이션 엔트리 포인트
├── resources/          # 프로젝트 리소스 파일
└── test/java/com/pathbook/pathbook_api # Unit test 코드
```

## 기능 구현 현황
### 사용자 인증
- [x] 회원가입
- [x] 이메일 인증 요청
- [x] 이메일 인증
- [x] 로그인
- [ ] 로그인 상태 유지
- [x] 세션 내 사용자 정보 요청
- [x] 비밀번호 재설정 요청
- [x] 비밀번호 초기화
- [x] 비밀번호 변경
- [ ] 계정 비활성화
- [x] 계정 삭제
- [ ] 관리자 권한
- [ ] 계정 정지

### 사용자 기능
- [x] 사용자 조회
- [x] 사용자 정보 변경
    - [x] 아이디
    - [x] 닉네임
    - [x] 이메일
    - [x] 성별
    - [x] 생년월일
    - [x] 바이오/상태 메시지
    - [x] 아이콘 이미지
    - [x] 배너 이미지
- [ ] 사용자 신고 기능

### 글 관련 기능
- [x] 글 조회
- [x] 글 작성
- [x] 글 수정
- [x] 글 삭제
- [x] 글 좋아요 기능
- [x] 글 북마크 기능
- [x] 글 태그 기능

### 패스 관련 기능
- [x] 패스 작성
- [x] 패스 수정

### 댓글 관련 기능
- [x] 댓글 조회
- [x] 댓글 작성
- [x] 댓글 수정
- [x] 댓글 삭제
- [x] 댓글 좋아요 기능

### 패스북 관련 기능
- [ ] 패스북 조회
- [ ] 패스북 생성
- [ ] 패스북에 글 추가/삭제
- [ ] 패스북 정보 수정
- [ ] 패스북 삭제
