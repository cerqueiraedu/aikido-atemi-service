FROM node:alpine

LABEL version="v1.0"
LABEL description="aikido-atemi-service"

COPY server.js .
COPY ./api ./api
COPY ./test ./test
COPY newrelic.js .
COPY package.json .
COPY package-lock.json .

RUN apk --no-cache add --virtual native-deps \
  g++ gcc libgcc libstdc++ linux-headers make python && \
  npm install --quiet node-gyp -g &&\
  npm install --quiet && \
  apk del native-deps