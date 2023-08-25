@ECHO remove IDE, Build Tools files to sub chapers.
@ECHO OFF

FOR /F "delims=" %%a in (project-folder-list.txt) DO (
  IF EXIST %%a\.idea\ rmdir /S /Q %%a\.idea\
  IF EXIST %%a\.gradle\ rmdir /S /Q %%a\.gradle\
  IF EXIST %%a\bin\ rmdir /S /Q %%a\bin\
  IF EXIST %%a\build\ rmdir /S /Q %%a\build\
)

@ECHO remove is complete.
PAUSE
