.DEFAULT_GOAL := build

run:
	make -C homework_4 run

build:
	make -C homework_4 build

test:
	make -C homework_4  test

build-run: build run

.PHONY: build