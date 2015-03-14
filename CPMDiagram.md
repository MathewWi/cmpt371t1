# CPM Diagram #

## For Current Milestone ##

The CPM Diagram for this milestone can be viewed at the following link:

`http://i.imgur.com/UtEGY.png`

It's not included as an image because it's too big and gets cut off by the wiki.  Please follow the link to view the CPM Diagram.

This CPM Diagram has changed significantly from the last one.  This is because as a team, we realized we would not be able to complete any of the new features we had wished to.  We made the decision to polish of the original game mechanics, and focus on making a robust and enjoyable game.

Up until the big join, it mostly remains the same.  The Sound and Asset Manager were moved to be parallel with the tasks before and after the big join.  This was switched because in reality, that was how the tasks ended up getting completed, and some art assets are still underway.  It also logically makes more sense this way.  The only thing that relies on these things is the final release.  It therefore can be done in parallel with New Levels, User Manual, etc.

Another thing that has changed is the removal of a few of the nodes.  These nodes included new game elements and random level generator.  As mentioned before, this was because we wanted to focus on making the game we currently have more robust and polished.

New Levels, User Manual, Special Effects, Level Editor, and User Evaluations all rely on all the things before the big join.  This is because these are the elements that contribute to the basic elements of the game.  Before we add polish, these must be complete.  This is also the case for the User Manual and User Evaluations.  Without at least a basic idea of how the levels and game are going to work overall, these things cannot really be done.

Final Testing relies on New Levels so that we actually have some levels to do some testing on.  However, this should not be considered a strong reliance, as only a few levels will likely be created in order to do this, and the full library of levels will be completed concurrently.

It should also be noted that all the things that are pointing to the big join have also been loosely worked on since upon their completion.  This is because new bugs and issues have risen since their completion has been considered.  This diagram is, however, accurate in a high-level sense of our project.

### Where we are: ###

Seeing as this is the last milestone, we have been working mostly on flushing out the bugs and doing final testing.  In addition to this, that isn't noted on this diagram, is final polish of the aspects of the game.

So, we have achieved our goal of a final release.


### Where we were: ###

We are in the column of tasks after the big join.  We ran into a lot of trouble with bugs after the last milestone.  For the most part, we knew of the bugs, but did not realize they would take so long to figure out.  This milestone therefore became about polishing up and de-bugging the game entirely.  We still have a lot of new levels to add, but there are still some currently created.  The user manual has yet to be started, but it will be for the next milestone.  Special effects have been started on a basic level, and will be polished more in the next milestone.  The level editor is in place conceptually, but still needs implementation.  User evaluations are ready to go, but we simply did not have time to actually conduct.  Art and sound assets are mostly complete as well.  This leaves mostly just final testing and more polish for the last milestone.

## For Previous Milestones ##

The CPM Diagram that has been modified for this milestone can be viewed here: http://code.google.com/p/cmpt371t1/wiki/MilestonePlan#CPM_Diagram

### CPM Diagram Description ###

This CPM has only changed slightly from the previous one, and that was moving the random level generator.  This was moved because in hindsight it was kind of silly to think that we could pull of such a task only having the basics of a playable game just completed.  The Porting task was also removed, as all the tasks before - such as the input, graphics, etc - it representing the porting from XNA and #C to OpenGL and Java.  It was essentially saying the same thing twice, which isn't necessary.

When we started off, the Project Setup was the first task we had to do.  This was just organizing the team, assigning roles, etc.

The next bunch of tasks contains Development Environment Setup, Build Script, and Test Plan.  The first was crucial, developers and testers would not be able to do their jobs until they had their environment set up, at least not easily.  Then it was setting up the build script, as well as the TeamCity server.  This was also important because this was where the repository resides, and without a build script and server it would not be very effective in terms of continuous integration.  At this time we also worked out a basic test plan, just to get the ball rolling for the test team.

Next was the Android Proof of Concepts.  This was all the members of the development team researching their individual development tasks, in order to realize how they would actually go about implementing them.  This is after the previous group because without the development environment, build script and server, their research and preliminary workings would not be able to be shared and integrated together.  The test plan could also take place at this time.

Then, as it can be seen, there are two major join points in this diagram.  One that joins the Audio, Input, Graphics etc. tasks which represents the alpha prototype, then the one after the Random Level Generator, Special Effects etc. tasks, which represents the beta prototype.  The first group represents the major porting that had to be done in order to have the program run on Android.  The second bunch represents all of the additional things that we would like to do to the port.  Except for New Levels, these are all secondary features.  However, all of them depend on the full porting to be complete.

In addition, it can be seen that New Levels depends on Basic level to be complete first.  This makes intuitive sense, as before we can create levels for the user to play, we have to have a framework under which we can create them.  Also notice that New Levels depends on all the other previous tasks.  This is because its all these components that make up a level.  Without input, graphics, asset management etc. put into place, it would not be very useful to try and implement levels within the game.

Also, notice that New Game Elements depends on Basic Game elements.  This makes intuitive sense as well, for the same reasons previously stated for levels.  However, these new game elements do not depend on anything else.  This is because once basic elements are completed addition elements can be outlined.  Since basic elements will have everything that is needed for an element to exist within the game, new game elements does not need to direction depend on the rest of the tasks before it.

Then, once this group of tasks is complete, it is time to ensure the game is robust.  That is why final testing is a task that is to be completed after all implementation is complete.  User manual is also in this group because until we're completely done, it will be a hard thing to write.  However, I am considering moving this so we can get a start on writing it sooner, so we can add to it as we continue developing.

Then, once the game is completely tested, its time to release it!

### Where we are ###
In our development, we would be in the first big join, representing the alpha version of our game.  We have all the fundamentals implemented, so now it's time to add some polish.