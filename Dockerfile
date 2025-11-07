# 런타임: Debian/Ubuntu 계열 JRE 17
FROM eclipse-temurin:17-jre

# 작업 디렉터리
WORKDIR /app

# 헬스체크용 curl 설치 (우분투/데비안 계열)
RUN apt-get update -y \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

# Spring Boot JAR 복사 (Gradle 산출물 기본 위치)
# *-SNAPSHOT.jar가 아닐 수도 있으니 *.jar 전체를 대상으로 하고 app.jar로 이름 고정
COPY build/libs/*.jar /app/app.jar

# 외부 노출 포트
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app/app.jar"]
