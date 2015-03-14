

# Javac compiler not found #
Eclipse will give the following error when trying to build:
```
projectFilepath/build.xml:76: Unable to find a javac compiler;
com.sun.tools.javac.Main is not on the classpath.
Perhaps JAVA_HOME does not point to the JDK
```

## Fixes ##
Use one of the two:

### 1. Set the JAVA\_HOME environment variable to point to the JDK on your machine ###
#### In Windows ####
  * Click Start, right-click on Computer and select Properties
  * Click Advanced System Settings, then Environment Variables in the window that pops up.
  * In the System Variables box, find the JAVA\_HOME variable and click edit.  If there is not a variable named this, create a new one.
  * Set the value to be the path to your JDK.  For example:
```
C:/Program Files (x86)/Java/jdk1.6.0_23/bin
```

**Note:** The official Android development guide states the following:

> Note: When installing JDK on Windows, the default is to install in the
> Program Files" directory. This location will cause ant to fail,
> because of the space. To fix the problem, you can specify the JAVA\_HOME
> variable like this: `set JAVA_HOME=c:\Progra~1\Java\<jdkdir>`. The
> easiest solution, however, is to install JDK in a non-space directory,
> for example: `c:\java\jdk1.6.0_02`.

So if the above doesn't work for you, try that.

#### In Linux ####
Open a terminal and execute the export command. For example:
```
export JAVA_HOME=/usr/java/jdk1.5.0_07/bin/java
```

### 2. Add tools.jar to classpath ###
_Note that although this works, it has not been confirmed it's a legitimate, perfect fix._
  * In eclipse, click Window->Preferences.
  * In the resulting window on the left hand side, click Ant->Runtime.
  * Add an external JAR. Add <your JDK directory>/lib/tools.jar

### 3. Adding Android SDK to PATH ###
#### In Mac ####
  1. Open Terminal and execute export command
> export PATH=${PATH}:path.to.your.tools
> > path.to.your.tools could be /Users/derp/android-sdk/tools
> > > where tools could contain android, adb, emulator, etc functions

  1. Open Terminal and check on your home dir if you have a .bash\_profile

> > If not, then create it using touch .bash\_profile
> > Then add in the command from #1 and it should use that command each time
> > > a new terminal is opened so you don't have to redo it all the time.

#### In Linux ####

> Essentially the same as mac except the file is a bashrc.
> > Not positive, a confirmation is needed.

# Importing jars into multiple projects #
This issue takes the form of an IllegalAccessException (for reasons unknown to us..) when trying to run the test suite.  This happens because a library is included in the build path of both the main project and the test suite project.

To fix this problem:
  * right click on the Luminance project and go Build Path->Configure Build Path...
  * find the library in question.  The one that has been causing the issue is the vector jar.
  * click the export tab and ensure this jar is selected.
  * now go to the build path of the test suite
  * make sure the library you just exported is NOT included in the build path of this project.

### Build.xml error help not found ###

**remove default ="help" from build.xml**
