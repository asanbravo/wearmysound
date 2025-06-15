#!/usr/bin/env bash

ID=$(grep -r -o -E --include \*.feature "\[ID-[[:digit:]]+" * | cut -d':' -f2 | cut -d'-' -f2 | sort -n -r | head -1)
echo "Last test: ID-$ID"