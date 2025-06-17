# Getting Started

## 1. Starting postgres in Docker

### bash command

docker run -d \
--name job_postgres \
-e POSTGRES_DB=jobdb \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=postgres \
-p 5432:5432 \
-v pgdata:/var/lib/postgresql/data \
postgres:15

### windows command

docker run -d `
--name job_postgres `
-e POSTGRES_DB=jobdb `
-e POSTGRES_USER=postgres `
-e POSTGRES_PASSWORD=postgres `
-p 5432:5432 `
-v pgdata:/var/lib/postgresql/data `
postgres:15

## 2 Choose thr profile of the application

In the application.yml, choose active: dev if you need more logs, active: prod if you don't. 

## 3. Starting scraper application locally

### Via Intellij idea

Just click the Run button on ScraperApplication class

### Via command line

build the project with the command: ./mvnw clean package
start the application with the command: java -jar target\demo-0.0.1-SNAPSHOT.jar
