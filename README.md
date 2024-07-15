# MiniStory
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Next JS](https://img.shields.io/badge/Next-black?style=for-the-badge&logo=next.js&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)

1인 블로그 개발 프로젝트

기간 : 2024/02/21 ~ 2024/04/22 배포 완료
배포 주소 : https://dev-min.me

# 개발하며 작성한 블로그 기록
- [프로젝트 구상](https://seongminlife.tistory.com/29)
- [로그인 환경 구현](https://seongminlife.tistory.com/30)
- [게시글 CRUD 구현](https://seongminlife.tistory.com/31)
- [댓글 CRUD 구현](https://seongminlife.tistory.com/32)
- [자동 로그인 기능 구현](https://seongminlife.tistory.com/33)
- [Querydsl을 이용한 N+1 문제 해결 및 페이징 기능 적용](https://seongminlife.tistory.com/34)
- [게시글 작성 로직 변경 및 태그 추가 기능 구현](https://seongminlife.tistory.com/35)

# 기술 스택

### 1️⃣ 백엔드
> **프레임워크 및 라이브러리**
> - Java 17
> - Spring Boot 3.2.2
> - Spring Boot Starter Security
> - Spring Data JPA
> - Spring Boot Starter Data Redis
> - Spring Cloud Aws Starter 3.0.0
> - Lombok
> - jjwt 0.12.5
> - jsoup 1.17.2
> - commonmark 0.22.0
> - jsch 0.1.55
> - Querydsl 5.0.0

> **데이터베이스**
> - Mysql 8.0.36

### 2️⃣ 프론트엔드
> **프레임워크 및 라이브러리**
> - Next.js 14
> - MUI

> **레이아웃**
> tailwind-nextjs-starter-blog (https://github.com/timlrx/tailwind-nextjs-starter-blog)
### 3️⃣ 인프라
> - AWS EC2
> - AWS Route 53
> - AWS S3
> - AWS Certificate Manager
> - Github
> - Github Actions
> - Docker
> - DockerHub

# 아키텍쳐
<img width="634" alt="스크린샷 2024-07-02 오전 11 53 53" src="https://github.com/dnjsals45/ministory/assets/44596433/a31b01e5-e52d-4af3-95fd-5d7c5fe3471a">

# 기능
### 로그인 기능
- Google 및 Github을 통한 Oauth 로그인 기능
- jwt 토큰 기반의 인증/인가 기능 및 Redis 저장소를 이용한 자동 로그인 기능

### 게시글 기능 
- 메인 페이지 최근 게시글 확인
- 게시글 조회 기능 - 태그 기반 게시글 확인 기능, 페이징 기능, 검색 기능 (우측 상단 돋보기 아이콘)
- 게시글 상세 조회 기능
- 게시글 관리, 수정 및 삭제 (관리자) : 삭제의 경우 soft delete
- 게시글 작성 기능 : Markdown 에디터 사용
