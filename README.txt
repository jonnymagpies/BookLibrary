========================================================================================================
========================================== README ======================================================
========================================================================================================
==== 	Requirements																				====
====	1. Java 11+																					====
====	2. Maven compiler																			====
====	3. MySQL/MariaDB database																	====
====	4. OS Linux Ubuntu/Debian or use WSL on	Windows												====
====	5. IDE (STS or VSCode)(optional)															====
========================================================================================================
========================================================================================================
====	Installation																				====
====	1. Clone source from repository from the URL												====
====	2. Open Command Prompt/Powershell, change directory to the previously cloned repository		====
====	3. Using CMD/PS, type 'mvn clean compile install' to build the source into binary			====
====	4. Wait until the build process is finished													====
====	5. Go to target/BookLibrary-1.0.0-dist/BookLibrary-1.0.0 folder								====
====	6. Inside there are two folder (config and lib), a jar file (BookLibrary-1.0.0.jar),		====
====	   and a bash file (BookLibraryApp.sh)														====
====	7. To start/stop the app,type ./BookLibraryApp.sh [start/stop]								====
========================================================================================================
========================================================================================================
====	Preconfiguration																			====
==== 	open /src/main/resources/application.properties before build								====
====	OR 																							====
====	open config/application.properties after build												====
====	Instruction on which value to change is explained inside 									====
========================================================================================================
========================================================================================================