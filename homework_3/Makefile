
.DEFAULT_GOAL := build

run:
	docker compose up -d && gradle war

build:
	gradle build

test:
	gradle test

build-run: build

.PHONY: build