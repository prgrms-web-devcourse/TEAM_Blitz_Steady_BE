<p align="center"><img src="https://github.com/Team-Blitz-Steady/steady-client/assets/69716992/fce399f8-bab9-4cf2-bcc1-d29ecfba7fd6" width="50%" height="30%">
</p>

<br><br>

## 👨‍👩‍👧‍👦 멤버
|                                            Team Leader                                             |                                             Tech Leader                                              |                                         PO                                          |              
|:-----------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|
| <img src="https://avatars.githubusercontent.com/u/98159941?v=4" width="130" height="130"> | <img src ="https://avatars.githubusercontent.com/u/101342145?v=4" width="130" height="130"> | <img src ="https://avatars.githubusercontent.com/u/66556716?v=4" width="130" height="130"> | <img src ="https://avatars.githubusercontent.com/u/60502370?v=4" width="130" height="130"> | <img src ="https://avatars.githubusercontent.com/u/61923768?v=4" width="130" height="130"> |  
|                           [원건희](https://github.com/weonest)                           |                              [고범준](https://github.com/K-jun98)                              |                              [나영경](https://github.com/na-yk)
  
<br> <br>  

## ⭐️ 선정 이유

## Commit 컨벤션

```
Emoji Type(#issue-num): subject
// ex) 🚨 Fix(#41): commitlint 이슈 해결
```

```bash
🚑 :ambulance:
- 운영서버에 발생한 긴급한 버그 수정(Hot Fix)

🎨 :art:
- 패키지 구조변경, 코드 포맷팅

🔥 :fire:
- 불필요한 코드(주석 및 죽은코드) 삭제(동작 변경X)

✨ :sparkles:
- 새로운 기능 개발 코드 작성

✅ :white_check_mark:
- 테스트코드 수정, 작성(성공, 실패)

♲ :recycle:
- 코드 리팩터링(현재도 동작엔 문제가 없지만 로직이 변경될 때)

🐛 :bug:
- 개발환경에서 발생한 버그 수정

📝 :memo:
- 문서([ReadMe.md](http://ReadMe.md)) 수정

📌 :pushpin:
- 의존성 추가, 삭제, 및 버전 변경 모두 pushpin으로 통일 + 커밋 메시지에 자세히 남기기

👨‍💼 :construction_worker:
- CI/CD 스크립트 추가,수정,삭제

🔧 :wrench:
- 환경 파일(application.yml)  + @Configuration 추가, 수정, 삭제
```

## 패키지 구조
```text

└── java
     └── dev.steady
         ├── application
         │   ├── controller
         │   ├── domain
         │   │   └── repository
         │   ├── dto
         │   │   ├── request
         │   │   └── response
         │   ├── exception
         │   └── service
         ├── auth
         │   ├── config
         │   ├── controller
         │   ├── domain
         │   │   └── repository
         │   ├── dto
         │   │   └── response
         │   ├── exception
         │   ├── oauth
         │   │   ├── client
         │   │   ├── domain
         │   │   ├── dto
         │   │   │   └── response
         │   │   └── service
         │   └── service
         ├── global
         │   ├── advie
         │   ├── auth
         │   ├── config
         │   ├── converter
         │   ├── entity
         │   ├── exception
         │   ├── logging
         │   └── response
         ├── notification
         │   ├── controller
         │   ├── domain
         │   │   └── repository
         │   ├── dto
         │   ├── exception
         │   └── service
         ├── review
         │   ├── controller
         │   ├── domain
         │   │   └── repository
         │   ├── dto
         │   │   ├── request
         │   │   └── response
         │   ├── exception
         │   ├── infrastructure
         │   └── service
         ├── steady
         │   ├── controller
         │   ├── domain
         │   │   └── repository
         │   ├── dto
         │   │   ├── request
         │   │   └── response
         │   ├── exception
         │   ├── infrastructure
         │   │   └── util
         │   └── service
         ├── storage
         │   └── exception
         ├── template
         │   ├── controller
         │   ├── domain
         │   │   └── repository
         │   ├── dto
         │   │   ├── request
         │   │   └── response
         │   ├── exception
         │   └── service
         └── user
             ├── controller
             ├── domain
             │   └── repository
             ├── dto
             │   ├── request
             │   └── response
             ├── exception
             └── service
```

<br> <br>  

## 👨🏻‍💻 사용자 요청 흐름도

<img src="https://github.com/back-og/back-log/assets/66556716/298a6471-f9e4-49d8-bdd4-06169b9fcdd9" width="700" height="250">

<br><br>

## ⛓ CI/CD

<img src="https://github.com/back-og/back-log/assets/66556716/2c18cbc0-0faa-46cc-8c4e-e47c6c20d216" width="700" height="400">  

<br> <br>

## ⚙️ 기술 스택

### 🏷 서버

<img src="https://github.com/back-og/back-log/assets/66556716/d462ce19-0dd2-4899-af3a-56fa4f4467d1" width="700" height="330">

### 🏷 인프라
<img src="https://github.com/back-og/back-log/assets/66556716/aad8ca3f-9ab1-4827-94ae-ca1ecd9f72a2" width="700" height="130">

<br> <br>

## 📊 테스트 커버리지

<img src="https://github.com/back-og/back-log/assets/66556716/57e8dfa9-a934-428a-8437-86bf71e4534d" width="700" height="600">  

<br> <br>   

## 📋 API 문서화
<img src="https://github.com/back-og/back-log/assets/66556716/fb2d55ff-d152-4248-a5e7-1956a5368de1" width="700" height="600">  
<img src="https://github.com/back-og/back-log/assets/66556716/90063c0e-236f-4897-ac57-c369ee55e927" width="700" height="600">  



<br> <br>


![footer](https://capsule-render.vercel.app/api?type=rect&color=cefbc9&height=20&section=footer&fontSize=50&fontColor=000000)
