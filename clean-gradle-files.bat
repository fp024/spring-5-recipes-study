@ECHO remove gradle.properties, gradle wrapper to sub chapers.
@ECHO OFF

for /F "delims=" %%a in (project-folder-list.txt) do (
  del %%a\gradle.properties

  rmdir /S /Q %%a\gradle\
  del %%a\gradlew.bat
  del %%a\gradlew

)

@ECHO remove is complete.
PAUSE
