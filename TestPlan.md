


# Past/Existing Tests #
> There are currently no existing tests made for the game Luminance. We will be making the tests from the ground up along with the development team as they progress.  Since the code is being completely ported, we will have to write all these tests from scratch.

# Testing Concept #
## Test Suite ##
> The whole testing suite will be a project in and of itself with packages inside differentiating each test component.  For each package for source code written, there will be a corresponding test suite package.  For each class within a package, there will be a test class for that class, and for each method written there will be a test written for it.

> This will make it easy for the daily build to just run inside the eclipse IDE and go through its full steps as it automatically associates and runs. Whats more, the Android SDK uses an ant script for its builds and test builds so this can be implemented into the Team City server where the smoke test will be invoked before a commit will be accepted.

> Furthermore, the ant build will have a file that holds output from a successful run through the test suite.  The diff command will be used to test the output of running the smoke test with this file to ensure that the tests have passed. The output of a test, when invoked via command-line, will output to STDOUT. Should any errors be made, the script will then tell the user that the build has failed and reject the commit. A summary of the tests that have failed will be given to the end-committer.

> The main (and recommended) technologies to be used are Robotium and JUnit for Android. Robotium is essentially built for Android GUI testing and JUnit is recommended by Android's site.

## User-Evaluation on Public ##
> By the end of the second Milestone, we had hoped to have a working version of the game replicated on the Android phone should be up and running. However, this did not actually occur until the fourth Milestone.  With this, we will incorporate a user evaluation with which non-group members from the public will play our game and give a feedback via form. This form (in its alpha) is at the end of this document.

> The public will get roughly a half hour to full hour of gameplay within a break-out room on the Spinks 3rd floor. They will then fill out the sheet handed to them and the team will then process the results based on comments given by the public. Based on those results, bug reports will be posted upon the issues and any other comments such as functionality, coolness, etc will be resolved respectfully.

> We plan to do these evaluations shortly after the current deliverable (#4) is due.

## User-Evaluation ##
> Will be the same as the User-Public but will not need to fill out a form and have full access anytime to an Android phone. Discussion of the game will either be brought up through meetings, documentation on the wiki or a form as the same with the User-Public

# Daily Build #
> The build master will run the test suite on TeamCity and verify its success. Should a few errors occur, a bug report will be issued and taken up by the triage. Should the majority of the project fail then either an emergency meeting is in order or a very high alert report followed by an emergency e-mail to see the problem through thoroughly.

# Milestone 1: Documentation & Concept smoke test #
> The discussion and writing of this documentation and planning of smoke test.
## Tasks ##
  * Write-up of Documentation
  * Divide up Test Components
  * Conceptualize test smoke test structure
  * Set-up testing environments(tools)

**Estimated: 2-3 hours**

# Milestone 2: Smoke test, all core tests done, paired up with developers #
> Division of labour, Working smoke test, Finished suite of core tests

## Tasks ##
  * Know how to make an Ant Script
  * Write Milestone 2 black box tests simultaneously with development
  * Write Milestone 2 white box tests after development of components
  * Write Milestone 2 mutation tests for each component developed
  * Write Milestone 2 GUI testing (Robotium)simultaneously with dev.
  * Integrate black box, white box, mutation test into smoke test

**Estimated: 40 hours**
## Post Milestone Comments ##
> Much difficulty in setting up the emulator and build script to work on TeamCity.
> As a consequence Android Junit tests were not integrated into Smoke tests.
> JUnit tests has been postponed to next milestone.
> See TestTeam wiki for details

# Milestone 3: Test new features, User test #

## Pre-Milestone Comments ##
> After speaking with Prof. Stanley we re-evaluated our testing plans
> to concentrate more on core testing, e.g. black box, automated gui testing
> white box testing, user evaluations.
> We have decided to delay Jcoverage testing with Emma until our
> core tests are fully integrated to work with the emulator

## Tasks ##
  * Translate User scenarios into GUI testing scripts
  * Write all black box tests simultaneously with development
  * Write all white box tests after development of components
  * Integrate Mock objects to test objects in unit tests
  * Integrate GUI tests into daily builds
  * Integrate black box, white box, mutation test into smoke test
  * Carry out user evaluations and revise project
  * Plan out position grid testing by speaking with Level Builder,
> > GUI developer

**Estimated: 35 hours**

## Post-Milestone Comments ##

> User Evaluations were not carried out as planned.  The game was in a playable state, but was much to buggy to give to users at this point.  These evaluations have been pushed back to next milestone.

> We also ran into issues with Mocking.  This will also be attempted in the next milestone.

> However, a comprehensive coverage test was completed in this milestone using Emma.  In addition, a stress test proved that our application can take absurd amounts of input and not crash until it has reached a point that is impossible for a human user to actually input on the device.

<br>
<h1>Milestone 4: Test new source code, Possible hardware test</h1>

<blockquote><h2>Tasks</h2>
<ul><li>Complete GUI Testing<br>
</li><li>Continue to write black box tests simultaneously with development<br>
</li><li>Continue to write white box tests after development of components<br>
</li><li>Integrate Mock objects to test objects in unit tests<br>
</li><li>Carry out user evaluations and revise project<br>
</li><li>Complete mocking<br>
</li><li>Traceview profiling</li></ul></blockquote>

<b>Estimated: 35 hours</b>

<h2>Post-Milestone comments</h2>
User evaluations have yet again been pushed back to next milestone.<br>
<br>
Mocking has continued to be an issue.  We have discovered that we have been attempting to use technologies that simply not possible for our project.<br>
<br>
One thing we did for this milestone was that we got the developers to email or inform in some fashion the test team members.  They emailed the test team members who were ensuring the quality of their source code tests.  They gave detailed descriptions of how their code should work. so that we may better test it.<br>
<br>
<br>
<h1>Milestone 5: Thorough Run-through of all tests, Flush out bugs, User Evaluations</h1>
<blockquote>Make sure everything works</blockquote>

<blockquote>User evaluations FOR REAL this time!</blockquote>

<b>Estimated: 15 hours</b>

<br>
<b>Note: Estimates are total spent by whole group</b>

<h1>Division of Labour:</h1>
<blockquote>It has been discussed that the team will divide up the major testing (unit and general testing) portion into sub categories. This is in coalition with the development group’s choice of dividing their work. This will keep each member’s knowledge of the project narrow but thoroughly understandable. Some will have more than one job depending on their ease of testing. Below is a table of division:</blockquote>

<table><thead><th>Name</th><th>Testing task</th></thead><tbody>
<tr><td>Chet</td><td>  </td></tr>
<tr><td>  </td><td> ResourceManager package unit tests</td></tr>
<tr><td>  </td><td> Math package unit tests</td></tr>
<tr><td>  </td><td> GameLogic package unit tests </td></tr>
<tr><td>Amara</td><td>  </td></tr>
<tr><td>  </td><td> State package unit tests</td></tr>
<tr><td>  </td><td> Input testing</td></tr>
<tr><td>  </td><td> Engine unit tests</td></tr>
<tr><td>  </td><td> GUI package unit tests</td></tr>
<tr><td>  </td><td> Automated GUI Testing with Robotium</td></tr>
<tr><td>  </td><td> User evaluations</td></tr>
<tr><td>Jeff</td><td>  </td></tr>
<tr><td>  </td><td> Smoke Test </td></tr>
<tr><td>  </td><td> Level pacakge unit tests</td></tr>
<tr><td>  </td><td> User evaluations </td></tr>
<tr><td> Martina </td><td>  </td></tr>
<tr><td>  </td><td> GameObject package unit tests</td></tr>
<tr><td>  </td><td> Time package unit tests</td></tr></tbody></table>

Note: due to a time crunch, Martina helped out with testing.<br>
<br>
<h1>Technologies Used</h1>
<ul><li>Android JUnit<br>
<blockquote>Run Regression, Unit tests<br>
</blockquote></li><li>Robotium<br>
<blockquote>Run Gui based tests<br>
</blockquote></li><li>Google Code<br>
<blockquote>Code Base<br>
</blockquote></li><li>Ant Script<br>
<blockquote>Build script, Smoke test, Hooks<br>
</blockquote></li><li>Team City<br>
<blockquote>Smoke test, Hooks<br>
</blockquote></li><li>Emma<br>
<blockquote>Code Coverage</blockquote></li></ul>

Links:<br>
<ul><li>Robotium  <a href='http://code.google.com/p/robotium/'>http://code.google.com/p/robotium/</a>
</li><li>Android  <a href='http://developer.android.com/guide/developing/testing/testing_eclipse.html'>http://developer.android.com/guide/developing/testing/testing_eclipse.html</a>
</li><li>Emma <a href='http://emma.sourceforge.net/'>http://emma.sourceforge.net/</a></li></ul>

<h1>Test Summary</h1>
<h2>Regression</h2>
<blockquote>Performed only when a bug is reported and a fix has been issued. It is a check to also see if a fix has broken any other part of the project.</blockquote>

<h2>Smoke</h2>
<blockquote>This has been determined to only cover basic method testing as discussion of a first level play through might take too long. There would be no point in waiting for a commit that takes 10 minutes long. This will take a bit longer to understand as Team City, Ant Script and Shell scripting are needed to run in collaboration with the Android Virtual Device. It will be built on the Team City server, check basic methods and menus and, depending on time taken, might play through first level.</blockquote>

<h2>Black Box</h2>
<blockquote>Using the class diagrams and function/method stubs, the test team will be making black box test cases for their respective parts. This will be made in parallel with the development team as asking them to wait will not bode well because time is an issue. Thus, the test will be ran when the section of code is committed.</blockquote>

<h2>White Box</h2>
<blockquote>This will be implemented after the Black Box Testing has been passed. It will include basics such as mutation testing and if possible, path testing. This will run through from either a method or a whole run-through of a level(s).</blockquote>

<h2>Unit Test:</h2>
<blockquote>Unit test will be used in Eclipse IDE’s built-in to run the majority of the test suites. Also use test coverage tool to find areas of a program not exercised by a set of test cases, creating additional test cases to increase coverage and determining a quantitative measure of code coverage, which is an indirect measure of quality.</blockquote>

<h2>JCoverage Test:</h2>
<blockquote>Very comprehensive and fully implemented.</blockquote>

<h2>GUI Test:</h2>
<blockquote>Android specific Robotium GUI tester will be used in accordance with a user use scenario. GUI test would perform a series of functions needed to pass the first level of the game. GUI test will be written and automated and run by testers.</blockquote>

<h2>Activity Test</h2>
<blockquote>Testing the conditions of the game.  Currently not implemented.<br>
<h3>Initial Condition</h3>
Test whether or not the start-up of the game will be at some "playable" state, it also provides a confidence measure for subsequent tests.<br>
<h3>User Interface</h3>
Check whether or not the buttons/actions do what they do.<br>
<h3>State Management</h3>
Check whether if you've actually won a game when you have won a game.</blockquote>

<h1>Testing results format:</h1>
<blockquote>What will eventually be printed out as a result of running tests.<br>
TestPackage | “Short description” | MethodName | Passed/Failed</blockquote>

<h1>Defect/Bug Reports:</h1>
<blockquote>These will follow in with the formatting given to us by Google Code. They will also be issued through Google Code.</blockquote>

<blockquote>Defect Resolution Process:<br>
<ol><li>Tester or user discovers bug<br>
</li><li>Tester reports bug to issue tracker<br>
</li><li>Triage selects which bugs to handle<br>
</li><li>Bug is assigned to developer<br>
</li><li>Developer fixes bug marks the bug fixed<br>
</li><li>Triage assigns tester to retest<br>
</li><li>Tester tests and marks bug as verified<br>
</li><li>Triage archives bug</li></ol></blockquote>


<h1>Usability Test Reports:</h1>
<blockquote>A sheet will be given to testers when testing on a real device. This will include both members and non-members of team1. It will contain basics (see below. Bugs will be converted into official bug reports by testers and assigned to developer by Triage:</blockquote>

<hr />
(see UserEvaluations wiki for updated Usability test reports)