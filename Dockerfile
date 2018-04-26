FROM openjdk:8-alpine

COPY . /usr/src/hotpot_codeG

WORKDIR /usr/src/hotpot_codeG

CMD ["sh", "start.sh"]
