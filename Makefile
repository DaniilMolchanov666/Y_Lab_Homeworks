.DEFAULT_GOAL := build

run:
	make -C homework_2 run

build:
	make -C homework_2 build

test:
	make -C homework_2 test

build-run: build run

.PHONY: build