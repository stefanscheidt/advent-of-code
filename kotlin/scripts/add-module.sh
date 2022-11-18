#!/usr/bin/env bash
set -euo pipefail

MODULE=$1

SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)

mkdir -p "$MODULE/src/main/kotlin"
mkdir -p "$MODULE/src/test/kotlin"
cp "$SCRIPT_DIR"/templates/build.gradle.kts "$MODULE"
echo "include(\"$MODULE\")" >> settings.gradle.kts
