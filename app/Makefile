setup:
	./gradlew installDist

run:
	./gradlew run

build-css:
	npm ci
	npm run build:css

start:
	npx --no concurrently --kill-others --names css,app \
		"npm run watch:css" \
		"APP_ENV=development ./gradlew run"