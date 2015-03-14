# Abstract #
> This is a report outlining the major sources of risk from our project. Each risk will be noted along with the following relevant information:
    * Description - information about the risk being discussed
    * Probability - the likelihood that the risk will occur
    * Severity - how severe the risk would be should it occur
    * Rationale - reasons why the issue is considered a risk
    * Mitigation Plan - how we plan to prevent this risk from being realized
    * Contingency Plan - should the risk occur, what alternatives can be explored?
    * Status - has the issue been resolved or are there still potential risk issues?
    * Update(s) - more information will be posted as risks change


# Project Risks #
Below are risks that we faced, or the potential risks that were foreseen:

## Risk 1 ##
  * **Description:** Setting up TeamCity to run a smoke test when code is committed.

  * **Probability:** Low

  * **Severity:** High

  * **Rationale:** Not having TeamCity properly set up is a serious risk because without it there would be nothing to prevent team members from committing code that may compromise the main build. It is also needed to run our smoke test which will attempt to prevent this from occurring. Ultimately, we will be unable to work efficiently on this project without TeamCity and our smoke test.

  * **Mitigation Plan:** We are confident that this issue can be resolved if the Build Master and the Test Team combine their knowledge to get both systems to work together.

  * **Contingency Plan:** Should the issue not be resolved in a timely manner, we plan to designate more team members to work on the issue until it is resolved. Our research has indicated that TeamCity is the optimal choice for continuous integration, therefore other options will not be explored.

  * **Status:** Currently, the project builds successfully on TeamCity and a simple smoke test is in place. Therefore, this risk is sufficiently avoided.

  * **Update** 03/20/2011: The smoke test currently runs our suite of JUnit tests to ensure that nothing major is broken by a commit.

  * **Final Update** 04/04/2011: The smoke test infrastructure was set up before committing broken code caused productivity issues.

## Risk 2 ##
  * **Description:** Setting up a standard development environment for all developers.

  * **Probability:** Medium

  * **Severity:** High

  * **Rationale:** It is crucial to have a standard development environment so that all users have an identical environment to ensure that errors do not result from inconsistency.

  * **Mitigation Plan:** We plan to avoid the realization of this risk by proper use of our project wiki. We will provide detailed instructions on how to set up our chosen IDE as well as integration of all the required plugins.

  * **Contingency Plan:** If there are team members who are having issues setting up the standard development environment, we can delegate other team members who have been successful to help out those that are struggling.

  * **Status:** Several team members have already set up their development environments. In addition, we have published a page on our project wiki for the purpose of assisting those who have not yet completed the task. Therefore, this risk has been successfully managed.

  * **Update** 02/13/2011: At this point, all development and test team members have Eclipse setup and development has begun.

  * **Final Update** 04/04/2011: The standard development environment was set up early on and since then it has been used without issue.

## Risk 3 ##
  * **Description:** Ensuring proper team communication.

  * **Probability:** High

  * **Severity:** High

  * **Rationale:** Without efficient communication, a project is susceptible to risk in many areas, including ones that are not yet known. It is important that all team members have access to current information about design, testing, group meetings, issue tracking, and any other issues that may come up.

  * **Mitigation Plan:** We have dealt with this risk by publishing any important information on the project wiki. In addition, a project mailing list should be set up for efficient communication among all team members.

  * **Contingency Plan:** We believe that the project wiki and group mailing list should be sufficient to facilitate proper communication within the team. If this is not acceptable, team members who are unsure of their current task and cannot find the information on the project wiki should contact the project lead for additional guidance.

  * **Status:** The project wiki is updated frequently with details concerning meeting minutes, individual tasks, and guides for setting up the systems needed to start development and testing. Therefore, this risk has been successfully managed.

  * **Update** 02/13/2011: There have been issues with communication thus far, specifically between the development and test teams. To mitigate this risk we needed to make structural changes to increase the division of responsibilities.

  * **Final Update** 04/04/2011: At this point, team communication has greatly improved with frequent email messages and diligent use of the issue tracker.

## Risk 4 ##
  * **Description:** Ensuring project success after losing group members.

  * **Probability:** High

  * **Severity:** High

  * **Rationale:** Having lost two members from our test team, we needed to move developers into testing. This move will take manpower away from the development side, causing progress to be slower. In addition, there will be a learning curve for developers that have recently become testers. There will be some time needed to understand the code that is to be tested.

  * **Mitigation Plan:** We plan to hold frequent developer / tester meetings to ensure that both teams understand what is being implemented, and what can be tested at any given time. In addition, developers are to create contracts for all methods / classes that are added and commit them to the repository immediately.

  * **Contingency Plan:** If more specific assistance is needed, a test team member will contact the developer involved in the code being tested, and obtain some advice on how to properly test a given unit.

  * **Status:** After a recent meeting on 02/11/2011 both the developers and testers are aware of what is to be implemented, and which units can be tested first.

  * **Update** 03/20/2011: To ensure that developers are in constant communication with testers, we have paired up 1-2 developer(s) with the member of the test team that will be testing their code.

  * **Final Update** 04/04/2011: Despite having less members in our group, we were still able to produce quality work, and are very close to completion.


## Risk 5 ##
  * **Description:** Ensuring touch support for Android will provide a positive user experience.

  * **Probability:** Medium

  * **Severity:** Low

  * **Rationale:** Since the original platform did not have touch support for its original input, it is crucial that the user experience is maintained in moving forward to the new platform.

  * **Mitigation Plan:** We plan to thoroughly research the capabilities of Android touch APIs. In addition, we hope to find sample code to use as a reference to assist in the implementation.

  * **Contingency Plan:** Should our research into Android APIs be unsuccessful, we will attempt to find a 3rd party solution. It is likely that this is a solved problem already. To facilitate proper communication, we will publish our findings to the project wiki for easy access.

  * **Status:** We have designated a member of our design team to research this issue, to ensure that it is solved before we encounter it.

  * **Update** 02/13/2011: At this point, developers have located touch input code to use as a basis from the open source Android game Replica Island http://replicaisland.net/

  * **Final Update** 04/04/2011: The Replica Island code has served us well in implementing the touch input system.


## Risk 6 ##
  * **Description:** Ensuring the success of a complete code rewrite.

  * **Probability:** Medium

  * **Severity:** High

  * **Rationale:** This is the largest and most important task concerning this phase of the project. Re-writing the code for Android will be a time-consuming activity and is crucial for the project completion.

  * **Mitigation Plan:** Our project lead has in-depth experience with the old system, since he was one of the original developers. We are hoping to use this prior knowledge to create a stub framework so that the implementation can be divided into work units.

  * **Contingency Plan:** If there are still issues getting the code re-write completed, developers can seek the assistance of our project lead, and the issue will likely be resolved from there.

  * **Status:** We currently have a stub framework on our code repository and from there we plan to divide the development task into work units for developers to take on.

  * **Update** 02/13/2011: The general framework of our code base has been stubbed. We have delegated the initial work units to be implemented. These units are the initial priorities as reflected in the CPM Diagram.

  * **Final Update** 04/04/2011: After eliminating the new features from the game, the success of the complete rewrite is imminent.


## Risk 7 ##
  * **Description:** Ensuring that project resources are handled in a convenient way given the lower memory capabilities of a handheld device.

  * **Probability:** Low

  * **Severity:** Low

  * **Rationale:** Since our project will be using external resources (audio, textures, etc.) we must be aware of where these items are stored in the compiled application. Without knowing these details, we would not be able to reference the resources correctly. In addition, we need to take into account that a hand held device has lower memory than the original platform.

  * **Mitigation Plan:** We have delegated a developer to begin research on this topic, and attempt to find documentation on the Android developer page. From there, we can post the relevant information to the project wiki.

  * **Contingency Plan:** If an individual developer working on this task is having issues, they will request assistance from another team member to work together until the task is completed.

  * **Status:** This issue is currently being researched.

  * **Update** 02/13/2011: At this point, we have delegated that a resource manager system be implemented to handle the efficient loading of audio and texture elements as needed.

  * **Update** 03/20/2011: The source code relating to resource management has been written and tested. Further refactoring to follow.

  * **Final Update** 04/04/2011: Resource management seems to have been handled correctly, as performance has not been impacted.

## Risk 8 ##
  * **Description:** Avoid performance problems when running on Android phone.

  * **Probability:** Low

  * **Severity:** High

  * **Rationale:** It is possible that a basic game session could run slowly, impacting the overall game experience. When a game suffers performance problems, it severely detracts from the game play regardless of how good it may be.

  * **Mitigation Plan:** If we do encounter performance issues, one possible solution is to decrease graphics quality for the in-game objects. This will not sacrifice the number of in play objects and preserve the current game experience.

  * **Contingency Plan:** If lowering the graphics quality does not resolve the slowness, we will have to reduce the number of in play objects.

  * **Status:** We have not yet encountered the issue, but are prepared to deal with slowness should it come up.

  * **Update** 03/20/2011: We have encountered performance issues, but only within the Android emulator. Running the game on an actual device yields better results.

  * **Final Update** 04/04/2011: We have encountered performance issues using a custom version of the Android OS, but we later determined it was due to a lack of the required OpenGL libraries. Any phone that has the correct libraries does not have the issue.


## Risk 9 ##
  * **Description:** Repeated or defective work resulting from vague requirements.

  * **Probability:** Medium

  * **Severity:** High

  * **Rationale:** Due to the current high-level nature of our requirements document, it is possible that developers (and to a lesser extent, testers) may misunderstand what needs to be done. These misunderstandings can cause significant issues in the form of defects and even reworking of the project design.

  * **Mitigation Plan:** Before any new features are implemented, we must formalize a design of the new components, so there is no confusion.

  * **Contingency Plan:** If there is still confusion about how the new features are to be implemented, then the design lead will step in to clarify any issues that should arise, and ensure that all developers are on the correct path.

  * **Status:** Since we are still working on a runnable version of the game, this risk has not yet been realized. Once the existing features are implemented, we will formalize a plan for how to proceed with the new features.

  * **Update** 03/20/2011: We have decided not to implement any new features and focus on improving the game from its original state. Therefore, this risk is no longer an issue.

  * **Final Update** 04/04/2011: Having abandoned the new features, there is a specific idea of which tasks remain to be done.

## Risk 10 ##
  * **Description:** Work handed in late due to a miscommunication about due dates.

  * **Probability:** Medium

  * **Severity:** High

  * **Rationale:** There were several members that were under the impression that the next Milestone was due on Wednesday, March 8th as opposed to Tuesday. This risk has severe implications since it would involve us missing the deadline by 24 hours.

  * **Mitigation Plan:** The project's manager has sent out communication informing all of us of the correct due date.

  * **Contingency Plan:** If there are still individuals who are unaware of the correct due date, then the project manager will need to intervene on an individual level to ensure that proper communication takes place.

  * **Status:** Everyone is likely aware of the correct due date, and we have already received extra help from our project's manager. Therefore, the risk is sufficiently avoided at this point.

  * **Final Update** 04/04/2011: Everyone is aware of the upcoming due date, and we plan to have everything complete 24 hours prior to the deadline.

## Risk 11 ##
  * **Description:** Limited user evaluation may result in game play issues.

  * **Probability:** Medium

  * **Severity:** High

  * **Rationale:** If our game does not receive enough user evaluation until the later stages of development, it may be too late to fix issues that are related to game play. These issues may not be a result of bugs or logical errors in code; they may simply be features that do not provide the best user experience.

  * **Mitigation Plan:** We have scheduled user evaluations as soon as levels are available to ensure that we get a realistic picture of how the game is when played by individuals outside of the project.

  * **Contingency Plan:** We must also ensure that developers are aware of the importance of user evaluation and play testing.

  * **Status:** Levels are not yet complete, so user evaluation will not be possible for the moment. The developers should be aware of the plan to start user evaluations as soon as possible.

  * **Final Update** 04/04/2011: We have extensive feedback from users of different demographics. This will hopefully provide balanced feedback about the game and what can be improved.

## Risk 12 ##
  * **Description:** Limited test phones available due to OpenGL issues.

  * **Probability:** High

  * **Severity:** High

  * **Rationale:** Due to a custom version of the Android OS, certain OpenGL libraries that are needed were not included. As a result, this causes the game to run at 2-4 fps, which would make a demo impossible.

  * **Mitigation Plan:** Attempt to locate devices that have the required libraries to make the game runnable.

  * **Contingency Plan:** If all else fails, simply install a different custom OS on the devices that allow for acceptable graphics functionality.

  * **Status:** At this point, we have been given additional devices to help alleviate the issue. Ultimately, we do have a phone which we can use to demo the game, so the risk will likely be avoided.

  * **Final Update** 04/04/2011: We have a device that runs the game properly which will be used for the project demo.