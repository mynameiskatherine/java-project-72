clean:
	./gradlew clean

build:
	./gradlew clean build
	
install:
	./gradlew installDist
	
run-dist:
	./build/install/app/bin/app

run:
	./gradlew run

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain checkstyleTest

build-run: build run

.PHONY: build
.PHONY: test