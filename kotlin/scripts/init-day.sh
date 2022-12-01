#!/usr/bin/env bash
set -euo pipefail

YEAR=$1
printf -v DAY "%02d" "$2"

MODULE="year$YEAR"
SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

printf -v DEST_MAIN "%s/src/main/kotlin/day%s/Day%s.kt" "$MODULE" "$DAY" "$DAY"

mkdir -p "$MODULE/src/main/kotlin/day$DAY"
sed \
  -e "s/%%DAY%%/$DAY/g" \
  "$SCRIPT_DIR/templates/Day.kt.template" > "$DEST_MAIN"


printf -v DEST_TEST "%s/src/test/kotlin/day%s/Day%sTest.kt" "$MODULE" "$DAY" "$DAY"

mkdir -p "$MODULE/src/test/kotlin/day$DAY"
sed \
  -e "s/%%DAY%%/$DAY/g" \
  "$SCRIPT_DIR/templates/DayTest.kt.template" > "$DEST_TEST"
