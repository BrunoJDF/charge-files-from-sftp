FROM adoptopenjdk/openjdk8
ENV SFTP_HOST=sftp \
    SFTP_PORT=22 \
    SFTP_USER=foo \
    SFTP_PWD=pass \
    FOLDER_LOCAL="/data/file/upload/" \
    FOLDER_REMOTE="/manage_file/upload/" \
    FOLDER_TEMP="/manage_file/upload/temp/" \
    PORT=9090
    ##HOST_DB=jobDb \
    ##USER_DB=root \
    ##PWD_DB=root \
    ##DB=jobDb \
USER root
RUN mkdir -p /data/file/upload/
COPY target/fileAttachment-1.jar /fileAttachment-1.jar
ENTRYPOINT ["java", "-jar", "/fileAttachment-1.jar"]