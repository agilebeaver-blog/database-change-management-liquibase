# Repository database-change-management-liquibase

This is the repository containing example code for the blog post https://blog.agilebeaver.com/database-change-management-liquibase.

In this example we will see how we can change our database schema alongside with the code changes, both maintained in the same code repository.

## Building the example

This is a small Kotlin application built with spring boot and JDK 21. The latter is the only thing you have to install to run the application.

The build system uses gradle, if you use the gradle wrapper the required version of gradle will be downloaded and installed in the project directory prior to building the application for the first time.

To run the example application you have to access a running PostgreSQL database, which you can create with the docker commands below.

### Building the application

    ./gradlew clean build

### Running the application

    ./gradlew bootRun --args='--spring.profiles.active=local'

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