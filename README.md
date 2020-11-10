# Task Master

### Version 1.0
![Home Screen Lab 26](screenshots/lab26-HomePage.png)

- Built home page
- Add Task button that links to Add Task page
- All Tasks button that links to All Tasks page

### Version 2.0
![Home Screen Lab 27](screenshots/lab27-HomePage.png)
![Settings Page Lab 27](screenshots/lab27-SettingsPage.png)
![First Task Lab 27](screenshots/lab27-firstDetail.png)
![Second Task Lab 27](screenshots/lab27-secondDetail.png)
![Third Task Lab 27](screenshots/lab27-thirdDetail.png)

- Home page updated with Settings button and 3 taskLocal buttons
- Settings page added with username saved to the home screen
- Task buttons 1, 2, 3 that link and update title depending on what is selected.

### Version 3.0
![Home Screen Lab 28](screenshots/lab28-HomePage.png)

![Details Screen Lab 28](screenshots/lab28-detailPage.png)


- Home page updated with RecyclerView that scrolls and you click on the title
- TaskAdapter class that displays data from a list of Tasks
- Task Model added. Task has title, body and a sta

### Version 4.0
![Home Screen Lab 29](screenshots/lab29-HomePage.png)

![Add Task Screen Lab 29](screenshots/lab29-AddTask.png)

- Modify Add Task form to save the data entered in as a Task in your local database.
- Home page updated RecyclerView to display all Task entities in database
- Detail Page has description and status of a tapped taskLocal. Also displayed on the detail page in addition to the title.

### Version 5.0

- Added Expresso tests.
- verified all features are working
- no new features to take pictures of

### Version 6.0
![Home Screen Lab 32](screenshots/lab32-HomePage.png)

![Add Task Screen Lab 32](screenshots/lab32-aws.png)

- Using `amplify add api` updated all Task data to instead use AWS Amplify
- Modify Add Task to save the data entered in as a task to DynamoDB
- Refactor homepage RecyclerView to display all Task entities in DynamoDB

### Version 7.0
![Settings Lab 33](screenshots/lab33-settings.png)

![Add Task Lab 33](screenshots/lab33-addTask.png)

- create a second entity for a team 
- create 3 teams
- Modify Add Task to include radio buttons for which team that task belongs to
- Settings page updated to allow username and choosing their team.

### Version 8.0
![Home Page Lab 33.5](screenshots/lab33-daisy.png)

![Home Page Lab 33.5](screenshots/lab33-minnie.png)

- Home page only shows tasks for the team the user selects on the settings page.
- Added a text view to display the team the user is on

### Version 9.0
![Home Page Lab 36](screenshots/lab36-homepage.png)

![Home Page Lab 36](screenshots/lab36-signup.png)

![Home Page Lab 36](screenshots/lab36-loggedIn.png)

- Home page added Sign up, Login and Sign out buttons
- Added Cognito to Amplify setup. Added user login and sign up. Displays logged in username on the homepage.
- Logout button to sign out of the application

## Version 10.0
![Details Page Lab 37](screenshots/lab37-detailsPage.png)

![Add Task Page Lab 37](screenshots/lab37-imageAddTask.png)

- On Add Task activity allow user to select a file to attach to that task.
- On Task detail activity if there is an image display within that activity

### Version 11.0
![AWS notification Lab 38](screenshots/lab38-awsNotification.png)

![Firebase emulator Lab 38](screenshots/lab38-emulator2.png)

![Firebase notification Lab 38](screenshots/lab38-firebase.png)

![Firebase emulator Lab 38](screenshots/lab38-emulatorNotification.png)

- Added notification service through AWS and Firebase

### Version 12.0

![Analytics Lab 39](screenshots/lab39-analytics2.png)

- Added Analytics when opening the app, adding a task on the add task page and picking a team on the setting page


### Version 13.0

![Add image to app Lab 41](screenshots/lab41-add-to-app.png)
![Task Detail Lab 41](screenshots/lab41-taskdetail.png)

- Added an intent fileter to add an image from another application. Taken to the Add a task page. Able to save to task to a team.



