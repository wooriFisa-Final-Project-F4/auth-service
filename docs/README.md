# USER & AUTH Service

## Overview

Spring Cloud를 활용한 User & Auth 기능을 담당한 서버입니다.

## Requirements

|       기능       | 기술 스택                                                                       |
|:--------------:|:----------------------------------------------------------------------------|
|  Spring Boot   | - Spring Framework 2.7.15<br> - Java 17 <br> - Gradle 8.0 <br> - Spring Web |
|  Spring Cloud  | - Eureka <br> - Config <br> - Gateway <br> - OpenFeign                      |
|    Database    | - Mysql 8.33 <br>- Redis Client                                             |
|      ORM       | - JPA <br>- Native Query                                                    |
| Authentication | - JWT                                                                       |
|   Monitoring   | - Actuator <br> - Spring Cloud Sleuth                                       |
|    Incident    | - Resilience4J <br>                                                         |

## Auth Service 기능

|   기능   | 내용                                                                                                 |
|:------:|:---------------------------------------------------------------------------------------------------|
|  로그인   | 사용자로부터 정보를 입력받아 신원을 확인 후,<br>**JWT Access Token (Local Storage) & Refresh (Cookie) 토큰을 발행**한다.     |
| 토큰 재발행 | 사용자의 Access Token이 만료될 시,<br> Redis에 저장된 Refresh token 여부 및 Validation 통해 신원 확인 후 Access Token 재발행 |
|  로그아웃  | 사용자의 Access Token은 로그아웃 시 블랙리스트의 등록 및 Redis Refresh Token 삭제                                       |

<details>
<summary> 로그인 상세  접기/펼치기</summary>
<h3> Curl Request </h3>

```
curl -i -X POST https://artemoderni.com/api/user/auth/v1/login \
     -H "Content-Type: application/json" \
     -H "Access-Control-Allow-Origin: https://artemoderni.web.app" \
     -d '{
            "email":"[유저 이메일]",
            "password":"[계정 비밀번호]"
         }'
```

<h3> Http Request </h3>

```
> POST /api/user/auth/v1/login HTTP/2
> Host: artemoderni.com
> user-agent: curl/7.88.1
> accept: */*
> content-type: application/json
> access-control-allow-origin: https://artemoderni.web.app
> content-length: 55
```

<h3> Http Response </h3>

```
HTTP/2 200
content-type: text/plain;charset=UTF-8
content-length: 35
vary: Origin
vary: Access-Control-Request-Method
vary: Access-Control-Request-Headers
authorization: [Access Token]
set-cookie: refresh-token=[Refresh Token], httponly, secure, SameTime=None
```

<h3> Request Feilds </h3>

<table>
    <tr>
        <td> Path </td>
        <td> Type </td>
        <td> Description </td>
    </tr>
    <tr>
        <td> email </td>
        <td> String </td>
        <td> 이메일(ID 대체) </td>
    </tr>
    <tr>
        <td> password </td>
        <td> String </td>
        <td> 패스워드 </td>
    </tr>


</table>
<br>
</details>

<details>
<summary> 토큰 재발행 접기/펼치기</summary>

```
```

<br>
</details>
<details>
<summary> 로그인 상세  접기/펼치기</summary>

```
```

<br>
</details>

## User Service 기능

|       기능       | 내용                                                     |
|:--------------:|:-------------------------------------------------------|
|      회원가입      | 사용자로부터 회원 정보를 입력받아 회원가입을 수행한다.                         |
|    인증 코드 확인    | Redis에 등록된 유저의 이메일 인증코드와 일치 여부를 판독한다.                  |
|     계좌 연동      | Mock 서버와 통신을 통해 해당 유저의 계좌를 사이트와 연동한다.                  |
|    계좌 잔액 조회    | Mock 서버와 API 통신을 통해 유저 계좌의 잔액과 사용되고 있는 잔액에 대한 정보를 얻는다. |
|    내 정보 보기     | 사용자가 회원 가입 시 입력한 정보를 조회할 수 있다.                         |
|    Feign 통신    | 내부적으로 기능 수행하기 위한 사용자의 정보를 OpenFeign 통신을 통해 제공한다.       |
| 유저 전체 조회 (관리자) | 관리자가 사용자의 모든 정보를 조회할 수 있다.                             |

<details>
<summary> 회원 가입 접기/펼치기</summary>

```
```

<br>
</details>

<details>
<summary> 인증 코드 접기/펼치기</summary>

```
```

<br>
</details>

<details>
<summary> 계좌 연동 접기/펼치기</summary>

```
```

<br>
</details>

<details>
<summary> 계좌 잔액 조회 접기/펼치기</summary>

```
```

<br>
</details>

<details>
<summary> 관리자 유저 전체 조회 접기/펼치기</summary>

```
```

<br>
</details>



