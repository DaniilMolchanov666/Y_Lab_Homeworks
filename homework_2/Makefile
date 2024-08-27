
.DEFAULT_GOAL := build

run:
	docker compose up -d && gradle run

build:
	gradle build

test:
	gradle test

build-run: build

.PHONY: build