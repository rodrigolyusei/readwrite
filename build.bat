@echo off
REM =========================
REM Configuração do Athens
REM =========================

REM Exclui o diretório out se ele existir para garantir o clean build
IF EXIST out (
    echo Preparando clean build...
    rmdir /s /q out
)
REM Verifica se o diretório out/manual/rods existe e cria se necessário

IF NOT EXIST out\manual\rods (
    echo Criando o diretório out\manual\rods...
    mkdir out\manual\rods
)

REM Verifica se o arquivo manifest.txt do Cliente existe e cria se necessário
IF NOT EXIST out\manual\rods\manifest_athens.txt (
    echo Criando manifest_cliente.txt...
    (
        echo Manifest-Version: 1.0
        echo Main-Class: main.Athens
    ) > out\manual\rods\manifest_athens.txt
)

REM Navega até o diretório src
cd src

REM Compila o arquivo Athens.java e coloca os arquivos .class no diretório out/manual/rods
javac main\Athens.java -d ..\out\manual\rods
IF %ERRORLEVEL% NEQ 0 (
    echo Erro na compilação de Athens. Verifique o código.
    pause
    exit /b %ERRORLEVEL%
)

REM Volta para o diretório anterior
cd ..

REM Cria o arquivo athens.jar, usando o manifest_athens.txt e os arquivos do diretório out/manual/rods
jar cfm out\build\athens.jar out\manual\rods\manifest_athens.txt -C out\manual\rods\ .
IF %ERRORLEVEL% NEQ 0 (
    echo Erro ao criar o arquivo JAR do Athens. Verifique os arquivos.
    pause
    exit /b %ERRORLEVEL%
)

echo Operacao concluida com sucesso!
pause
