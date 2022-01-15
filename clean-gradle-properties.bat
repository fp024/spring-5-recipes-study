@ECHO remove gradle.properties, settings.properties to sub chapers.
@ECHO OFF
FOR /l %%i IN (2,1,9) DO (
  IF EXIST chap0%%i\gradle.properties (
    del chap0%%i\gradle.properties
  )
  IF EXIST chap0%%i\settings.gradle (
    del chap0%%i\settings.gradle
  )
)
FOR /l %%i IN (10,1,17) DO (
  IF EXIST chap%%i\gradle.properties (
    del chap%%i\gradle.properties
  )
  IF EXIST chap0%%i\settings.gradle (
    del chap%%i\settings.gradle
  )
)
@ECHO remove is complete.
PAUSE
