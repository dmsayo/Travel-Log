
# Travel Journal
Having travelled a lot due to having family in many locations, I have always tried to find ways to record
where I have gone and how memorable of a trip it was. Taking a multitude of photos can easily get disorganized
  and using a physical notebook simply takes up space in my bag. With this application,
   you would be able to record your different locations and write a description that could include:
- Your overall rating of your trip
- What activities did you do there
- What kind of foods you ate
- What year you went
- Would you go again
- And any other written description you would like to take note of

This application is for those like me who want to be able to neatly consolidate documentation of our travels into one
place without the need to worry about formatting or what information to include.

## User Stories
As a user, I want to:
- Be able to add multiple locations with a name, country, date, rating, and description to my visited list
- Be able to view a list of location names on my visited list 
- Be able to add or change a rating to a location on my visited list
- Be able to select a location and view its details 
- Be able to see how many locations I have visited 
- Be able to modify a location description
- Be able to view the average rating of all my visited locations 
- Be able to remove a location from my visited locations
- Be able to save my visited location list to file
- Be able to load my visited location list from file
- Be prompted to save my visited location list on quit
- Be prompted to load my visited location list on start

## Phase 4: Task 2

For this task, the Location class was made robust.
- The both constructors for Location now check whether all fields are valid and constructed properly.
- changeDateTravelled() method makes sure the new date is not negative
- changeRating() method makes sure the new rating is in range
- changeLocationName() method makes sure the new name field is not empty

## Phase 4: Task 3

Reflection:
- The LocationTrackerGUI class can be made more cohesive by introducing a new class that holds all the functionality of extracting data from the visited location list
- The Menu abstract class can help reduce coupling in the save and load menu buttons by having fields associated with the json reader and writer which its subclasses inherit
- Menu, Tool can also keep track of the VisitedLocationList using its association with the main GUI class which all the subclasses can remove their own associations with VisitedLocationList
- Methods in the button tools can be more organized by making a helper methods with fields to display the popups without having very long method bodies and splitting it between multiple lines
- There is inconsistency in the way that fields are named in the interactive tools which could either be fixed by having their fields inherited or manually fixing them
- Overall, the UML diagram presents many cases in which there is either redundant or unnecessary associations which could be inherited from a superclass to reduce coupling