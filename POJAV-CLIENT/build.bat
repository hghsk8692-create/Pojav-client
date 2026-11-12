@echo off
setlocal
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle was not found.
  echo Install Gradle 9.2+ and make sure "gradle" is available in PATH,
  echo or open this project in IntelliJ IDEA and use its Gradle build task.
  pause
  exit /b 1
)
call gradle build
if errorlevel 1 (
  echo.
  echo Build failed. Check the Gradle error above.
  pause
  exit /b 1
)
echo.
echo Build complete. Look in build\libs\ for the mod JAR.
pause
