#!/usr/bin/env bash

function wait_for_service_up() {
  local retries=1
  local max_retries=50
  local service_health_url="http://localhost:8090/actuator/health"

  echo "Checking service health at: ${service_health_url}"

  until curl --silent --fail --output /dev/null "${service_health_url}"; do
    if [[ ${retries} -gt ${max_retries} ]]; then
      echo "Service did not start after ${max_retries} retries."
      return 1
    fi

    echo "Waiting for service to be up... Retry ${retries}/${max_retries}"
    ((retries++))
    sleep 5
  done

  echo "Service is UP!"
}
