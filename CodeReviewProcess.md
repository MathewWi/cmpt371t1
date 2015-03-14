
# Review of existing system (V1) #
The system hasn't been peer reviewed thoroughly because the implementation won't be reused since the new code base will be rewritten. However, the design and architecture of the system has been evaluated by the whole group during one of the early meetings.

Stephen presented a class diagram and information about the previous system. This was scrutinized by the whole group and suggestions were made on how to improve the architecture and relationships between classes. We also discussed any thoughts on how to change the system for the rewrite. For instance, we discussed possibly doing sprite-based 2D graphics, or using real 3D models for game objects rather than hard-coded primitives. Any previous architectural concerns noticed by Stephen, such as the relationship between game objects and game state were reviewed.

# Review schedule (V2) #
Code reviews will be generally done when mini-milestones are completed. For example, if someone is assigned to develop an audio player system for the game as a mini-milestone, once their work is completed for the current phase, it will be subject to code review.

# Review assignment (V2) #
Once a member's code is subject to review, reviews will be assigned at the end of weekly meetings. Assignments will be done once normal meeting topics have been completed. Members will announce that their coding task is completed and they would like a review. Another person in the meeting will agree to take on the review.

We will strive to have everyone involved in the review process. All code should be reviewed, and all members will be doing reviews in a generally round-robin pattern. Each member will perform a review on a peer's code before any member does another review themselves. This way everyone is ensured to participate and everyone should be doing the same number of reviews.

Reviews may also be done impromptu, with a member requesting a review before a milestone deadline. This could be useful in case the member is unsure if they're going about the task in a good way.

We have conducted both peer and personal code reviews.  For the personal code reviews, an individual goes through the code they have been assigned and takes down notes about the things they find.  For peer reviews, more than one of us sits down together and looks at portions of the code together.

There has also been two formal inspections so far.  These were conducted on the resource package, and the GameState.java class.  We plan to do one more formal inspection for this project.

# Mechanism #
We originally wanted to use Google Code's built-in code review functionality. We thought this allowed members to easily comment on specific code and for the owner of the code to see feedback and tie it to specific areas. However, we quickly discovered that any comments made in this way would be deleted completely if a new version of that file were committed.

We then started to use the wiki for this functionality.  Upon a member doing a code review, they would simply document it in the CodeReviews wiki page.

# Technical issues #
Since there are many conceptual domains involved in the project, such as sound, graphics, input, cross-platform capability, game mechanics, etc, the people responsible for their own area will have much more technical knowledge about it. By doing the reviews in a round-robin fashion, members will be exposed to areas outside their expertise. For example, someone will have to do reviews that involve OpenGL code when they might not know anything about OpenGL.

This can be considered a good thing, since everyone will gain more knowledge on the various parts of the system, but it can inhibit the effectiveness of code reviews. However, regardless of expertise, many other aspects of code can be reviewed, such as basic coding mistakes, style issues, coupling/coherence problems, etc. Another big advantage of this approach is having a fresh set of eyes that isn't already used to seeing that code or even topic. If it proves to be too difficult to have reviewers keep up with learning enough outside their knowledge area to perform effective code reviews, we will reconsider the review assignment process.


---

<font size='-1'>Author: Zenja</font>