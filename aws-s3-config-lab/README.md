## AWS S3 Laboratory with Spring Boot    

### Project     

--- 
* AWS S3 With Spring Boot

### Project Description

---
AWS S3 SDK Java 를 활용 하여 여러 ```Configuration``` 설정을 구현해 보고 설정 값의 동작을 S3 기능 (```Upload```, ```Delete``` 등)을 통해 확인 합니다.      

### Installation   

---
* Mac OS 및 Linux
```shell
./gradlew clean buiild
java -jar build/libs/aws-s3-config-lab-0.0.1-SNAPSHOT.jar
```       

### Progress     

---
(1) 기능 구현
- [x] Async S3 업로드 (Multipart 단건)
- [ ] Async S3 업로드 (Multipart 다건)
- [x] S3 업로드 (Multipart 단건)
- [x] Async S3 단건 파일 삭제
- [ ] Pre-Signed URL

(2) 공통 기능    
- [x] 사용자 로그 (Console)
- [ ] 에러 처리

(3) Configuration
- [x] Access, Secret Key 사용
- [x] STS (Security Token Service) 기반 + Role + 자동 갱신


---
