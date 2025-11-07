# JRE 17 (경량 안정)
FROM eclipse-temurin:17-jre

WORKDIR /app

# 헬스체크 등 유틸
RUN apt-get update -y \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

COPY build/libs/*-SNAPSHOT.jar /app/app.jar


EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
