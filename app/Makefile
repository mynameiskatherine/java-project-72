run-dist:
	./build/install/app/bin/app

run:
	./gradlew run

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

check-deps:
	./gradlew dependencyUpdates -Drevision=release

dev:
	./gradlew run

setup:
	gradle wrapper --gradle-version 8.3

clean:
	./gradlew clean

build:
	./gradlew clean build

start: dev

install:
	./gradlew installDist

test:
	./gradlew test

image-build:
	docker build -t hexletcomponents/java-javalin-example:latest .

image-push:
	docker push hexletcomponents/java-javalin-example:latest

build-run: build run

.PHONY: build
