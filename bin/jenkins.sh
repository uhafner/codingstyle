# Make sure that docker runs with the current UID to avoid permission problems on volume docker/volumes/jenkins-home
CURRENT_UID="$(id -u)"
export CURRENT_UID

CURRENT_GID="$(id -g)"
export CURRENT_GID

CURRENT_USER="$(id -u):$(id -g)"
export CURRENT_USER

echo Running docker compose with user ID $CURRENT_USER

docker pull jenkins/jenkins:lts-jdk21
docker compose build --pull
docker compose up --always-recreate-deps
