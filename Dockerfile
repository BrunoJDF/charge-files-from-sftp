FROM adoptopenjdk/openjdk11:alpine-slim
ENV HOST_DB=jobDb \
    USER_DB=root \
    PWD_DB=root \
    ##DB=jobDb \
    SFTP_HOST=sftp \
    SFTP_PORT=22 \
    SFTP_USER=foo \
    SFTP_PWD=pass \
    FOLDER_LOCAL="/data/file/upload/" \
    FOLDER_REMOTE="/manage_file/upload/" \
    FOLDER_TEM="/manage_file/upload/temp/" \
    PORT=9090
USER root
RUN mkdir -p /data/file/upload/
COPY target/fileAttachment-1.jar /fileAttachment-1.jar
ENTRYPOINT ["java", "-jar", "/fileAttachment-1.jar"]