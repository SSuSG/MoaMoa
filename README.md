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

![MoaMoaDB](https://user-images.githubusercontent.com/33506590/169702974-a48c7a31-a569-4f74-97e5-b4d18cb415c4.PNG)

# 5.API명세서

https://www.notion.so/b9a15d8a03a64703914077a52d1df37a?v=554914601dd14f77a0e17b9d117f5d73

# 6.프로젝트 완성본

1.메인화면(홈)
![Home](https://user-images.githubusercontent.com/33506590/169703519-232b074a-f1d8-47c2-a11f-b43c7b518954.png)










2.로그인화면
![Login](https://user-images.githubusercontent.com/33506590/169703531-7a48baa8-c668-4310-9470-703ffe41c087.png)










3.회원가입



















![join-1](https://user-images.githubusercontent.com/33506590/169703551-53e6969e-4485-41e7-9667-ffc9fa6fb37a.PNG)










3-1.아이디중복체크(중복일시)









![join-2](https://user-images.githubusercontent.com/33506590/169703554-fa53ea5a-60b5-4dc2-8dbf-c12120f3f5b9.PNG)










3-2. 아이디 중복체크(중복이 아닐시)









![join-3](https://user-images.githubusercontent.com/33506590/169703555-f55427f1-6a23-4092-97dc-a8a27487d296.PNG)










3-3. 비밀번호 확인(일치하지 않을시)









![join-4](https://user-images.githubusercontent.com/33506590/169703558-bbc6df0e-6ccf-48b2-b91d-d47e508b8729.PNG)










3-4.비밀번호 확인(일치할 시)









![join-5](https://user-images.githubusercontent.com/33506590/169703560-ffea202c-3e6f-494e-89d0-e334f5b79e6b.PNG)










3-5. 회원가입 성공시









![join-6](https://user-images.githubusercontent.com/33506590/169703568-25b12fd7-e302-4dcd-a94d-0d29bfff1c99.PNG)










4-1.틀린 아이디 또는 비밀번호를 입력할시









![Login-2](https://user-images.githubusercontent.com/33506590/169703633-5a46356a-b512-4888-9a02-1091c82e6743.PNG)










4-2. 회원가입후 인증 없이 로그인할시 ( 인증키 입력 페이지로 이동)
![auth-1](https://user-images.githubusercontent.com/33506590/169703637-ad60c117-cf60-4f50-87a6-a0e51d5a35e1.PNG)










4-3. 회원가입한 이메일로 인증키가 발송된것을 확인
![auth-2](https://user-images.githubusercontent.com/33506590/169703641-3657f9ed-a5f0-48e6-81df-8b1d3cc141f6.PNG)










4-4. 잘못된 인증키값 입력시
![auth-3](https://user-images.githubusercontent.com/33506590/169703646-3bd31f99-7d5f-4d84-bb59-2c78c93e31e9.PNG)










4-5.올바른 인증키값 입력시
![auth-4](https://user-images.githubusercontent.com/33506590/169703648-f1df92eb-ed7e-4620-83b4-3b5dc7dd0adf.PNG)










5-1. 아이디 찾기 ( 이름 + 이메일 입력)
![findId-1](https://user-images.githubusercontent.com/33506590/169703650-66d0fa07-65d5-4ee1-a7d3-9da211d2fee5.PNG)










5-2.아이디 찾기 (가입된 이름+이메일 입력)
![findId-2](https://user-images.githubusercontent.com/33506590/169703652-08c592a3-dcac-4c03-a782-6e8da8aa3ed8.PNG)










5-3.아이디 찾기 ( 가입되지않은 이름 + 이메일 입력 )
![findId-3](https://user-images.githubusercontent.com/33506590/169703655-c8b4aae4-5a65-4146-bd52-b3c69c1f8fa4.PNG)










6-1.비밀번호 찾기 (가입된 정보 입력)
![findPw-1](https://user-images.githubusercontent.com/33506590/169703656-4376024d-30ad-443b-b617-f2ede0777697.PNG)










6-2.비밀번호 찾기 (가입되지 않은 정보 입력)
![findPw-2](https://user-images.githubusercontent.com/33506590/169703658-61fb8076-044f-4b43-846a-2a4eeaeef5ce.PNG)










7. 마이페이지
![mypage-1](https://user-images.githubusercontent.com/33506590/169703722-cfc7f705-f9cf-4bed-b526-f6682840fcd1.PNG)










7-1.마이페이지(내정보/ 초기상태)
![mypage-2](https://user-images.githubusercontent.com/33506590/169703727-415269fb-d0a8-43a8-9b06-4612eb9d0091.PNG)










7-2.마이페이지(쿠폰 목록/ 초기상태)
![mypage-3](https://user-images.githubusercontent.com/33506590/169703729-50eeef59-6705-4256-a780-148ea1fd3d31.PNG)










7-3.마이페이지(결제)
![mypage-4](https://user-images.githubusercontent.com/33506590/169703730-928ecb2a-5b1a-4d91-ad7e-4109309c2924.PNG)










7-4.마이페이지(구독)
![mypage-5](https://user-images.githubusercontent.com/33506590/169703735-126d4443-3467-41e5-b868-a356093161f9.PNG)










8.가게(등록된 가게(=펀딩)들)
![restaurant-1](https://user-images.githubusercontent.com/33506590/169703755-90a78cec-bde8-41e3-87b6-399f55e08396.PNG)










8-1. Detail한 펀딩정보
![restaurant-2](https://user-images.githubusercontent.com/33506590/169703756-b6f237ab-ff6b-4901-b1c1-e1a53fb54f1d.PNG)










8-2. 가게(=펀딩)의 리뷰
![restaurant-3](https://user-images.githubusercontent.com/33506590/169703758-8f6076e7-90dc-4af4-9401-fd487c0b318d.PNG)










8-3.펀딩참여시 (돈이 부족할때)
![restaurant-4](https://user-images.githubusercontent.com/33506590/169703760-84d2b1e9-12cc-407b-9f86-80a1a56049d6.PNG)










8-4. 마이페이지(결제/카카오톡 연동)
![mypage-6](https://user-images.githubusercontent.com/33506590/169703789-7779e188-7e26-4170-9cbd-12896376637a.PNG)










8-5.마이페이지(결제2/카카오톡 연동)
![mypage-9](https://user-images.githubusercontent.com/33506590/169703791-44b5c821-382a-4f7a-8fa6-508ede12c16f.jpg)










8-6.마이페이지(결제 성공)
![mypage-7](https://user-images.githubusercontent.com/33506590/169703794-8e125a08-ee13-42b2-9641-61eb24f61233.PNG)










8-7.마이페이지(결제성공후 내정보)
![mypage-8](https://user-images.githubusercontent.com/33506590/169703797-49266926-eaf2-4640-996a-8e17cf9d0afc.PNG)










8-8.펀딩참여시 (돈이 충분할때)
![restaurant-12](https://user-images.githubusercontent.com/33506590/169703801-b6f788c0-b62a-46c4-a21f-8d97ce9e207c.PNG)









8-9.펀딩참여성공시(현재 펀딩개수가 하나 늘어남)
![restaurant-13](https://user-images.githubusercontent.com/33506590/169703806-99326bab-a64d-4cdd-b456-cb46a490ebd4.PNG)










8-10. 마이페이지(펀딩 참여목록 / 초기상태에 비해서 펀딩참여목록이 생김)
![mypage-funding-1](https://user-images.githubusercontent.com/33506590/169703826-d1d03310-5784-41ae-9ca1-c2afb4beb566.PNG)










8-11.펀딩리뷰(리뷰 등록후)
![restaurant-5](https://user-images.githubusercontent.com/33506590/169703828-2c54c77e-9bd7-4ea1-a9e7-257d2f1e2287.PNG)










8-12.펀딩리뷰(리뷰 수정버튼클릭시)
![restaurant-6](https://user-images.githubusercontent.com/33506590/169703831-aa93c5f9-d265-4cc1-9ad9-a6c1d7ba0b6d.PNG)










8-13.펀딩리뷰 (리뷰 수정후)
![restaurant-7](https://user-images.githubusercontent.com/33506590/169703833-2e904d04-e8de-4fad-8e63-6b7b0cc4560a.PNG)










8-14.펀딩리뷰(여러개의 리뷰)
![restaurant-8](https://user-images.githubusercontent.com/33506590/169703834-c2ebfc55-0d5a-4526-8467-b7fbd36c0870.PNG)










8-15.펀딩리뷰(리뷰 더보기 클릭시)
![restaurant-9](https://user-images.githubusercontent.com/33506590/169703835-ec665a05-38b5-46b2-a82d-a99007d15bac.PNG)










8-16. 펀딩리뷰( 리뷰 삭제 클릭시)
![restaurant-10](https://user-images.githubusercontent.com/33506590/169703837-2952263f-1bf9-41f2-bfbd-365ad5ee2d0f.PNG)










8-17.펀딩리뷰 (리뷰 삭제 후)
![restaurant-11](https://user-images.githubusercontent.com/33506590/169703838-769ef00a-dec6-4069-a7af-f57e94810dc0.PNG)










8-18.마이페이지(쿠폰함/ 펀딩이 성공하여 펀딩에 참여한 회원에게 쿠폰을 나눠준 경우)
![mypage-coupon-1](https://user-images.githubusercontent.com/33506590/169703882-e0fed83e-aa1c-4c6e-a44b-b03e604574fd.PNG)










8-19.마이페이지(쿠폰함 / 쿠폰사용한 경우)
![mypage-coupon-2](https://user-images.githubusercontent.com/33506590/169703884-b48d2a72-610c-45eb-aca6-aaba4f94dce3.PNG)










9.마이페이지(구독성공)
![mypage-subscription-1](https://user-images.githubusercontent.com/33506590/169703889-06f5c4be-ae80-4825-bac3-7caac6696313.PNG)










9-1.마이페이지(구독실패/돈 부족)
![mypage-subscription-2](https://user-images.githubusercontent.com/33506590/169703891-9c2c81d6-7de3-49c2-833e-44c669f370fa.PNG)










10.FAQ(공지 리스트)
![faq-1](https://user-images.githubusercontent.com/33506590/169703896-3ffefb4e-3621-41b3-bbb5-d62b9443fa3c.PNG)










10-1.FAQ(상세글/ 운영자일시)
![faq-2](https://user-images.githubusercontent.com/33506590/169703897-021aefef-1fde-4fa3-9d26-f14c89e46c46.PNG)










10-2.FAQ(상세글/ 일반사용자일시)
![faq-3](https://user-images.githubusercontent.com/33506590/169703898-ad60d27b-183e-4abf-8b2a-c05b5f71c89f.PNG)










10-3.FAQ(일반 사용자가 글쓰기를 클릭시)
![faq-4](https://user-images.githubusercontent.com/33506590/169703901-a8d9d1d4-d99d-453c-a4a1-78a2e13661df.PNG)










11.펀딩 검색
![search-1](https://user-images.githubusercontent.com/33506590/169703909-55757586-2e28-4e2f-aefc-eb3e967b6bfb.PNG)










11-1.펀딩 검색(일치하는 펀딩(=가게)가 없을경우)
![search-2](https://user-images.githubusercontent.com/33506590/169703910-61dee3e3-842c-471e-a304-e22581774b2a.PNG)










11-2.펀딩 검색(일치하는 펀딩(=가게)가 있는경우)
![search-3](https://user-images.githubusercontent.com/33506590/169703914-806ec9a7-2971-45b0-9366-50730f659be9.PNG)










12.ADMIN페이지
![admin-1](https://user-images.githubusercontent.com/33506590/169703927-30066f7e-3934-40dd-a587-d748376c24e8.PNG)










12-1.펀딩등록
![admin-2](https://user-images.githubusercontent.com/33506590/169703931-67ea64bc-68df-4071-8b4e-cec9aa1a2199.PNG)










12-2.펀딩등록
![admin-3](https://user-images.githubusercontent.com/33506590/169703934-b21684a1-336f-4dd2-a9a0-613a6cb801c1.PNG)










12-3.FAQ쓰기
![admin-4](https://user-images.githubusercontent.com/33506590/169703936-c2b2cf4b-e240-4b66-ac5d-f62ae5e76e80.PNG)










12-4.ADMIN페이지(일반 유저가 접속시도한 경우)
![home-2](https://user-images.githubusercontent.com/33506590/169703941-d89adbc6-ad69-4391-9561-323e19682be9.PNG)










# 7.회고

-배운점이 많은 프로젝트였습니다. 이때까지 공부한 spring,jpa,queryDsl,thymeleaf 등 다양한 기술을 직접 사용해보면서 많은 에러를 겪기도 하고 하나하나 완성되가는 완성물을 보가면서
많이 뿌듯함을 느꼈다.

-이번에는 로그인을 세션으로 구현해보았는데 , 다음 프로젝트에서는 spring security를 이용해서 jwt기반의 로그인을 구현해보려고 한다.

-백엔드쪽 구현도 힘들었지만 , 그만큼 프론트엔드 쪽도 처음부터 시작해서 만들어가느라 힘들었다. 그래서 사실 디자인적 부분은 거의 고려하지않고 백엔드적 기술구현을 확인할수 있게끔만 만들어보아서 많이 아쉽기 때문에 Vue또는 React를 공부해볼 생각이다.

-그리고 html,css,java script 와 jquery , ajax등을 사용해 프론트부분을 만드는과정에서 데이터를 어떻게 넘겨주고 받는지 데이터를 프론트쪽에 어떤식으로 바인딩하는지 등 많은 공부가 되었다. 확실히 백엔드를 지향하지만 프론트쪽 지식이 많으면 많을수록 개발에 큰 도움이 될거같아 다음에는 React,Vue 같은 기술도 공부해서 사용해보고 싶다.
