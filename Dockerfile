FROM postgres:16.4-alpine3.20
LABEL authors="agilebeaver"

ENV POSTGRES_DB="agilebeaver"
ENV POSTGRES_USER="agile"
ENV POSTGRES_PASSWORD="beaver"

