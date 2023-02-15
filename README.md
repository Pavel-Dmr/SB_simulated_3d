
<div align="center">
<img src="https://capsule-render.vercel.app/api?type=waving&color=random&height=300&section=header&text=쇼핑몰%20사이트%20데모&fontSize=90" />
<h2>📚 Tech Stack 📚</h2>
  <p>✨ PlatForms & Languages ✨</p>
	<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white" />
	<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white" />
	<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white" />
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white" />
  <br>
  <br>
  <p>🎁 Framework 🎁</p>
   <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=Spring Boot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=Spring Security&logoColor=white" />
  <br>
  <br>
  <p>📱 DataBase 📱<p>
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" />
  <img src="https://img.shields.io/badge/MariaDB-003545?style=flat&logo=MariaDB&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=PostgreSQL&logoColor=white" />
  <br>
  <br>
  
  <p>💻 Template Engine 💻</p>
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white" />
  <br>
  <br>
  <p>🛠 Tools 🛠</p> 
  <img src="https://img.shields.io/badge/Intellij IDEA-000000?style=flat&logo=IntellijIDEA&logoColor=white" />
  <img src="https://img.shields.io/badge/Visual Studio Code-007ACC?style=flat&logo=Visual Studio Code&logoColor=white" />
  <br>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=Gradle&logoColor=white" />
  <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white" />
  <br>
  <br>
  <p>🎨 SNS & Portfolio 🎨 </p>
  <img src="https://img.shields.io/badge/Gmail-EA4335?style=flat&logo=Gmail&logoColor=white" />
  <img src="https://img.shields.io/badge/Portfolio-56B366?style=flat&logo=ProtonVPN&logoColor=white" />
  
  <br>
  <br>
  <h2>📜 목차 📜</h2>
</div>  

  [1. 개요](#1장-개요)
  
  [2. 목적](#2장-목적)
  
  [3. 설치 및 실행 방법](#3장-설치-및-실행-방법)
  
  [4. 시스템 구성도](#4장-시스템-구성도)
  
  [5. 구현](#5장-구현)
  
  [6. 이슈](#6장-이슈)
 
  
  ---
  
  # 1장 개요
   - 스프링 기반의 프레임워크,빌드 툴,DBMS 환경 구성,템플릿 엔진 등등 스프링부트 사이트 개발 실습을 목적으로 개발
  
  # 2장 목적
  
	(1) 배경
	- 스프링부트 사이트 개발 실습 및 실제 쇼핑몰 기능을 개발

	(2) 사용자
	- 쇼핑몰 구매자 , 관리자

	(3) 개발 목표
	- 쇼핑몰에 필요한 기능을 직접 개발해보고, 실제 쇼핑몰의 기능을 수행 할 수 있도록 사이트을 개발
	
  # 3장 설치 및 실행 방법
      스프링부트 버전 : 2.7.6
      Java 버전 : Java SDK 11
      의존성 관리 버전 : 1.0.15
      
      의존성 목록
          Spring Data JDBC
          Spring Data JPA
          querydsl
          
          JetBrains Anotation
          Lombok
          modelmapper
          
          H2 Database
          MySQL Driver
          
          Spring Security
          Spring Boot Validation
          Spring Web
          Spring Boot Devtools
          
          Thymeleaf
          thymeleaf-layout-dialect
          thymeleaf-extras-springsecurity5
          
          
          
    
  # 4장 시스템 구성도
  
  
  # 5장 구현
  
	  - 회원 가입 / 로그인 / 아이디 기억하기
	  - 상품 등록 / 수정 / 삭제
	  - 상품 주문 / 취소
	  - 장바구니 기능
   
  # 6장 이슈
  ### "Spring MVC 패키지 간의 작업 수행 도중 예외 발생"
  
    [상황]
    
    - 스프링부트 MVC 구조에서 패키지 간의 작업 수행시 예외 발생 
    
    [문제]
    
    - 사용자가 View에서 상호작용 하면서, 패키지에 해당 변화을 반영하고 일련의 과정을 수행하면서 예외가 발생하는 경우가 잦음
    - 각 패키지의 클래스가 작업 수행을 위한 기능을 수행해야하는데, MVC 구조의 이해 부족에 의해서 개발 코드가 불안정함
    
    [해결]
    
    - 코드을 계속 눈으로 보면서, 무엇이 잘못 됬는지 찾으려면 너무 오랜 시간이 걸리고 파악하지 못할 수 있음 (수 많은 패키지와 클래스에서 무엇이 잘못 됬는지 확인 하기 어려움)
    - 디버깅 작업와 로그 기록의 필요성이 대두됨. lombok에서 제공하는 slf4j 라이브러리을 사용하여, 로그을 기록함
    - 사용자가 View에서 상호작용할 때, 특정 행동의 단위(페이지 접근,버튼 클릭이나, 데이터 입력 등등)마다 로그가 기록되면서 어느 부분이 예외을 유발하는지 확인 할 수 있게됨
    - 일련의 오탈자가 있어서 발생하는 예외 처리임이 확인 되었다. 오탈자을 올바르게 수정하고 View에서 정상적인 기능 작동을 확인함.
    
logback.xml    
![code](https://user-images.githubusercontent.com/104084926/216399205-0357e33f-3353-4423-a707-5c08e3846c7b.png)

  ### "QueryDsl을 구현하는 Custom_RepositoryImpl을 구현 후 Service에서 Custom_Repository 메소드 요청 시 오류 발생"
  
    [상황]
    
    - 커스텀 메소드에 최소 1개 이상의 매개변수는 제공했으나, 쿼리에는 0개의 매개변수만 있습니다.
    - 해당 메소드을 사용에 연관된 컨트롤러,서비스,저장소가 빈 등록 오류가 발생
    
    [문제]
    
    - QueryDsl을 통해, 구현한 메소드을 사용 할 수가 없다. 원하는 기능 구현을 할수가 없음
    
    [해결]
    
     리포지토리 TDD 작성 후, Impl만 테스트 했는데, 구현 QuesyDsl 코드 자체에는 문제가 없었다.
     서비스 TDD 작성 후, 서비스 - 리포지토리 간의 메소드 테스트을 진행 했는데, 쿼리 매개변수 오류가 났다.
     오류 해결 과정에서 수정이 잦다보니, 오류도 다양했는데, 커스텀 쿼리 메소드에 끝에 By을 포함 해 작성 했을 땐, 쿼리 매개변수 오류가 났고
     커스텀 쿼리 메소드에 By을 제거하고 작성 했을때는 , 빈 오류가 났다
     By가 끝에 붙었을땐, Jpa리포지토리 메소드로 인식되고, 안붙었을땐 QueryDsl 메소드로 인식이 안된다는 소리
     Impl 클래스가 제대로 적용이 안되서 QueryDsl 메소드로 인식이 안되고 있다고 판단됨
    
     무엇이 문제인가 하니, Impl 클래스명을 잘못 생성 했다. 커스텀 리포지토리명이 Item_Repository_Custom이었는데 Impl 클래스명을 Item_Repository_Custom_Impl로 작성했다.
     implements 해도 Impl 클래스명 자체가 잘못되니, 사용자 정의 구현 클래스로 인식을 못해서, 커스텀 리포지토리 메소드을 JpaRepository 메소드로 인식 했다.
     Item_Repository_CustomImpl로 수정 후, 제대로 동작함.





  
  
