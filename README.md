# NewsApp_Homework_3
Live News App Project Using Google Room Persistence Library and Third part News api  

Part I

Preliminary

Visit this site and look around a bit: https://newsapi.org/. Sign up and get your free api key. Past this url: https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=   with your api key added to the end in a browser, and look at the results.
In Android Studio, open up the starter code (available at the class assignment repo, that info will be out shortly, if it's not out, you can create your own project to implement this stuff and transfer it to the starter project when it's available).
Network and AsyncTask

Create a new class called NetworkUtils. Define the appropriate base_url and any needed parameter constants (make sure they are Java constants) here as static class members.
Create a public static method in NetworkUtils that uses Uri.Builder to build the appropriate url, the same url as above, and returns a Java URL object. Name it buildURL. Put this method in your NetworkUtils class: (tested with NetworkUnitTest)
Set up permissions to use the internet in the manifest. (if you don't, you will lose a lot of points)
Extend and implement a subclass of AsyncTask, call it NewsQueryTask, to handle the http request, and have its doInBackground. Use NeworkUtils.getRsponsFromHttpUrl to get the json result string. Return the string.
Implement a menu item called get_news in your res directory. Have its title read "Refresh".  Implement the menu item in your activity so that it executes the AsyncTask.

Part II

In this part, you will add to part 1 by parsing the JSON you got from newsapi.org into NewsItem objects. These will be displayed in a RecyclerView. When an item in the RecyclerView is clicked, a browser will be opened to the url of the news item.
Create a NewsItem model class to store information about each news story. You need to include fields (make them of type String) for all of the information in each item (see JSON for what those items are (they will include the article's title and description, and url, and other things), you need to figure out what the information is from the JSON). 
Create a new class JsonUtils. Add a static method public static ArrayList<NewsItem> parseNews(String JSONString) that will parse the JSON you received into an ArrayList<NewsItem>.
Add a RecyclerView named news_recyclerview to your activity's xml layout. Add a new layout for your list item. It should have a LinearLayout with three child TextViews, title, description, date. 
Implement the RecyclerView's adapter, call it NewsAdapter, along with the Holder as an inner class (call it NewsItemViewHolder). Set up the RecyclerView so that it displays, in each item, the item's title, description, and date (it doesn't have to be formatted, just put it in raw). The view's textviews shall also begin as follows:
Title: ...
Description: ...
Date: ...
Implement click listeners for the RecyclerView's items so that, when clicked, a browser is opened to the url for that news item. 

Part III
Add all Gradle dependencies for Room ORM (1pt)
Modify the NewsItem model class to be a Room Entity (3pts)
Add all needed annotations
With your annotations, make the table name be news_item
Also add an int primary key that auto-increments. Name it id. (use @PrimaryKey(autoGenerate = true) as an annotation)
Make sure the class has two constructors, one which takes in parameters to set all the fields, and one like the first except it doesn't take a parameter for, nor sets, the id field. Use @Ignore for the second constructor, so Room doesn't try to use it.
Create a new interface NewsItemDao with the following abstract methods (with appropriate annotations): (3pts)
loadAllNewsItems
insert(List<NewsItem items)
clearAll //clears all current entries in database
All methods that return something should return the appropriate LiveData object (see lecture/Udacity)
Create a Repository class: (5pts)
Create a method that geta all the news items from the database (using NewsItemDao). It should do this in a background thread, use LiveData for this.
Create a method in that syncs the database with the news api. It should clear all current entries in the database, call the api, get a result string, parse that into a list of news objects, and persist that in the database. Use NewsItemDao for all database objects. Do this in an AsyncTask as well (make a separate AsyncTask for this).
Create a NewsItemViewModel class that extends from ViewModel (in the Android Architectures api). In it keep a Repository object. It should keep an instance of a respository and an instance of LiveData<List<NewsItem>>. It should also have a method that returns the LiveData list. Implement any other needed methods. (5pts)
In your Activity, make a NewsItemViewModel instance variable. In onCreate, set the instance variable to a new NewsItemViewModel using ViewModelProviders. (2pts)
Call your method to get the LiveData object, and set an observer to it with the observe method (see example code). In the overridden OnChanged method, set your recyclerview adapter's NewsItem list to the new news Item (passed into the method). (5pts)
In your menu item listener, call the Repository's sync method (1pt)
Finally, make sure that your RecyclerView displays a new set of data drawn from the database (not directly from the api). Do whatever else you need to make this happen. (5pts)


Part IV
mplement a FirebaseJobService that uses your Repository method(s) to sync the database with the news api. (5pts)
Schedule this service to start every 10 seconds with a 10 flex seconds using FirebaseJobDispatcher. It should start up when main activity loads, and should repeat forever. Also, set replace current to true. Make and pass in all the necessary tags for identification by the system. (5pts)
Make sure only one of these (JobService) is running at any time by using a boolean flag (use the example in class as a model). (2pts)
Have the service send a notification with a cancel action, and all needed strings/images. (To get icons/images, right click the drawable folder, select New/Vector asset. Click the image, other icons will be visible. Choose from those). The cancel action should be performed by an IntentService (see the Udacity Hydration App example covered in class) and dismiss the notification. It shouldn't cancel the job scheduling. (8pts)
UI:

Add a field to your NewsItem model object to store the thumb url. (1pt)
Modify your JSON parsing method to get "urlToImage" and place that into each NewsItem. (2pts)
Modify your UI to look like below (use CardView and ConstraintLayout, add Gradle dependencies). (5pts)
Note, I prepended the date before the abstract with a "  .  ". I think this is cleaner than having a textview with date. Note also some extra information is in the date: feel free to remove this if you want, but don't worry about it.
Use Picasso to load the image located at "urlToImage" in each RecyclerView Item. (2pts)
