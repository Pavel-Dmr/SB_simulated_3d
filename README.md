
<div align="center">
<img src="https://capsule-render.vercel.app/api?type=waving&color=random&height=300&section=header&text=쇼핑몰%20사이트%20데모&fontSize=90" />
<h2>📚 Tech Stack 📚</h2>
  <p>✨ PlatForms & Languages ✨</p>
	<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white" />
	<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML5&logoColor=white" />
	<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=CSS3&logoColor=white" />
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat&logo=JavaScript&logoColor=white" />
  <br>
  
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat&logo=PostgreSQL&logoColor=white" />
  <br>
  <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat&logo=Spring Boot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat&logo=Spring Security&logoColor=white" />
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
  
  [2. 설치 및 실행 방법](#2장-설치-및-실행-방법)
  
  [3. 시스템 구성도](#3장-시스템-구성도)
  
  [4. 구현](#4장-구현)
 
  
  ---
  
  # 1장 개요
    스프링부트 기반의 쇼핑몰 데모 사이트입니다. 기존 쇼핑몰 기능을 구현 해보고, 스프링부트 기반의 기능 이해을 위해서 제작 되었습니다. 
  
  
  # 2장 설치 및 실행 방법
      스프링부트 버전 : 2.7.6
      Java 버전 : Java SDK 11
      의존성 매니지먼트 : 1.0.15
      
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
          
          
          
    
  # 3장 시스템 구성도
  
  
  # 4장 구현
  
  ### 직접 구현한 기능
  
  1. 로그인 구현 
      - Member_Controller
      - Member_Dto
      - Member
      - Member_Service
      - Member_Repository
      - Member_Login.html 
    
  2. 상품 등록 및 수정
      - Item_Controller
      - Item_Dto , Item_Img_Dto
      - Item , Item_Img
      - Item_Service , Item_Img_Service
      - Item_Repository , Item_Img_Repository
      - Item_Form.html , Item_View.html
  
  3. 상품 주문 및 취소
      - Order_Controller
      - Order_Dto , Order_Hist_Dto , Order_Item_Dto
      - Order , Order_Item
      - Order_Service
      - Order_Repository , Order_Item_Repository
      - Order_Hist.html
      
  4. 장바구니 구현
      - Cart_Controller
      - Cart_Item_Dto , Cart_Order_Dto , Cart_Detail_Dto
      - Cart , Cart_Item
      - Cart_Service
      - Cart_Repository , Cart_Item_Respository
      - Cart.html
      
  5. 보안 커스텀
      - Login_Fail_Service
      
### 제공된 기능
    
  1. DB 쿼리문 처리
     - JpaRepository 인터페이스

  2. 디버깅 로그 출력
      - lombok.extern.slf4j.Slf4j;
      - logback.xml

  3. 빌드 툴
      - Gradle
  
  4. 보안
      - Spring Security
      - Security_Config
 
    
     
   
    
  ---




  
  
