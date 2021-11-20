cls
@echo off
echo Script de instalacao de ambiente do Javino para Windows
echo.
echo Este script realiza o download e instalacao das dependencias do Javino
echo.
set /p choice="Deseja Continuar? (Digite [S] para confirmar):  "

IF "%choice%"=="S" (
	goto inicio
) ELSE IF "%choice%"=="s" (
	goto inicio
) ELSE (
	echo.
	echo Instalacao Cancelada.
	pause
	exit
)

:inicio
MD %HOMEDRIVE%%HOMEPATH%\javino
CD %HOMEDRIVE%%HOMEPATH%\javino

IF NOT EXIST %homedrive%%homepath%\javino\javino-dependences.zip bitsadmin /transfer Javino_DEPS /download /priority HIGH https://turing.pro.br/kadupantoja/t00ls/javino-dependences.zip %homedrive%%homepath%\javino\javino-dependences.zip
IF NOT EXIST %homedrive%%homepath%\javino\7z.exe bitsadmin /transfer 7Zip /download /priority HIGH https://turing.pro.br/kadupantoja/t00ls/7z.exe %homedrive%%homepath%\javino\7z.exe

7z.exe x javino-dependences.zip

SETX JAVINO_DEPS "%HOMEDRIVE%%HOMEPATH%\javino\javino-dependences\"

echo.
echo Script de instalacao de ambiente do Javino para Windows
echo.
echo Fim!
echo.
pause
exit
