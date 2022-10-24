# bookJeokBookJeok

# 소개
북적북적(BookJeokBookJeok)은 읽고 싶은 책을 저장해 두고, 목표 독서일을 지정한 후 알림을 보내주고, 독후감을 기록하고 공유하는 웹 어플리케이션입니다.

# 기술 스택
- Java
- SpringBoot

# 기능
### 책 검색 기능
- 네이버 API
- WebClient
### JWT 토튼을 통한 인증/인가 기능
- SpringSecurity
- jwtt
### 회원가입
- JPA 사용, 비밀번호 암호화하여 저장
### 위시리스트 CRUD
- JPA 사용
- 데이터 공간 절약을 위해 책 정보는 ISBN(serial number)만 저장하여, 조회할 때마다 네이버API 호출

### 독후감 CRUD
- JPA 사용

### 독후감 공유(진행예정)
- 독후감 공유 / 검색 / 좋아요 / 댓글
