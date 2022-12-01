#!/usr/bin/env bash
set -euo pipefail

YEAR=$1
DAY=$2

printf -v DEST "./year%s/src/main/resources/input" "$YEAR"
printf -v FILE "%s/day%02d.txt" "$DEST" "$DAY"

mkdir -p "$DEST"
curl https://adventofcode.com/"$YEAR"/day/"$DAY"/input --cookie session="$AOC_SESSION" -o "$FILE"
