#!/bin/sh
echo "copy gradle.properties, settings.properties, gradle wrapper to sub chapers."

PROJECT_FOLDER_LIST=$(sed 's/\\/\//g' "project-folder-list.txt")

for folder in $PROJECT_FOLDER_LIST;
do
  cp gradle_properties_for_sub_project.properties $folder/gradle.properties
  
  mkdir -p $folder/gradle/wrapper/
  cp -r gradle/wrapper/* $folder/gradle/wrapper
  cp gradlew $folder
  cp gradlew.bat $folder
done

echo "copy is complete."
