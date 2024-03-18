# Library

Library back-end implemented using Kotlin, Ktor, and PostgreSQL.

## Containerization

Run images using `docker-compose.yml` file:

```shell
podman compose up -d
```

### Using ktor plugin

To build image:

```shell
./gradlew buildImage
```

To load image using Podman:

```shell
podman load --input build/jib-image.tar
```

To run image using Podman:

````shell
 podman run localhost/ktor-docker-image:latest  --env-file=./.env --expose=8080 --name=library
````