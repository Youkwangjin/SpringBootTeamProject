<img src="https://capsule-render.vercel.app/api?type=waving&height=200&text=Acontainer&fontAlign=50&fontAlignY=40&color=gradient"/>

# 🌈 SpringBoot 웹 프로젝트 창고 중개 플랫폼 개발
- 프로젝트명: Acontainer
- 참여자 : 최민혁, 조혁진, 이재민, 한지원, 유광진, 이서호, 김효림 (7명)
- 총 개발기간 : 2023/09/01 ~ 2023/10/05 (4주)


## 📌목차
- [프로젝트 개요](#-프로젝트-개요)
- [Project Architecture](#-project-architecture)
- [ERD](#-erd)
- [개발환경](#-개발환경)
- [Backend Library](#-backend-library)
- [프로젝트 주요 기능 및 구현 세부사항](#-프로젝트-주요-기능-및-구현-세부사항)
- [미래 개선 방안](#-미래-개선-방안)
- [참조 사이트](#-참조-사이트)

## ✍️ 프로젝트 개요

### 🌿 배경
#### 1. 공급과잉과 수요 불균형
- 창고 산업에서 발생한 공급과잉은 수요와 공급 사이의 심각한 불균형을 초래했으며, 이로 인해 창고 공실률이 증가했습니다.
#### 2. 창고 공실률 증가에 따른 문제 인식
- 창고 공실률의 증가는 창고 소유주들에게 경제적인 손실을 초래하며, 물류 시장의 비효율성을 낳습니다.
### 🔫 목적
#### 1. 창고 공실률 감소와 최적 활용 증진
창고 공실률을 줄이고 창고 공간을 최적으로 활용하기 위한 효율적인 관리 및 예약 시스템을 제공하는 것을 목표로 합니다.
#### 2. 물류 효율성 향상
용자가 쉽게 접근하고 사용할 수 있는 인터페이스를 통해 물류 과정의 효율성을 높이고 시간 및 비용을 절감할 수 있도록 합니다.

## ❄️ Project Architecture
![image](https://github.com/Youkwangjin/SpringBootTeamProject/assets/117841714/6a5bf59d-7b0c-43e2-ac1f-4ce2ead4ac3f)
![스크린샷 2024-02-22 182416](https://github.com/Youkwangjin/SpringBootTeamProject/assets/117841714/52beec2b-3ae2-4f86-a93d-c86a8e98ccc1)


## 🏬 ERD
![image](https://github.com/Youkwangjin/SpringBootTeamProject/assets/117841714/ed649889-4f1c-4cb6-aff4-16a3c5271153)

## 🛠 개발환경 

📌 **Front-end**

<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white">
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white"> 

📌 **Back-end**

<img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"> <img src="https://img.shields.io/badge/java-1572B6?style=for-the-badge&logo=java&logoColor=white"> 

📌 **DataBase**

<img src="https://img.shields.io/badge/mariadb-003545?style=for-the-badge&logo=mariadb&logoColor=white"> <img src="https://img.shields.io/badge/MyBatis-2E5E82?style=for-the-badge&logo=MyBatis&logoColor=white">

📌 **Framework**

<img src="https://img.shields.io/badge/intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white">

## 📙 Backend Library

| Library                          | Description                                                 |
|----------------------------------|-------------------------------------------------------------|
| spring-boot-starter-web          | 웹 애플리케이션 개발을 위한 스프링 부트 스타터 (MVC)         |
| lombok                           | 코드 간소화를 위해 Getter, Setter, Builder 등 자동 생성      |
| mariadb-java-client              | MariaDB 데이터베이스 연결을 위한 JDBC 드라이버               |
| spring-boot-starter-test         | 스프링 부트 기반 테스트를 위한 스타터 키트                   |
| spring-boot-starter-thymeleaf    | 서버 사이드 HTML 렌더링을 위한 자바 템플릿 엔진              |



## ✨ 프로젝트 주요 기능 및 구현 세부사항

#### 1. 효율적인 창고 관리 및 예약 시스템 제공
- 사용자와 창고 공급자 간의 효율적인 창고 예약 및 관리 시스템을 제공합니다.
#### 2. 사용자 친화적인 인터페이스 설계
- 사용자는 창고 예약, 상태 확인, 취소 등의 과정을 손쉽게 진행할 수 있게  사용하기 쉬운 웹 인터페이스를 구축합니다.
#### 3. 동적 예약 상태 업데이트
- 예약 취소 기능을 통해 사용자는 예약을 취소할 수 있으며, 이 경우 창고의 예약 상태가 즉시 업데이트됩니다.
#### 4. 보안 및 데이터 무결성
- 사용자 입력 및 서버 응답에 대한 유효성 검사를 실시하여 데이터의 정확성과 안전성을 보장합니다.
#### 5. 카카오 맵 API
- 카카오 맵 API를 활하여 사용자에게 창고의 위치 정보를 정확하고 직관적으로 제공합니다.


## 😲 미래 개선 방안

#### 1. 카카오 맵 API를 통해 창고 이미지 표시 개선
#### 2. 데이터베이스 구조 효율성 개선
#### 3. 클린 코드 원칙 적용 완성

## ✅ 참조 사이트

https://구해줘공장창고.com/
