#!/bin/sh
echo "clean gradle.properties, gradle wrapper to sub chapers."

PROJECT_FOLDER_LIST=$(sed 's/\\/\//g' "project-folder-list.txt")

for folder in $PROJECT_FOLDER_LIST;
do
  rm $folder/gradle.properties

  rm -r $folder/gradle
  rm $folder/gradlew
  rm $folder/gradlew.bat

done

echo "remove is complete."
