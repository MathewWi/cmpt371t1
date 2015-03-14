# Focus #

Since the past milestone, we have worked on improving the things that causes us to falter in the past.  We had discovered the core of these shortcomings was a lack of communication.

> ## Enhanced communication ##
    * Discuss as a group what changes are going to be made.
    * More open discussion in group meetings, rather than one person leading the whole thing
    * Overall whole group more readily available to communication while developing (i.e. working together, being on instant messaging or cell phones etc.)
    * This improved communication allowed us to move forward in the program at a way more efficient rate.

> ## Enhanced collaboration ##
    * Collaborate as a whole group to solve integration and system issues.
    * Separate team meetings for specific discussions to solve more specific issues.
    * Communicate with outside parties (i.e. TA's, professors, etc.) for additional help where required.

> ## Improved division of labour ##
    * No longer relying on one person to do it all.
    * Have a development lead that is separate from project manager.

> ## Improved chain of command ##
    * More defined developer lead position and project manager positions.
      * Project manager runs overall project development
      * Development lead divides development tasks between the development team.
      * Test Team lead divides up testing tasks to their team.
      * Odd jobs divided up during meetings, so one person doesn't have to do them all.

> ## Game oriented testing ##
    * Graphics testing has proven to be a task too impractical to do robustly.
    * Focus is on black box / play testing.

> ## Version Control Strategy ##
    * Remained the same throughout this milestone from the last.
      * TeamCity for continuous integration and as a build server, along with a google code repository set up on a VM on campus.
    * Developers are to commit their classes with stubbed out methods and javadoc comments.
    * T est team is then able to start writing unit tests on this stubbed out code, therefore making out whole software development process more efficient.
    * In addition, there is also a build upon commit to ensure that it does not break existing functionality.  If the build fails upon check-in, it does no allow that code into the repository.

> ## Daily Build ##
    * A daily build is run on TeamCity to ensure the continuing quality of the software.
    * Sends and email to every group member when there is an unsuccessful build.  It also prevents committing of code that breaks the build.
    * This is riding on the notion of fixing bugs on existing functionality before continuing development.
    * In addition, a simple smoke test is to be run to ensure existing quality is not compromised by new functionality.

> ## Android/Java Caveats ##
    * These technologies posed unique problems:
      * No abusing memory allocations - Memory limit: 24MB
      * Avoid ‘new’ anywhere in update() or draw() code paths - takes up too much memory.
      * This causes the garbage collector to suck up CPU time.
      * We will be turning off the garbage collector.  Instead, we will use manually as we see fit.
      * In java, everything is a thread.
        * Main thread, event thread, android thread
        * OpenGL context only valid and accessible in android thread, causing the game to lag.  This is because when the user touches the screen, the event thread has to handle millions of events, causing context switch to android thread to take a long time.
        * This has lead the developers to carefully consider how they structure the code.

> ## Game Oriented Testing ##
    * For this project, we will assume OpenGL is correct.
      * Too much time and effort required to test this!
    * Focus on black-box testing.
    * Stubs are also tested before any code is written.

> ## Use of Logger for debugging ##
    * We are heavily using slf4j.
    * This is a lightweight generic logging library that uses other frameworks underneath in a light weight fashion.
    * Developers add plenty to the logger as they develop.
    * Along with the LogCat Eclipse plug-in, the team is able to read the log to see what is going on in the application.  This has been invaluable for debugging purposes.
    * It should be noted that we have been using this all along, just failed to mention it before :)