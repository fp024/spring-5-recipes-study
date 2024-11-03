@ECHO copy gradle.properties, gradle wrapper to sub chapers.
@ECHO OFF

for /F "delims=" %%a in (project-folder-list.txt) do (
  copy gradle_properties_for_sub_project.properties %%a\gradle.properties

  xcopy /S /Y gradle\wrapper\ %%a\gradle\wrapper\
  copy gradlew.bat %%a
  copy gradlew %%a
)

@ECHO copy is complete.
PAUSE
