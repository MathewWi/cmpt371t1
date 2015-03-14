# Use Cases #

**User Procedure Name**  First Level Walk-through

New player walks through the first level of game

**Scope**  Luminance

**Level**  user-goal

**Primary Actor**  New player

**Stakeholders and Interests**



_New Player:_ Wants to learn how to play the game decides to walk-through the tutorial.

_Tester:_ Wants to run the new player script for playing the first level of the game to check for possible issues.

_Developer:_Wants to run the new player script to see what functionalities of the game can be improved.

**Preconditions** :

> Walk-through map is created e.g. Luminance video 1:03.

> Map rules:

> One light beam

> 3 differing colored circles

> At least one wall obstacle impeding direct access to circle

> Top wall, bottom wall

> A solution

> Only 1 prism

> Background music plays

> On completion success music plays

> Multiple instances of mirrors, based on difficulty

> Infinite eraser

> Able to change orientation, camera focuses on centre


B: brick,  (R): red circle,  (B): blue circle,  (G): green circle,   /P\: prism,  (W):emitter

/M: left-up mirror,  \M: left-down mirror,  M\: right-up mirror,   M/: right-down mirror

Phase 0:

|  | 1 | 2 | 3 | 4 | 5 | 6 |
|:-|:--|:--|:--|:--|:--|:--|
| 1 |  |  | B | (W) | B | B |
| 2 | B |  |  |  |  |  |
| 3 | (B) |  | B |  | B |  |
| 4 |  | B | B |  | B | (G) |
| 5 |  |  |  |  |  |  |
| 6 |  |  | (R) |  |  |  |
| 7 |  |  |  |  |  |  |
| 8 |  |  |  |  |  |  |

Phase 1:

|  | 1 | 2 | 3 | 4 | 5 | 6 |
|:-|:--|:--|:--|:--|:--|:--|
| 1 |  |  | B | (W) | B | B |
| 2 | B |  |  |  |  |  |
| 3 | (B) |  | B |  | B |  |
| 4 |  | B | B |  | B | (G) |
| 5 |  |  |  |  |  |  |
| 6 |  |  | (**R** ) | /P\ |  |  |
| 7 |  |  |  |  |  |  |
| 8 |  |  |  |  |  |  |



Phase 2:

|  | 1 | 2 | 3 | 4 | 5 | 6 |
|:-|:--|:--|:--|:--|:--|:--|
| 1 |  |  | B | (W) | B | B |
| 2 | B |  |  |  |  |  |
| 3 | (B) |  | B |  | B |  |
| 4 |  | B | B |  | B |  |
| 5 |  |  |  |  | (**G** ) |  |
| 6 |  |  | (**R** ) | /P\ |  |  |
| 7 |  |  |  |  |  |  |
| 8 |  |  |  | M\ | /M |  |

Phase 3:

|  | 1 | 2 | 3 | 4 | 5 | 6 |
|:-|:--|:--|:--|:--|:--|:--|
| 1 |  |  | B | (W) | B | B |
| 2 | B |  |  |  |  |  |
| 3 | (**B** ) |  | B |  | B |  |
| 4 |  | B | B |  | B |  |
| 5 |  |  |  |  | (**G** ) |  |
| 6 |  |  | (**R** ) | /P\ |  | \M |
| 7 | M\ |  |  |  |  | /M |
| 8 |  |  |  | M\ | /M |  |

B: brick,  (R): red circle,  (B): blue circle,  (G): green circle,   /P\: prism,  (W):emitter

/M: left-up mirror,  \M: left-down mirror,  M\: right-up mirror,   M/: right-down mirror

**Success Guarantee**

The player splits the beam of white light into primary colors and redirects them into the primary colors and redirects them into colored goals using mirrors. Success music plays and the user is given the option menu and their time of completion.



**Main Success Scenario**

  1. They open the program
  1. The player chooses ->Start Game from main menu
  1. Single taps the tools prism
  1. Double taps at (6, 4) the prism is placed
  1. Single taps the tools mirror
  1. Double taps at (8, 4) the mirror is placed
  1. Single taps until  M\
  1. Single taps the tools mirror
  1. Double taps at (8, 5) mirror is placed
  1. Single taps the mirror
  1. Double tap at (6, 6) mirror placed
  1. Single taps until \M
  1. Single taps the mirror
  1. Double tap at (6, 7) mirror placed
  1. Single taps the tools mirror
  1. Double tap at (7, 1) mirror placed
  1. Single taps until M\
  1. First level complete

**Extensions**

  1. . They open the program
  1. . The player chooses ->Start Game from main menu
    * Level 1 loaded
    * background music plays
    1. ) Double taps on each tool cycles through
  1. . Single taps the tools prism
    * prism shown as chosen
    1. ) Double taps at (5,4)  the prism was placed incorrectly
    1. ) Single taps the tools eraser
    1. ) Single taps the prism at (5, 4)
      * prism at (5, 4) is erased
  1. . Double taps at (6, 4) the prism is placed
    * white light is split into RGB
    * red circle is linked
  1. . Single taps the tools mirror
    * the mirror is shown as chosen
    * the mirror count reduces
    1. ) Double taps at (8, 3) the mirror is placed incorrectly
    1. ) Single taps the tools eraser
    1. ) Single taps the mirror at (8, 3)
      * mirror at (8, 3) is erased
  1. . Double taps at (8, 4) the mirror is placed
  1. . Single taps until  M\
    * mirror is flipped
  1. . Single taps the tools mirror
    * the mirror is shown as chosen
    * the mirror count reduces
  1. . Double taps at (8, 5) mirror is placed
    * green circle is linked
  1. . Single taps the tools mirror
    * the mirror is shown as chosen
    * the mirror count reduces
  1. . Double tap at (6, 6) mirror placed
  1. . Single taps until \M
    * the mirror is flipped
  1. . Single taps the mirror
    * the mirror is shown as chosen
    * the mirror count reduces
  1. . Double tap at (6, 7) mirror placed
  1. . Single taps the tools mirror
    * the mirror is shown as chosen
    * the mirror count reduces
  1. . Double tap at (7, 1) mirror placed
  1. . Single taps until M\
    * the mirror is flipped
    * blue ring is linked
    * menu opens
  1. . First level complete
    * victory music plays

**Special Requirements**

> -No special requirements

**Technology and Data Variations**  **List**

> -No technology or data variations

**Frequency of Occurrence**

> -frequent all players will play through the first level.

**Miscellaneous**

Actors:

  * Irratic player always erases after each placement, connects light to wrong orb, switches levels, restarts level
  * Commuter/leasure player always pauses game, turns off sound, rotates android frequiently
  * Average player plays each level perfectly in the fastest amount of time.




### Use Case (1) Light to Orb Connection(collision) ###

Actors: Erratic player

Goal: Connect light to Orb

Activities: The player

  1. User chooses prism
  1. User places Prism
    * System refracts light into RGB
    * System deactivates prism from toolbar
    * Light collides with Wall obstacle
> > > //prism -> mirror -> brick
    * Light collides with outer boundary
> > > //prism -> outer boundary
    * Light collides with differing color orb
> > > //prism -> wrong orb
  1. User chooses mirror
  1. User places mirror
    * Light collides with mirror
  1. User Rotates mirror
    * Light reflects to a different angle
  1. User chooses mirror
  1. User places mirror
    * Light collides with mirror
  1. User Rotates mirror
    * Light reflects to a different angle
  1. User chooses mirror
  1. User places mirror
    * Light collides with mirror
    * Light collides with wrong orb
> > > //prism -> mirror -> wrong orb
  1. User Rotates mirror
    * Light reflects to a different angle
    * Light collides with prism
    * Light intersects with different light
> > > //prism -> mirror -> mirror-> prism
> > > //lights intersect


### Use Case (2) Backtracks steps ###

Actors: Erratic player

Goal: User wants to backtrack on steps previously taken

Activities:

  1. User starts game
  1. User chooses random level
    * Game view is shown
  1. prism -> mirror -> orb
    1. User chooses prism
    1. User places Prism
      * System refracts light into RGB
      * System deactivates prism from toolbar
      * Light collides with Wall obstacle
> > > > //prism -> mirror -> brick
      * Light collides with outer boundary
> > > > //prism -> outer boundary
      * Light collides with differing color orb
> > > > //prism -> wrong orb
    1. User chooses mirror
    1. User places mirror
      * Light collides with mirror
    1. User chooses eraser

  1. User erases prism
    * Prism disappears
    * RGB disappears
    * System reactivates prism in toolbar
    * White light collides with mirror
  1. User chooses prism
  1. User places prism
    * System refracts light into RGB
    * System deactivates prism from toolbar
    * Lights reflects off of mirror
  1. User selects deployed mirror
  1. User moves said mirror to different coordinate
    * Previously in contact light no longer impeded
    * Light at the new coordinate reflected
  1. User Rotates mirror
    * Light reflects to a different angle
  1. User chooses mirror
  1. User tries to place a mirror on top of another mirror
  1. User tries to place a mirror on top of a Wall
  1. User chooses eraser
  1. User erases mirror
    * Light continues on with no obstacle in the way
  1. User presses pause
    * System brings up pop up menu
  1. User presses restart
    * System brings up the same map reset

### Use Case (3) Switch states ###

Actors: Marathon player

Goal: User wants to switch out of the game to use another app.

Activities:
  1. User starts game
  1. User chooses random level
    * Game view is shown
  1. prism -> mirror -> orb
    1. User chooses prism
    1. User places Prism
      * System refracts light into RGB
      * System deactivates prism from toolbar
      * Light collides with Wall obstacle
> > > > //prism -> mirror -> brick
      * Light collides with outer boundary
> > > > //prism -> outer boundary
      * Light collides with differing color orb
> > > > //prism -> wrong orb
    1. User chooses mirror
    1. User places mirror
      * Light collides with mirror
    1. User receives a call
      * Game sound is automatically turned off
    1. User rotates phone
      * Phones changes orientation
      * Game is automatically paused
    1. User exits the Luminance
    1. User reenters Luminance
      * System shows pop up menu screen
    1. User chooses sound on
    1. User chooses resume
      * System reloads all previously made moves

  1. User chooses pause
    * System loads pop up menu
  1. User chooses main menu
    * System loads main menu



## Test Cases ##

  1. Light to obstacle collision
  1. Object interaction/function
  1. Object to obstacle collision
  1. User statistics registration
  1. User transition between states, saved menu, resume
  1. System under stress


### Test Case (1): Light to obstacle collision ###

Purpose: To test and verify connections to objects are being handled appropriately.

Prerequisite:	 A game level has been started

Test Data:


> (post map grid here)

Steps:

  1. prism -> mirror -> orb
  1. User chooses prism
  1. User places Prism
    * System refracts light into RGB
    * System deactivates prism from toolbar
    * Light collides with Wall obstacle
      * prism -> mirror -> brick
    * Light collides with outer boundary
      * prism -> outer boundary
    * Light collides with differing color orb
      * prism -> wrong orb
  1. User chooses mirror
  1. User places mirror
    * Light collides with mirror
  1. User Rotates mirror
    * Light reflects to a different angle
  1. User chooses mirror
  1. User places mirror
    * Light collides with mirror
  1. User Rotates mirror
    * Light reflects to a different angle
  1. User chooses mirror
  1. User places mirror
    * Light collides with mirror
    * Light collides with wrong orb
      * prism -> mirror -> wrong orb
  1. User Rotates mirror
    * Light reflects to a different angle
    * Light collides with prism
    * Light intersects with different light
      * prism -> mirror -> mirror-> prism
      * lights intersect

Notes and Questions:

GUI Tests:

Unit Tests


### Test Case (2): Object interaction/function ###

Purpose: To test and verify that objects unique, functional, and interact with each other.

Prerequisite:	 A game level has been started

Test Data:

> (post map grid here)

Steps:

  1. User starts game
  1. User chooses random level
    * Game view is shown
  1. prism -> mirror -> orb
    1. User chooses prism
    1. User places Prism
      * System refracts light into RGB
      * System deactivates prism from toolbar
      * Light collides with Wall obstacle
        * prism -> mirror -> brick
      * Light collides with outer boundary
        * prism -> outer boundary
      * Light collides with differing color orb
        * prism -> wrong orb
    1. User chooses mirror
    1. User places mirror
      * Light collides with mirror
    1. User chooses eraser

  1. User erases prism
    * Prism disappears
    * RGB disappears
    * System reactivates prism in toolbar
    * White light collides with mirror
  1. User chooses prism
  1. User places prism
    * System refracts light into RGB
    * System deactivates prism from toolbar
    * Lights reflects off of mirror
  1. User selects deployed mirror
  1. User moves said mirror to different coordinate
    * Previously in contact light no longer impeded
    * Light at the new coordinate reflected
  1. User Rotates mirror
    * Light reflects to a different angle
  1. User chooses eraser
  1. User erases mirror
    * Light continues on with no obstacle in the way
  1. User presses pause
    * System brings up pop up menu
  1. User presses restart
    * System brings up the same map reset

Notes and Questions:

GUI Tests:

Unit Tests

### Test Case (3): Object to obstacle collision ###

Purpose: To test and verify that objects unique, functional, and interact with each other.

Prerequisite:	 A game level has been started

Test Data:

> (post map grid here)

Steps:

  1. User starts game
  1. User chooses random level
    * Game view is shown
  1. prism -> mirror -> orb
    1. User chooses prism
    1. User places Prism
      * System refracts light into RGB
      * System deactivates prism from toolbar
      * Light collides with Wall obstacle
        * prism -> mirror -> brick
      * Light collides with outer boundary
        * prism -> outer boundary
      * Light collides with differing color orb
        * prism -> wrong orb
    1. User chooses mirror
    1. User places mirror
      * Light collides with mirror
    1. User chooses eraser
    1. User tries to erase wall
    1. User Rotates mirror
      * Light reflects to a different angle
    1. User chooses mirror
    1. User tries to place a mirror on top of another mirror
    1. User tries to place a mirror on top of a Wall
    1. User tries to place a mirror on top of prism

Notes and Questions:

GUI Tests:

Unit Tests

### Test Case (4): User statistics registration ###

Purpose: To test and verify that toolbar/high score is correctly updated after each object use

Prerequisite:	 A game level has been started

Test Data:

> (post map grid here)

Steps:

  1. User starts game
  1. User chooses random level
    * Game view is shown
  1. User chooses prism
  1. User places prism in empty grid square
    * Prism count is reduced in toolbar
  1. User chooses eraser
  1. User erases prism
    * System increments number of prisms
  1. User beats game
    * System updates high score with time
  1. User chooses main menu
  1. User selects high score bored
    * User sees high score bored is updated

Notes and Questions:

GUI Tests:

Unit Tests

### Test Case (5): 	User transition between states, saved menu, resume ###

Purpose: To test and verify that state changes are taking place and are handled appropriately

Prerequisite:	 A game level has been started

Test Data:

> (post map grid here)

Steps:

  1. User starts game
  1. User chooses random level
    * Game view is shown
  1. prism -> mirror -> orb
    1. User chooses prism
    1. User places Prism
      * System refracts light into RGB
      * System deactivates prism from toolbar
      * Light collides with Wall obstacle
        * prism -> mirror -> brick
      * Light collides with outer boundary
        * prism -> outer boundary
      * Light collides with differing color orb
        * prism -> wrong orb
    1. User chooses mirror
    1. User places mirror
      * Light collides with mirror
    1. User receives a call
      * Game sound is automatically turned off
    1. User rotates phone
      * Phones changes orientation
      * Game is automatically paused
    1. User exits the Luminance
    1. User reenters Luminance
      * System shows pop up menu screen
    1. User chooses sound on
    1. User chooses resume
      * System reloads all previously made moves
    1. User chooses pause
      * System loads pop up menu
    1. User chooses main menu
      * System loads main menu

Notes and Questions:

GUI Tests:

Unit Tests

### Test Case (6): 	Stress test ###

Purpose: To test and verify that is able to cope under stranious conditions

Prereq:	Map is started

Test Data:

> (post map grid here)

Steps:

  1. User starts game
    1. Android's Luminance memory is reduced
  1. User chooses first level play through tutorial
    1. Android slowly reduces memory for Luminance to 0
      * Luminance system realizes memory is too low returns error exits
    1. Android's Luminance memory is reset to normal
    1. Android's hard drive space is set to very low
  1. User starts game
  1. User chooses first level play through tutorial
    * Luminance gives error hard drive space is too low
    * Luminance exits

Notes and Questions:

GUI Tests:

Unit Tests