#!/bin/bash

JAR_NAME=threads32-1.0-SNAPSHOT.jar

mvn clean package


origFilesFolder=/opt/test-files
fileDest=/opt/test-files/samples

rm -rf ${fileDest}/*

function copyFiles {

    totalFiles=$1
    fileName=$2

    for (( i = 1; i <= $totalFiles; i++))
    do
        cp "${origFilesFolder}/${fileName}" "${fileDest}/${fileName}_${i}"
    done

}

for fileSuffix in "10mb" "20mb" "40mb"
do

    echo $fileSuffix

    for fileCount in 10 20 40
    do
        copyFiles ${fileCount} "file_"${fileSuffix}
        echo "[SYNC] Running [text] files... Count $fileCount !"
        java -jar target/${JAR_NAME} false false ${fileSuffix} "${fileDest}"
        echo "[SYNC] Running [text] files... Count $fileCount !"
        java -jar target/${JAR_NAME} true false ${fileSuffix} "${fileDest}"

        rm -rf ${fileDest}/*

        copyFiles ${fileCount} "binary_"${fileSuffix}
        echo "[SYNC] Running [binary] files... Count $fileCount !"
        java -jar target/${JAR_NAME} false true ${fileSuffix} "${fileDest}"
        echo "[ASYNC] Running [binary] files... Count $fileCount !"
        java -jar target/${JAR_NAME} true true ${fileSuffix} "${fileDest}"

    done

done