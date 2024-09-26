# Flash Cards Application

## Description
The Flash Cards App is the application whose function is to create study cards and help users learn something new, such as foreign words, definitions, or dates.

## Key Features
The Flash Cards Application contains the following functionality:
1) Flipping cards
2) Card sets by themes
3) Easy card management
4) Progress and score tracking
5) Quick navigation

minSdk = 27; targetSdk = 34. Flash Cards App supports platform 8.1 (Oreo) and above.

## Technologies Used
*	Android Architecture: UI, Data
*	Room database
*	ViewModel
*	LiveData
*	RecyclerView
*	View Bindings
*	Navigation Graph
*	Save Args
*	ActionBarWithNavController
*	Popup menu
*	Spinner
*	Scope functions: apply
*	Constraint Layout
*	Flow
*	Coroutines
*	ObjectAnimator
*	ViewPager2
*	Progress Bar
*	AlertDialog
*	Landscape View
*	Snackbar
*	Styles
*	Light and Dark Themes
*	DataStore
*	SplashScreen API

## Detailed Description
Flash Cards App is a flash card application whose function is to create study cards and help users learn something new, such as foreign words, definitions, dates, and so on. 
This description aims to show the structure of the study project, its functionality, and what instruments have been implemented.

* The app starts with a list of sets of cards grouped by themes. If it is the first start of the application, an example set is shown for better understanding of the app structure.

  From the main page, a user can create a new set of cards, start practicing an existing set of cards, delete or edit the set, add new cards to the existing set of cards, change the app theme (dark / light), or use a spinner to choose the set from the list.
  All cards are written to the Room database; a set name is one of the columns in the table.
  A list of card sets is shown on the screen using RecyclerView.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/ee9ddde4-3e2f-4ed5-bf9b-7a074df4be75" width="298" height="628">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/5b3e74ea-6561-4883-baf0-b0baf4803c41" width="299" height="625">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/c629f173-60b3-4edc-95c1-d444c19f8b9b" width="294" height="629">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/411c9c1d-9efe-448e-ae2c-41368cc16eb6" width="299" height="630">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/74b86791-41c8-4762-8a60-36048a06edd6" width="304" height="631">

&nbsp;
* When creating a new set, a set name is required. If it is missing, a warning appears.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/5d4d6329-5959-4861-b95b-44ce46825286" width="303" height="628">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/d27d59b3-e3a3-4241-9680-5fbac4e58a50" width="302" height="631">

&nbsp;
* To edit a set means to edit the name of the set. When deleting the set, the AlertDialog appears. If a user clicks to delete the set, all the cards with this set name will be deleted from the Room database.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/2c36c83a-4817-4aee-a232-10da745f914a" width="298" height="630">

&nbsp;
* The app supports two themes: dark and light. The chosen theme is saved in the DataStore.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/56484d48-1086-47be-879a-0f1067ce10f6" width="301" height="631">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/8b2bbcef-face-4a6e-a768-69981bee43ec" width="293" height="630">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/1c9e862b-1b34-4e27-ba30-08c6adff68ca" width="293" height="631">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/f8940fa0-fbda-4fd2-b087-e890cc78acf4" width="304" height="631">

&nbsp;
* When adding a new card to the set, a question is required, and an answer is optional. If the question is missing, a warning appears.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/a3d81135-65ef-4f88-a730-a7ca2d45c792" width="295" height="629">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/0361dc2c-f36c-4085-9e89-b7d9ddd0ab18" width="296" height="629">

&nbsp;
* A user can start to practice a set or add new cards from the set fragment as well as from the main page. A list of cards is shown on the screen using RecyclerView.

   <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/73eff04b-be56-4c79-bdd1-2c89ae431752" width="300" height="628">

&nbsp;
* When practicing cards, a card appears on the screen as a flash card that can be turned: a question is on one side and the answer is on the other. The Animator is used for flipping the card, and the ViewPager2 is used for swiping cards.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/1e18f7c6-1675-41ee-8d0b-b00d99e87e4f" width="302" height="629">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/13ba2576-6c6f-4c40-b55f-4772aba32af3" width="298" height="628">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/9da3fc0a-e375-4365-9d6f-608fb8461732" width="296" height="634">

&nbsp;
* While swiping cards, a user can track their progress by clicking the True or False buttons. While swiping, a progress bar at the top of the screen shows how much is left until the end of the set.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/a0bddee7-da61-4ff0-b6ab-6dab3316ff57" width="304" height="629">

&nbsp;
* At the end of a practice session, a summary page is shown with a summary of the right and wrong answers. If a card was swiped and a user didn’t press the True or False buttons, this card isn’t tracked and will not be shown on the summary page. From the summary page, a user can repeat their practice session with the same set of cards or return to the main page.
  
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/1638b265-e3dd-4e7a-aca6-cc3ca6c68422" width="297" height="626">

&nbsp;
* Every card in the set can be edited and deleted. To edit the card means to change the question or answer. In this case, a card will be overwritten in the Room database by its ID. When deleting a card, the AlertDialog is shown. If a user chooses to delete a card, its row will be deleted from the Room database.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/09a80f9c-9d0c-4966-9257-4fa48f35506c" width="297" height="628">

&nbsp;
* The navigation graph looks like this:

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/82abcb34-a43f-4bec-97ea-74f9d9ddef5f" width="650" height="463">

&nbsp;
* Flash Cards App supports a Landscape View.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/c12b79a4-a80e-4247-8de7-64de75036f62" width="767" height="366">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/20a1b192-8f0c-42c3-afda-d85ed15072dc" width="766" height="370">
  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/7fddbf96-5322-4c35-83a1-0dd57e15d64a" width="765" height="369">

&nbsp;
* Flash Cards App has an icon.

  <img src="https://github.com/xeniamlkh/FlashCardApp/assets/89986215/00e3bcbe-39a9-4475-958f-34ef3c6aa8d3" width="307" height="633">

