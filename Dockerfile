FROM gradle:7-jdk11

WORKDIR /SAMMaruServer
COPY . /SAMMaruServer

RUN ["gradle", "-x", "test" , "build"]
CMD ["gradle", "bootRun"]