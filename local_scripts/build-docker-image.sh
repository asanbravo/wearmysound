#!/usr/bin/env bash

function build_docker_image() {
  if [ ! -z $1 ] && [ $1 == "skipBuild" ]; then
    skipBuild='true'
  fi

  if [[ "$skipBuild" != 'true' ]]; then
    echo "Building docker image"
    git_repo=`git rev-parse --show-toplevel`
    cd $git_repo/code/boot
    ./local_scripts/build-service-docker-image.sh
    cd -
  fi
}
