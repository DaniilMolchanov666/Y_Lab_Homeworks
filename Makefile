.DEFAULT_GOAL := build

run:
	make -C homework_3 run

build:
	make -C homework_3 build

test:
	make -C homework_3 test

build-run: build run

.PHONY: build