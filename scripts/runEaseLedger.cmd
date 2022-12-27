@echo off

set JAVA_ROOT=C:\Java
set APP_PATH=..
set LIB_DIR=..\lib
set UTIL_JAR=..\..\..\Utility\source\lib\uhillsUtils.jar
set JAR_NAME=easeledger.jar
set ECLIPSE_PLUGIN_DIR=C:\Java\eclipse3.1\plugins
set APP_NAME=EaseLedger
set SWT_DLL_DIR=%ECLIPSE_PLUGIN_DIR%
set MAIN_CLASS=com.uhills.finance.easetax.main.ApplicationMain
set JAVA=java

set CLASSPATH=%LIB_DIR%\%JAR_NAME%
set CLASSPATH=%CLASSPATH%;%UTIL_JAR%
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

echo Running %APP_NAME%...

rem pushd %APP_PATH%

%JAVA% -Djava.library.path=%SWT_DLL_DIR% -Dorg.jfree.report.LogLevel=Warn -classpath %CLASSPATH% %MAIN_CLASS%

rem popd
