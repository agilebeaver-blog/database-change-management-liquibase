# Repository database-change-management-liquibase

This is the repository containing example code for the blog post https://blog.agilebeaver.com/database-change-management-liquibase.

In this example we will see how we can change our database schema alongside with the code changes, both maintained in the same code repository.

## Building the example

This is a small Kotlin application built with Spring Boot 3 and JDK 21. The latter is the only thing you have to install to run the application.

The build system uses gradle, if you use the gradle wrapper the required version of gradle will be downloaded and installed in the project directory prior to building the application for the first time.

To run the example application you have to access a running PostgreSQL database, which you can create with the docker commands below.

### Building the application

    ./gradlew clean build

### Running the application

    ./gradlew bootRun --args='--spring.profiles.active=local'

## Accessing the REST controller

If you have ``curl`` installed on your machine you can access the REST controller by simply using the following expression:

    curl http://localhost:8088/agilebeaver/api/users/

You will see something like

    [{"uuid":"d6025806-ffd2-48cc-8ed0-4bfc8e156554","login":"admin","email":"thebeaveradmin@agilebeaver.com","roles":["admin"]},{"uuid":"1baf8299-ca73-403c-af0e-257cd00d0ff0","login":"agilebeaver","email":"agilebeaver@agilebeaver.com","roles":["standard"]}]

This is not nicely formatted, but you can see the tow users inserted into the database on startup.

> Make sure to have the last _slash_ present, otherwise you will receive a HTTP 404 error (with Spring Boot 3 you have to be precise with your definition and the urls you use).

Now you can get any of the users by appending the UUID to the url, for example

    curl http://localhost:8088/agilebeaver/api/users/1baf8299-ca73-403c-af0e-257cd00d0ff0

will return

    {"uuid":"1baf8299-ca73-403c-af0e-257cd00d0ff0","login":"agilebeaver","email":"agilebeaver@agilebeaver.com","roles":["standard"]}

See the difference with the results: the first request returns a list of users in the form of \[user1, user2\], the latter returns a single user entity.

> If you want to have the result in a more readable output, try ``jq``. This is the result if you use pretty printing with _jq_:
    
    curl http://localhost:8088/agilebeaver/api/users/ | jq '.'
    % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
    Dload  Upload   Total   Spent    Left  Speed
    100   253    0   253    0     0   1144      0 --:--:-- --:--:-- --:--:--  1144
    [
        {
            "uuid": "d6025806-ffd2-48cc-8ed0-4bfc8e156554",
            "login": "admin",
            "email": "thebeaveradmin@agilebeaver.com",
            "roles": [
                "admin"
            ]
        },
        {
            "uuid": "1baf8299-ca73-403c-af0e-257cd00d0ff0",
            "login": "agilebeaver",
            "email": "agilebeaver@agilebeaver.com",
            "roles": [
                "standard"
            ]
        }
    ]


## Starting, stopping and removing a local database with docker

We have prepared a simple PostgreSQL database to be used with this application.

### Creating the image

You can create a docker image using the following commands (don't miss the dot at the end of the command).

    docker build --tag agilebeaver-db .

### Starting the image

To start a docker container running this image use the command

    docker run --detach -p 5432:5432 agilebeaver-db

You can name the image any name you want to, and if you prefer another port you can use any other port you want (and which is available on your local system). And remember that ports 1024 and below are reserved for root user.

You can see the running container with the following command.

    docker container ls

It should print something like this:

    CONTAINER ID   IMAGE                      COMMAND                  CREATED          STATUS             PORTS                                         NAMES
    0d31b0260334   agilebeaver-db             "docker-entrypoint.s…"   11 minutes ago   Up 2 minutes       0.0.0.0:5432->5432/tcp, :::5432->5432/tcp     nifty_curran

With these settings you can access the database using the url ``jdbc:postgresql://localhost:5432/agilebeaver`` with the user ``agile`` and password ``beaver``.

### Inspect the running container

This is helpful to retrieve some information about the container and docker attributes around the container. If you want to be sure to remove the right volume after removing the container, you can inspect the running container and grep for the necessary information.

    docker container inspect nifty_curran | grep "var/lib/docker/volumes"

This will print something like the following:

    "Source": "/var/lib/docker/volumes/3aa7c65b7fabce9f8c5d14d1e3bbbe357c389baea8c0c3b5f23474c55fdb9911/_data",

So now we know the volume to be removed is ``3aa7c65b7fabce9f8c5d14d1e3bbbe357c389baea8c0c3b5f23474c55fdb9911`` (the directory name in the ``volumes`` directory).

### Stopping and removing a running container

To stop the container you have to know it's name (seen with the ``ls``-command), for example

    docker container stop nifty_curran

Then you can remove the container:

    docker container rm nifty_curran

And to completely get rid of the data you have to remove the volume.

    docker volume rm 3aa7c65b7fabce9f8c5d14d1e3bbbe357c389baea8c0c3b5f23474c55fdb9911