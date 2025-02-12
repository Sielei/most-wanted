# Backend Dockerfile
FROM azul/zulu-openjdk:21 as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM azul/zulu-openjdk-alpine:21-jre
WORKDIR /app

# Add non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the jar from builder stage
COPY --from=builder --chown=spring:spring /app/target/*.jar app.jar

# Set container timezone
ENV TZ=UTC

# Container configuration
ENV JAVA_OPTS="\
    -XX:+UseG1GC \
    -XX:+UseContainerSupport \
    -XX:MaxRAMPercentage=75.0 \
    -XX:ActiveProcessorCount=2 \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/tmp/heapdump.hprof \
    -XX:+ExitOnOutOfMemoryError \
    -Xlog:gc*=info:file=/tmp/gc.log:time,uptime,level,tags:filecount=5,filesize=10m \
    -XX:+UseStringDeduplication \
    -XX:+OptimizeStringConcat \
    -Duser.timezone=UTC \
    -Djava.security.egd=file:/dev/./urandom \
    -Dfile.encoding=UTF-8"

# Spring-specific configuration
ENV SPRING_OPTS="\
    --server.tomcat.threads.max=50 \
    --server.tomcat.max-connections=200 \
    --spring.main.lazy-initialization=true \
    --server.tomcat.accept-count=100"

EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run with optimized settings
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar $SPRING_OPTS"]