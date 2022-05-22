# MoaMoa

# 1.아이템선정

-이때까지 공부해온 스프링,JPA,QueryDsl,html,css,js,thymeLeaf의 기능을 직접 구현해보고 싶었고 기본적인 CRUD기능뿐만이 아니라 다양한 유효성 검사 , 외부api사용 , 이미지저장 및 보여주기 , 페이징, 세션 , 이메일 인증 등의 기능을 구현하고자 했고 코로나로 인해 매출이 감소한 소상공인들의 매출 증대를 위해 좋은 아이템이 없을까? 하다가 나온 프로젝트이다. 크라우드 펀딩에서 아이디어에 착안하여 매달 추가 및 삭제 그리고 갱신되는 식당 중 일정 인원수 이상의 사람들이 펀딩을 하게 되면 펀딩에 참여한 사람들에게 쿠폰이 발급되어 할인된 가격으로 식당을 이용할 수 있는 프로젝트이다.

# 2.개요

프로젝트 명칭 : MoaMoa Funding
개발 인원 : 1명
개발 기간 : 2021.11.10 ~ 2022.02.10
주요 기능 :
-기본적 CRUD기능
-Session을 이용한 로그인
-페이징(번호를 사용한 페이징과 , 더보기를 통한 페이징 구현)
-인증키를 통한 이메일 인증
-결제api 사용
-이미지저장 및 이미지 보여주기
-로그인,회원가입,펀딩입력,공지사항 등 모든 입력등에서 유효성 검사

개발 언어 : Java 11
개발 환경 : SpringBoot 2.6.3, gradle , jpa(Spring Data JPA , queryDSL) , thymeleaf
데이터베이스 : MySQL
형상관리 툴 : GitHub
간단 소개 : 식당들이 특정한 메뉴에 대해 할인된 가격으로 펀딩을 열어서 고객도 할인된 가격으로 즐길수 있고
최소,최대 판매량을 제한하고 펀딩을 열기때문에 식당도 손해를 보지않는 펀딩시스템 구현

# 3.요구사항 분석

## 1.회원가입

#유효성 검사
-공백 혹은 빈칸을 입력하면 "OO를 입력해주세요"메시지 띄워준다.

#중복확인											
-아이디는 중복으로 회원가입이 불가하다. 아이디 중복 체크기능이 있어야한다.
-비밀번호를 두 번 입력받아서 일치하는지 확인해야한다.회원가입 페이지에서 바로 확인
가능하게 해준다.						
-동일한 이름+이메일 로는 중복회원가입이 불가하다.			
-중복된 명의로 회원가입하거나 입력한 두 비밀번호가 일치하지 않을경우			
오류메시지를 보여준다.

#기타
-회원가입에 성공하면 준회원이다.
-입력된 이메일로 인증키를 보내줘야한다. 보내진 인증키로 인증을 해야 준회원->회원이 된다.

## 2.로그인

#로그인 하지 않은 경우 아래 페이지만 이용가능
-회원가입 페이지
-로그인 페이지
-아이디 찾기 페이지
-비밀번호 찾기 페이지
-그 외 로그인 접속이 필요한 페이지로 이동시 자동으로 로그인 페이지로 이동

#로그인 검사
-아이디 및 비밀번호가 일치하지 않을시 오류메시지 보여주기
-성공적으로 로그인시 home페이지 또는 원래 요청했었던 페이지로 리다이렉트해준다.

#회원타입
-준회원
-> 가입후 이메일 인증을 하지 않으면 준회원이다. 준회원인 상태로는 아무 기능을 이용할수없다.
-> Home 을 제외하고 다른페이지로 들어가면 자동으로 인증페이지가 나오게 구현해야한다.
-일반회원 -> 펀딩작성,공지사항작성을 제외한 모든기능 사용가능 , 펀딩참여시 10%할인 받는다.
-구독회원 -> 펀딩작성,공지사항작성을 제외한 모든기능 사용가능 , 펀딩참여시 20%할인 받는다.
-관리자 -> 펀딩작성 , 공지사항 작성가능

## 3.구독

-일반회원은 원래가격에 10%할인된 가격으로 펀딩참여가능
-구독회원은 원래가격에 20%할인된 가격으로 펀딩참여가능
-구독은 정기결제가 아닌 수동결제이다.
-구독 기한은 결제일로부터 한달이다.
-구독할 돈이 없으면 결제페이지로 이동한다.

## 4.펀딩

#펀딩 작성
-관리자만이 작성할수 있다.						
-펀딩 작성시 각종 정보와 최소,최대펀딩수,펀딩시작날짜 ,펀딩종료날짜,
그리고 썸네일과 여러 이미지를 등록할수 있어야한다.

#펀딩성공 or 실패 조건
-펀딩성공
1.펀딩기간동안 max(최대쿠폰)달성시 성공
2.펀딩기간이 끝났을때 min(최소쿠폰)을 넘겼을시 성공

- 펀딩실패
1.펀딩기간이 끝났을때 min(최소쿠폰)을 못 넘겼을시 실패
2.펀딩 실패시 참여한 회원들에게 자동으로 환불되어야함

#펀딩 검사
-펀딩은 매일 자정 정각에 검사를해서 펀딩이 성공적인지 아닌지 판단한다.
-펀딩은 매일 자정 정각에 검사를해서 펀딩이 시작날짜가 되었으면 펀딩상태를 READY -> PROCEEDING으로 바꾸어준다.

#펀딩참여
-회원은 펀딩에 참여하려면 펀딩금액 이상을 가지고 있어야한다. 소유금액이 펀딩금액에 미치지 않을시 오류메시지를 보여준다.
-회원은 각 펀딩마다 한 번만 참여가능하다. 이미 참여한 펀딩에 참여할시 오류메시지를 띄워준다.

#펀딩 보여주기
-펀딩들은 각 페이지당 4개씩 보여주는 페이징처리를 해준다.
-현재진행중인 펀딩만 보여준다.

## 5.쿠폰

-펀딩이 펀딩기간동안 성공시 자동으로 쿠폰을 만들어 펀딩에 참여한 회원들에게 쿠폰을 보내줘야한다.
-쿠폰사용기간은 펀딩종료날짜부터 +14일이여야 한다.
-쿠폰사용여부를 알수있어야한다.
-쿠폰을 사용하기를 누르면 쿠폰을 더이상 사용할수 없어야 한다.

## 6.검색

-가게이름으로 펀딩 검색이 가능해야한다.					
-진행중 OR 끝난 펀딩도 검색가능하다.

## 7.공지사항

-관리자만이 작성할수 있다.
-작성자가 아니더라도 관리자면 수정,삭제가 가능하다.
-CRUD가능하게 구현해야한다.

## 8.마이페이지

-내 개인정보를 확인할수 있어야한다.
-내 쿠폰내역을 확인 가능해야한다.
-내 펀딩참여내역을 확인 가능해야한다.

## 9.결제

-카카오톡으로 결제가 가능해야한다.

## 10.아이디찾기/비밀번호찾기

-이름+이메일을 입력하면 그 정보에 맞는 아이디를 찾아준다.
-이름+이메일+아이디를 입력하면 그 정보에 맞는 비밀번호를 찾아준다.

## 11.리뷰

-펀딩정보페이지에 리뷰를 남길수 있어야한다.
-리뷰는 더보기 버튼을 누를시 5개씩 더 볼수있어야한다.
-CRUD구현
-리뷰는 작성자만 수정,삭제가 가능하다.

# 4.DB설계

![MoaMoaDB.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cca5e064-5adf-4613-8f83-0a01b7bc9e02/MoaMoaDB.png)

# 5.API명세서

# 6.프로젝트 완성본

1.메인화면(홈)

![Home.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0c8eb7a4-9118-4141-ade3-4f4418f1ea99/Home.png)

2.로그인화면

![Login.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c2753d8d-8a01-4e05-ae93-b6b6078f1d81/Login.png)

3.회원가입

![join-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7c268cc6-56ff-4dec-85d3-12ec7666bf5d/join-1.png)

3-1.아이디중복체크(중복일시)

![join-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0789e2fa-9fa0-432e-92f7-fdf636308c4f/join-2.png)

3-2. 아이디 중복체크(중복이 아닐시)

![join-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c14326d4-6e80-4227-b062-e0d7b6edf896/join-3.png)

3-3. 비밀번호 확인(일치하지 않을시)

![join-4.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7564b88f-7d3d-40b4-ac56-1b7d90f49598/join-4.png)

3-4.비밀번호 확인(일치할 시)

![join-5.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/454eb82a-543d-464e-ba62-ec73bc9d9456/join-5.png)

3-5. 회원가입 성공시

![join-6.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/b9331246-f570-44c1-8153-3c26fc3479bd/join-6.png)

4-1.틀린 아이디 또는 비밀번호를 입력할시

![Login-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9caa4a08-ce90-4f3c-9bb5-453b85fe3cc0/Login-2.png)

4-2. 회원가입후 인증 없이 로그인할시 ( 인증키 입력 페이지로 이동)

![auth-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e2419fed-9db9-4382-93d6-7930a3873d89/auth-1.png)

4-3. 회원가입한 이메일로 인증키가 발송된것을 확인

![auth-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d3ae96a6-599c-4e20-989c-7b52a6503eb9/auth-2.png)

4-4. 잘못된 인증키값 입력시

![auth-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1e935af8-c108-46ca-b7f2-a36609c4a801/auth-3.png)

4-5.올바른 인증키값 입력시

![auth-4.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1fd3ca87-c8f8-46bd-a779-cbb3779b9fff/auth-4.png)

5-1. 아이디 찾기 ( 이름 + 이메일 입력)

![findId-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f49a457b-6df7-4bf4-a76b-76682d82392b/findId-1.png)

5-2.아이디 찾기 (가입된 이름+이메일 입력)

![findId-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4bf16335-8d01-47b6-82f0-65b5f9bbb06a/findId-2.png)

5-3.아이디 찾기 ( 가입되지않은 이름 + 이메일 입력 )

![findId-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2afd18b6-45ee-4dad-8afe-88a7bb55e69c/findId-3.png)

6-1.비밀번호 찾기 (가입된 정보 입력)

![findPw-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0a416cc8-53c9-4c45-a99f-af7c9a3fe50b/findPw-1.png)

6-2.비밀번호 찾기 (가입되지 않은 정보 입력)

![findPw-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4f0cb04f-bbb4-4f14-853d-7f089a0d39c4/findPw-2.png)

7. 마이페이지

![mypage-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9b5a8e61-287b-4bef-8072-5ac8488d8f3e/mypage-1.png)

7-1.마이페이지(내정보/ 초기상태)

![mypage-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4e949c92-6b08-4d86-87a2-1bb0277329b0/mypage-2.png)

7-2.마이페이지(쿠폰 목록/ 초기상태)

![mypage-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/185e5fb7-aae5-4157-bddc-99bd80e8659d/mypage-3.png)

7-3.마이페이지(결제)

![mypage-4.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ede343fa-015f-4d68-a5fc-c7ca06de043b/mypage-4.png)

7-4.마이페이지(구독)

![mypage-5.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/e78f8447-d787-489e-bf73-6298167ca27f/mypage-5.png)

8.가게(등록된 가게(=펀딩)들)

![restaurant-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/5039ea6b-0dfe-4bcb-a432-15e6bc6b6321/restaurant-1.png)

8-1. Detail한 펀딩정보

![restaurant-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/252346c8-5e01-4126-a3d3-e4d8e4d391d1/restaurant-2.png)

8-2. 가게(=펀딩)의 리뷰

![restaurant-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4850c3fa-3780-4418-9f4a-23f3f15c2cb5/restaurant-3.png)

8-3.펀딩참여시 (돈이 부족할때)

![restaurant-4.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6d7e8ffd-e9a8-49fc-a8e9-3141422a45cb/restaurant-4.png)

8-4. 마이페이지(결제/카카오톡 연동)

![mypage-6.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/97571fdc-e81f-4310-b86d-a7e3e485acf7/mypage-6.png)

8-5.마이페이지(결제2/카카오톡 연동)

![mypage-9.jpg](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/acbf22cd-f9e2-4ff5-a929-32a3302d21c7/mypage-9.jpg)

8-6.마이페이지(결제 성공)

![mypage-7.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6a7998a3-146d-4927-8f02-fb68fdac54fb/mypage-7.png)

8-7.마이페이지(결제성공후 내정보)

![mypage-8.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2839b32e-dc2f-4ae9-a8c8-90b334fa5d9f/mypage-8.png)

8-8.펀딩참여시 (돈이 충분할때)

![restaurant-12.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ddad6981-7f9b-4951-8a21-8ec06321633c/restaurant-12.png)

8-9.펀딩참여성공시(현재 펀딩개수가 하나 늘어남)

![restaurant-13.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/aaa7f338-3e1a-4512-b0e2-30983c2bd7ff/restaurant-13.png)

8-10. 마이페이지(펀딩 참여목록 / 초기상태에 비해서 펀딩참여목록이 생김)

![mypage-funding-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/354a15e7-def9-4f92-816c-ea6c4f7c0aba/mypage-funding-1.png)

8-11.펀딩리뷰(리뷰 등록후)

![restaurant-5.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/15a52d7a-a6bc-4507-8f5a-15d6bf7a83b0/restaurant-5.png)

8-12.펀딩리뷰(리뷰 수정버튼클릭시)

![restaurant-6.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ca7de03f-9772-4c57-9b01-eec6cbc1d7b3/restaurant-6.png)

8-13.펀딩리뷰 (리뷰 수정후)

![restaurant-7.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/5e20c6c3-9298-49a9-a378-b3fd97770140/restaurant-7.png)

8-14.펀딩리뷰(여러개의 리뷰)

![restaurant-8.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8fc31ce0-9fd8-425e-895b-78b36e751b85/restaurant-8.png)

8-15.펀딩리뷰(리뷰 더보기 클릭시)

![restaurant-9.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/da4ef732-9d92-46a6-857b-3e98161be08f/restaurant-9.png)

8-16. 펀딩리뷰( 리뷰 삭제 클릭시)

![restaurant-10.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/62b6c402-9fdc-4267-8f17-5ba04c2bab09/restaurant-10.png)

8-17.펀딩리뷰 (리뷰 삭제 후)

![restaurant-11.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ad40794b-dfc1-4add-8e29-759fffc85d61/restaurant-11.png)

8-18.마이페이지(쿠폰함/ 펀딩이 성공하여 펀딩에 참여한 회원에게 쿠폰을 나눠준 경우)

![mypage-coupon-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c85e6706-727a-4fd2-bbb3-402ad53f00e9/mypage-coupon-1.png)

8-19.마이페이지(쿠폰함 / 쿠폰사용한 경우)

![mypage-coupon-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/66a76490-ae80-41af-93df-ef138168deb3/mypage-coupon-2.png)

9.마이페이지(구독성공)

![mypage-subscription-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bef04912-c6cb-4347-9b60-9d1e6aadb49a/mypage-subscription-1.png)

9-1.마이페이지(구독실패/돈 부족)

![mypage-subscription-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f5f66c93-d5c8-4a50-b85e-31ae8fc09828/mypage-subscription-2.png)

10.FAQ(공지 리스트)

![faq-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/2fa91ae7-9f6a-4aa0-bf69-4126522b50bf/faq-1.png)

10-1.FAQ(상세글/ 운영자일시)

![faq-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7de7cd15-d694-4c8f-b1a8-61ed7748cb30/faq-2.png)

10-2.FAQ(상세글/ 일반사용자일시)

![faq-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/64c3c2a1-1784-4cbb-af98-de6195f07b1d/faq-3.png)

10-3.FAQ(일반 사용자가 글쓰기를 클릭시)

![faq-4.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9f3ec779-7d18-4163-8362-9fbe561b086a/faq-4.png)

11.펀딩 검색

![search-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/90a8028c-d1ff-4b52-b06a-5ba67effb696/search-1.png)

11-1.펀딩 검색(일치하는 펀딩(=가게)가 없을경우)

![search-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7d0d326c-8c8a-4d9b-bfe8-0688501b149c/search-2.png)

11-2.펀딩 검색(일치하는 펀딩(=가게)가 있는경우)

![search-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/be728b72-9737-4e8b-a2d0-3aaa445e773f/search-3.png)

12.ADMIN페이지

![admin-1.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a783ae06-f863-4764-b7fb-d56f921fd11e/admin-1.png)

12-1.펀딩등록

![admin-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f872fa46-f200-4143-bbd3-17dd4225d6d9/admin-2.png)

12-2.펀딩등록

![admin-3.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/76aa665a-0e15-47d5-8b56-9892e8231a66/admin-3.png)

12-3.FAQ쓰기

![admin-4.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a3412705-d5a1-4c5e-8f93-a6ac6be12fe4/admin-4.png)

12-4.ADMIN페이지(일반 유저가 접속시도한 경우)

![home-2.PNG](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7f2e7b48-a50f-4f79-99b2-27eb7e70c9f0/home-2.png)

# 7.회고

-배운점이 많은 프로젝트였습니다. 이때까지 공부한 spring,jpa,queryDsl,thymeleaf 등 다양한 기술을 직접 사용해보면서 많은 에러를 겪기도 하고 하나하나 완성되가는 완성물을 보가면서
많이 뿌듯함을 느꼈다.

-이번에는 로그인을 세션으로 구현해보았는데 , 다음 프로젝트에서는 spring security를 이용해서 jwt기반의 로그인을 구현해보려고 한다.

-백엔드쪽 구현도 힘들었지만 , 그만큼 프론트엔드 쪽도 처음부터 시작해서 만들어가느라 힘들었다. 그래서 사실 디자인적 부분은 거의 고려하지않고 백엔드적 기술구현을 확인할수 있게끔만 만들어보아서 많이 아쉽기 때문에 Vue또는 React를 공부해볼 생각이다.

-그리고 html,css,java script 와 jquery , ajax등을 사용해 프론트부분을 만드는과정에서 데이터를 어떻게 넘겨주고 받는지 데이터를 프론트쪽에 어떤식으로 바인딩하는지 등 많은 공부가 되었다. 확실히 백엔드를 지향하지만 프론트쪽 지식이 많으면 많을수록 개발에 큰 도움이 될거같아 다음에는 React,Vue 같은 기술도 공부해서 사용해보고 싶다.
