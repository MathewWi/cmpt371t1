

# Introduction #
Everyone should try to use the same development environment unless you have a good reason not to. You'll need Eclipse, the Android SDK, the Eclipse TeamCity plugin, and the Eclipse ADT plugin.

# Get the tools #
## Eclipse ##
Download Eclipse Classic from http://www.eclipse.org.

## Java JDK ##
Download the Java JDK at http://www.oracle.com/technetwork/java/javase/downloads/index.html. _(I believe that Eclipse Classic should come with this already)_

If you download 64bit JDK sometimes the Android SDK installer won't recognize it.  Just install the 32bit JDK.

## Android SDK ##
  1. Go to http://developer.android.com/sdk/index.html and download the  Android SDK for your particular platform.
  1. If you're on Windows, install the SDK by running the downloaded executable. If you are on Mac OS X or Linux, unarchive the package and resulting SDK in a safe place. Note the location.

## ADT Plugin ##

### Installation ###
  1. Go to Eclipse's Help menu and select Install New Software...
  1. In the resulting window click on the Add... button and add this URL, https://dl-ssl.google.com/android/eclipse/, to the Location field. Name it whatever you want and click OK. Note: If Eclipse tells you that it's a duplicate location, you can skip this step.
  1. Make sure that the site you entered in the previous step is selected in the "Work with" field. If it isn't click on the down arrow at the end of the field and click on the site you added.
  1. You should now see a Developer Tools entry in the table in the center. You can click on the disclosure triangle to view the contents if you want, but we're going to install it all anyway. Just click on the checkbox beside "Developer Tools" and click Next. Follow the on-screen instructions and restart Eclipse when it prompts you to and you will have finished installing the ADT plugin for Eclipse.

### Configuration ###
  1. Go to Eclipse's preferences and select in the left pane.
  1. By the SDK Location field, click on the Browse... button.
  1. Find the location that you location that you placed the Android SDK and select that.

### Adding Platforms and Components ###
  1. In Eclipse, go to Window > Android SDK and AVD Manager.
  1. In the left pane, select Available packages.
  1. Check off SDK Platform Android 2.1 and 2.2. Also check off "Android SDK Tools, [revision 8](https://code.google.com/p/cmpt371t1/source/detail?r=8)", "Android SDK Platform-tools, [revision 1](https://code.google.com/p/cmpt371t1/source/detail?r=1)" and "Documentation for Android SDK, API 9, [revision 1](https://code.google.com/p/cmpt371t1/source/detail?r=1)"

### Make AVD (Android Virtual Device) ###
  1. Name the device whatever you want.
  1. Choose Android 2.1-update1 - API Level 7
  1. Leave the SD Card section blank
  1. For the "Built-in" choose Default (HVGA) and click Create AVD.


## UML Diagram Plug-in ##
**ObjectAid UML Explorer for Eclipse**

  1. In eclipse, go to Help->Install New Software
  1. Click "Add" to add a site to get updates from and use the address http://www.objectaid.com/update in the location field
  1. Select the plug-in and go through the following dialogs to complete the installation

## TeamCity Plug-in ##
  1. In eclipse, go to Help->Install New Software
  1. Click "Add" to add a site to get updates from and use the address http://pp00505600136c.usask.ca/update/eclipse in the location field
  1. Select the "JetBrains TeamCity Plug-in", click next, and go through the following diagrams to finish installation
  1. After restarting Eclipse, go to TeamCity->Login, use the address http://pp00505600136c.usask.ca/ and enter your TeamCity username/password. Don't use the "tc.zenja.ca" address here.

# Eclipse Setup #
  1. Make an account at [tc.zenja.ca]
  1. In the main page of your account, click on My Settings & Tools in the upper right
  1. Click on Edit in the Version Control Username Settings
  1. Change the username to your Google account username

## Subversive ##
Subversive needs to be set up before you can work on the project through TeamCity.
  1. Enable the Subversive update site. Go to Window->Preferences, go to the Updates section, and enable the site corresponding to the address below, if it isn't already enabled. Close the dialog box
  1. Go to Help->Install New Software
  1. From the "Work with" drop-down, choose the Subversive update site corresponding with the address http://download.eclipse.org/technology/subversive/0.7/update-site/
  1. Select all the packages except the sources one
  1. Follow through the next dialogs to complete the installation
  1. Restart Eclipse. When it starts again, you will be prompted for the connector you'd like to use. Choose SVN Kit 1.3.5.

## Project check-out ##
The project must be imported by adding it within Eclipse using Subversive. Do not use any previously manually checked out folders.
  1. Go to File->New->Other
  1. Choose SVN->Project from SVN
  1. Use the URL https://cmpt371t1.googlecode.com/svn/trunk. In the Authentication section, enter your Google Code username and password. To get your password, go to Google Code, go to your profile page by following the link on the upper right corner of the page, and going to the settings tab. **Note:** this Google Code password is not the same as your normal Google account password.
  1. Click next and finish. You will then be prompted on how to check out the project. Choose "Check out as a project with the name specified" and keep the name as Luminance, then click finish.

### Project Check-out for LuminanceTestSuite ###
  1. Same as in Project check-out
  1. Use the URL of https://cmpt371t1.googlecode.com/svn/trunk/TestSuite instead
  1. Give name and code:password
  1. It should then be auto-filled same as a project and be named LuminanceTestSuite for the last step
  1. Right click-Runas-Android JUnit
  1. NOTE: Please leave the folder as is when checking out the Luminance Project itself as that won't be recognized as a test project. You need to check it out seperately

### Project Check-out for LuminanceSmokeTest ###
**Command-line
  1. Use this line [co https://cmpt371t1.googlecode.com/svn/branches/LuminanceSmokeTest LuminanceSmokeTest --username your.Gmail.name](svn.md)
  1. To run, please either check the README under antscript folder inside  or change the filepaths of the build.properties file and have ant on your machine and invoke [ant](ant.md) on the command-line.
  1. Make sure the filepaths are right! Use absolute paths.
  1. ie. on Mac, invoke pwd and thats the path you use.**


## Configure local build settings ##
The project is now set up to use Ant to build itself, just like the TeamCity server is doing. Mostly everything should come set up straight out of the repository, except you need to create a "local.properties" file in the project's root folder (where build.properties, etc are) to point to the location of the Android SDK folder on your machine. The file should contain a single line, such as this:
```
sdk.dir=/home/zenja/android-sdk
```
If your path has any spaces in it, follow the instructions below.

### Converting path to DOS short filenames ###
If the path to your SDK directory contains spaces (for example, if it's in Program Files), and you're on Windows, you need to convert it to DOS short filenames. To find out the path on your machine, follow this process:
  1. Open a command prompt
  1. Change to the drive that your SDK is installed to (if other than C:)
  1. Type `dir /x` which will show you the short name for the files/folders in the current folder. Make note of the short name for the one that leads to the SDK
  1. Enter the directory that takes you one step closer to the SDK, and repeat step 3 until you have the full path to the SDK folder
  1. Put the full short path in the local.properties file. It should look something like
```
sdk.dir=C:/PROGRA~1/Android/ANDROI~1
```
The example above should be the correct path for the default install location for the SDK on Win32. On 64bit, PROGRA~1 will be PROGRA~2.

If your path contains spaces and you're not on Windows, contact me (Zenja).

## Committing ##
When you're ready to commit changes, use the TeamCity->Remote Run menu option. This will perform a test run. In this dialog, you can also choose to commit the code if the test is passed. Enable this when you intend to commit.

Make sure to de-select any files generated by the build process. This includes .class files, for example. Also, **make sure** to not commit your modified local.properties file. A more reliable solution to this need to be found.

## Running application on the phone ##
### Through Eclipse ###
While the phone is plugged in, executing a Run should start the application on the phone.

### Through command line ###
Simply run "ant install" on the command line inside the project directory. This will build the project and upload it to your device.

## LogCat Window ##
To view stdout and stderr from the Android emulator you need to enable LogCat.
  1. Go go Window -> Show View -> "Other..."
  1. In the "Show View" window that opens expand the "Android" folder and select "LogCat"


---

<font size='-1'>Authors: Zenja and Kumaran</font>