# 使用 OpenJDK 17 作为运行阶段的基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制构建阶段生成的 JAR 文件到运行阶段
COPY /target/homework-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口（Spring Boot 默认使用 8080 端口）
EXPOSE 8080

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]