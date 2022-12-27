@echo off

date /t
time /t

set JAVA_ROOT=C:\Java
set ECLIPSE_PLUGIN_DIR=C:\Java\eclipse3.1\plugins
set UTIL_JAR=..\..\Utility\source\lib\uhillsUtils.jar
set APP_PATH=..
set SRC_DIR=src
set BIN_DIR=bin
set LIB_DIR=lib
set SRC_FILE_LIST=srclist.txt
set SERIAL_ID_FILE=serialIds.txt
set APP_NAME=EaseLedger
set JAR_NAME=easeledger.jar
set ICONS_PATH=com\uhills\finance\easetax\graphics\icons
set IMAGES_PATH=com\uhills\finance\easetax\graphics\images
set MANIFEST_FILE=mainClass.mf
set JAVA_HOME=C:\Java\jsdk1.4\bin

set CLASSPATH=%UTIL_JAR%
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jaf\activation.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\javamail\mail.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\gnu.regexp-1.1.4\lib\gnu-regexp-1.1.4.jar
set CLASSPATH=%CLASSPATH%;%ECLIPSE_PLUGIN_DIR%\org.eclipse.jface_3.1.0.jar
set CLASSPATH=%CLASSPATH%;%ECLIPSE_PLUGIN_DIR%\org.eclipse.core.runtime_3.1.0.jar
set CLASSPATH=%CLASSPATH%;%ECLIPSE_PLUGIN_DIR%\org.eclipse.swt.win32.win32.x86_3.1.0.jar
set CLASSPATH=%CLASSPATH%;%ECLIPSE_PLUGIN_DIR%\org.eclipse.ui.workbench_3.1.0.jar
set CLASSPATH=%CLASSPATH%;%ECLIPSE_PLUGIN_DIR%\org.eclipse.osgi_3.1.0.jar
set CLASSPATH=%CLASSPATH%;%ECLIPSE_PLUGIN_DIR%\org.eclipse.core.boot_3.1.0.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jfreereport\jfreereport-all.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jfreereport\lib\bsh.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jfreereport\lib\gnujaxp.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jfreereport\lib\itext.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jfreereport\lib\jcommon.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jfreereport\lib\pixie.jar
set CLASSPATH=%CLASSPATH%;%JAVA_ROOT%\jfreereport\lib\poi.jar

pushd %APP_PATH%

dir /b /s %SRC_DIR%\*.java > %SRC_FILE_LIST%

rem Get rid of those pesky Source Safe files...
del /f /s /q *.scc

rem del /f /s /q %BIN_DIR%\*
if exist %BIN_DIR%\com rmdir /s /q %BIN_DIR%\com

if "%1" == "nodebug" goto nodebug

:debug
echo Building %APP_NAME% (Includes debug info)...
%JAVA_HOME%\javac -g -classpath %CLASSPATH% -d %BIN_DIR% @%SRC_FILE_LIST%
if errorlevel 1 goto exit
goto archive

:nodebug
echo Building %APP_NAME% (Optimized, No debug info)...
%JAVA_HOME%\javac -g:none -O -classpath %CLASSPATH% -d %BIN_DIR% @%SRC_FILE_LIST%
if errorlevel 1 goto exit
goto archive

:archive
xcopy /s /y /i /q %SRC_DIR%\%ICONS_PATH% %BIN_DIR%\%ICONS_PATH%
xcopy /s /y /i /q %SRC_DIR%\%IMAGES_PATH% %BIN_DIR%\%IMAGES_PATH%

echo Manifest-Version: 1.0> %MANIFEST_FILE%
echo Main-Class: com.uhills.finance.easetax.main.ApplicationMain>> %MANIFEST_FILE%
echo.>> %MANIFEST_FILE%

%JAVA_HOME%\jar -cmf %MANIFEST_FILE% %LIB_DIR%\%JAR_NAME% -C %BIN_DIR% .
if errorlevel 1 goto exit

:serialIds
echo Capturing Serialization Ids...
echo Serialization Ids > %SERIAL_ID_FILE%
date /t >> %SERIAL_ID_FILE%
time /t >> %SERIAL_ID_FILE%
echo. >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.Address                       >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.Category                      >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.Company                       >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.CompanyType                   >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.Contact                       >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.ContactType                   >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.DateFilterType                >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.Report                        >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.ReportType                    >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.TaxCode                       >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.Transaction                   >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.TransactionType               >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.core.JobCode                       >> %SERIAL_ID_FILE%

serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.persist.memory.JobCodeTable        >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.persist.memory.CategoryTable       >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.persist.memory.CompanyTable        >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.persist.memory.ContactTable        >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.persist.memory.ReportTable         >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.persist.memory.TransactionTable    >> %SERIAL_ID_FILE%

serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.persist.file.FileDatabase          >> %SERIAL_ID_FILE%

serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.filtersort.SortCriteria            >> %SERIAL_ID_FILE%
serialver -classpath %LIB_DIR%\%JAR_NAME%;%UTIL_JAR% com.uhills.finance.easetax.filtersort.FilterCriteria          >> %SERIAL_ID_FILE%

:exit

popd

