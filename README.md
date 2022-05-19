# Author

**author**: David S Teles

**email**: david.ds.teles@gmail.com

Hello, my name is David Teles. I'm a software engineer. You can find me on [Linkedin](https://www.linkedin.com/in/david-teles/?locale=en_US). 

Feel free to send me a message.

# About

This is a starter point for springboot projects. It has basic examples and configurations that you maybe want in your projects.

You'll find here:

* multiple module project structure
* service discovery
* tracing
* openfeign
* api gateway
* layers communication using dependency injection.
* interceptors for exceptions and language
* i18n configurations as well as messages bundles to both spring and hibernate validation changing dynamically.
* multiple profiles to separate environment.
* tests of all layers

## Swagger
To access the open api specification and browser on api docs.

```
http://{HOST}:{PORT}/swagger-ui.html
```

## Profiles

to run springboot microservices with a specific profile

```
/gradlew :discovery:bootRun --args='--spring.profiles.active=docker'
```

# Docker

You can try all at once using docker compose file. 

To build up and run locally include build fresh new image

```
    ./gradlew clean build && docker compose -f docker-compose-local.yml up -d
```

To run using remote repository and download latest docker image

```
    docker compose -f docker-compose-all.yml up -d
```

To run infrastructure dependencies only. That way you can run a microservices locally
from IDE and develop its features.

```
    docker compose up -d
```

## Build Docker Images With Spring Plugin
This is one way to build a microservice docker image.

To generate docker image from modules we can use spring boot plugin to do that.

```
./gradlew clean build bootBuildImage
```

For a specific module, we can use the command

```
./gradlew :product:clean build bootBuildImage
```

## Build Docker Images With Jib

This is another way to build a docker image.

To generate docker image and publish it to docker registry automatic.

The jib plugin is configured in those project who need an image

```
./gradlew jib
```

For a specific module, we can use the command

```
./gradlew :product:jib
```
