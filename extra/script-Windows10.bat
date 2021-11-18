cls
@echo off
echo Script de instalacao de ambiente do Javino para Windows
echo.
echo Este script realiza o download e instalacao do EclipseIDE, Java JDK-11 e Python 3
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
pause



:inicio
MD %HOMEDRIVE%%HOMEPATH%\Javino

cls
echo Script de instalacao de ambiente do Javino para Windows
echo.
set /p choice="Deseja realizar o download e Instalacao do Eclipse IDE? (Digite [S] para confirmar):  "

IF "%choice%"=="S" (
	goto eclipse	
) ELSE IF "%choice%"=="s" (
	goto eclipse
) ELSE (
	goto ifjdk
)

:eclipse
echo Iniciando o Download do Eclipse
IF NOT EXIST %homedrive%%homepath%\Javino\eclipse.exe bitsadmin /transfer Eclipse /download /priority FOREGROUND http://javino.turing.pro.br/t00ls/W10/eclipse-inst-jre-win64.exe %homedrive%%homepath%\Javino\eclipse.exe
echo.
timeout 2 > NUL
echo Download concluido, iniciando o instalador. Siga as orientacoes do instalador.
%homedrive%%homepath%\Javino\eclipse.exe
timeout 15 > NUL
echo.
pause

:ifjdk
cls
echo Script de instalacao de ambiente do Javino para Windows
echo.
set /p choice="Deseja realizar o download e Instalacao do Java JDK 11? (Digite [S] para confirmar):  "
IF "%choice%"=="S" (
	goto jdk
) ELSE IF "%choice%"=="s" (
	goto jdk
) ELSE (
	goto ifpython
)

:jdk
echo Iniciando o Download do Java JDK 11
IF NOT EXIST %homedrive%%homepath%\Javino\jdk11.exe bitsadmin /transfer JDK-11 /download /priority FOREGROUND http://javino.turing.pro.br/t00ls/W10/jdk-11.0.12_windows-x64_bin.exe %homedrive%%homepath%\Javino\jdk11.exe
echo.
timeout 2 > NUL
echo Download concluido, iniciando o instalador. Siga as orientacoes do instalador.
%homedrive%%homepath%\Javino\jdk11.exe
timeout 15 > NUL
echo.
pause

IF NOT EXIST "%PROGRAMFILES%\Java\jdk-11.0.12\bin\javac.exe" goto varerro
echo.
echo Adicionando Variavel de Ambiente JAVA_HOME
SETX PATH "%PATH%;%PROGRAMFILES%\Java\jdk-11.0.12\bin"
SETX JAVA_HOME "%PROGRAMFILES%\Java\jdk-11.0.12"
goto varok

:varerro
echo.
echo ATENCAO: Configure MANUALMENTE as variaveis de ambiente do JAVA (JAVA_HOME e PATH)
echo Nao foi encontrado o diretorio %PROGRAMFILES%\Java\jdk-11.0.12
pause

:varok
echo.
pause



:ifpython
cls
echo Script de instalacao de ambiente do Javino para Windows
echo.
set /p choice="Deseja realizar o download e Instalacao do Python 3? (Digite [S] para confirmar):  "
IF "%choice%"=="S" (
	goto python
) ELSE IF "%choice%"=="s" (
	goto python
) ELSE (
	goto fim
)

:python
echo Iniciando o Download do Python 3
IF NOT EXIST %homedrive%%homepath%\Javino\python3.exe bitsadmin /transfer Python /download /priority FOREGROUND http://javino.turing.pro.br/t00ls/W10/python-3.10.0-amd64.exe %homedrive%%homepath%\Javino\python3.exe
echo.
timeout 2 > NUL
echo Download concluido, iniciando o instalador. Siga as orientacoes do instalador.
%homedrive%%homepath%\Javino\python3.exe
timeout 15 > NUL
echo.
pause

:fim
cls
echo Script de instalacao de ambiente do Javino para Windows
echo.
echo Fim!
echo.
pause
exit
