# Simple blog

This is just a simple blog software as a small tech demo demonstrating Quarkus with PostgresSQL and
JavaScript using the fetch api.

## How to Get Started

The application is configured for the database specified in the accompanying docker-compose.yml file.
If you want to use a different database, you need to modify the
[application.properties](src/main/resources/application.properties) file.

## Starting the PostgreSQL database in a Docker container

To start the database, you can use either Docker Desktop or IntelliJ (Services), or navigate to the root directory of
the project on the command line:


```bash
$ docker compose up --detach
```

This command starts the database in the background. To check its status, use the following command:

```bash
$ docker ps --all
```

To stop the database, use the following command:

```bash
$ docker compose down
```

The container is configured to store the database data in a Docker volume, so the data will be retained when the
database is started again.

To view the volumes, use the following command:

```bash
$ docker volume ls
```

To delete the data volumes, use the following command:

```bash
$ docker volume rm mysimpleblog_my_simple_blog_db_data
```

## Starting the application

Now you can compile the Spring Boot application with:

```bash
$ mvn clean install
```

If the database is available and configured, you can start the application with the following command:

```bash
$ mvn quarkus:run
```

The application creates the database schema if it does not exist and executes the
[data-postgres.sql](src/main/resources/data-postgres.sql) on the database.
This will create two users: *alice* (password: *alice*) and *bob* (password: *bob*).
The passwords are generated using Bcrypt with 12 rounds, and
you can generate them here: <https://bcrypt-generator.com/>.


## Accessing Swagger-UI

You can test the complete functionality using the Swagger-UI, which is
available at <http://localhost:8080/swagger-ui/>.

The endpoints <http://localhost:8080/users/me> and POST on <http://localhost:8080/entries> require authentication.
You can provide authentication by clicking the *Authorize* button at the top right.

## Authentication via HTTP

Only simple HTTP Basic authentication has been implemented here. This should not be used in production,
especially without SSL, as the passwords are sent in plain text and can be intercepted.

To send the login credentials via HTTP, you need to set the *Authorization* header with the value `Basic`,
followed by a space, the base64-encoded username, a colon, and the base64-encoded password.

For *alice:alice*, the header would be:
```
Authorization: Basic YWxpY2U6YWxpY2U=
```

And for *bob:bob*, the header would be:
```
Authorization: Basic Ym9iOmJvYg==
```

