.DEFAULT_GOAL := build

run:
	make -C homework_1 run

build:
	make -C homework_1 build

test:
	make -C homework_1 test

build-run: build run

.PHONY: build