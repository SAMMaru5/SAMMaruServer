FROM gradle:7-jdk11

WORKDIR /SAMMaruServer
COPY . /SAMMaruServer

RUN ["gradle", "-x", "test" , "build"]
CMD ["gradle", "bootRun"]

ENV SPRING_DATASOURCE_URL jdbc:mysql://mysql:3306/sammaru?serverTimezone=UTC&characterEncoding=UTF-8
ENV SPRING_DATASOURCE_USERNAME sammaru
ENV SPRING_DATASOURCE_PASSWORD sammaru
