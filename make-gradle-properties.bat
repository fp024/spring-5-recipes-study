@ECHO copy gradle.properties, settings.properties to sub chapers.
@ECHO OFF
FOR /l %%i in (2,1,9) do (
  copy gradle.properties chap0%%i\
  IF EXIST chap0%%i\local-settings.gradle (
    copy chap0%%i\local-settings.gradle chap0%%i\settings.gradle
  )
)
FOR /l %%i in (10,1,17) do (
  copy gradle.properties chap%%i\
  IF EXIST chap%%i\local-settings.gradle (
    copy chap%%i\local-settings.gradle chap%%i\settings.gradle
  )
)
@ECHO copy is complete.
PAUSE
