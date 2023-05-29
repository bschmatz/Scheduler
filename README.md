# MySchedule

## Description

This is a scheduling app, that allows users to view their schedules and enlist in courses.
In addition to standard users, there are also admin users, who can create events.
Assistants can wish for a specific event to be created.

## How does it work?

Upon starting the app, you will be either asked to create an account or log in. Note that you can only create an Assistant or Student.

After logging in, you can view your schedule, and if you are a student, you can also enlist in courses. As an assistant you are given the option to "make a wish"

Admins can view current wishes and create an event if they want to.

## How to run it?

This app is made for a MYSQL database, so you will need to have that installed. You will also need to have a server running on your machine.
Note that you need to have the correct database-schema or the app will not work. You can find a generated sql-script in [datebase-schema.sql](database-schema.sql).

## How to use it?

When you first open the program, you can choose between logging in, or creating a new User. Note that you won't be able to create a new admin.
After logging in, depening on your user type, you will be able to do different things.

### Admin

The admin is has by far the most extensive interface. They can view all events, rooms and courses and add/delete/edit them as they please. At the top of the screen you will have multiple tabs.
They should be pretty self-explanatory. Note that in order to edit an event, you have to click it while on the "Edit" tab, and a dialog will open.
Wishes that have been added by Assistants can also be fulfilled.

### Assistant

The assistant can add requests, or "wishes" as I called them, and submit them to the database. They can also view their own schedule, and their current wishes.

### Student

The student can see a list of events in which he is enrolled, and can also enroll in new events.
Luckily for them, they can also choose to sign out of an event, if they no longer wish to attend.

## More information

This app was made as a project for the course "Databases" at FH Joanneum Graz, Austria.
I tried to keep the code readable, keep in mind however that this is my first time working with JavaFX, so it might not be the best code you've ever seen.
If you have any questions, feel free to leave messages on my [GitHub](https://github.com/bschmatz)
