#!/usr/bin/env bash

docker_image_name=demo-app

function demo_app_up() {

  docker run -d -it -p 8080:8090 \
  --network=compose_default \
  --name $docker_image_name $docker_image_name
}

function demo_app_down() {
  docker logs -t $docker_image_name >"$docker_image_name".log
  docker rm -f $docker_image_name
}
