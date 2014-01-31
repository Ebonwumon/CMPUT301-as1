#tpavlek_as1#

##User howto##
To launch the application, launch tpavlek_as1 from the launcher. The apk to install is in the bin/ directory and is titled "as1.apk"

The main screen, on first launch should have no clickers and you shouldn't be able to do anything. Use the three-dot menu in the top left for application options.

1. New Counter
  * Fill in the clicker name by tapping on the EditText field and typing a name
  * Click Create.
  * Optionally, click the top left to navigate back to the main activity
2. Edit Counter
  * If you have not created any clickers, this button will do nothing
  * Edits the currently-focused counter. If you want to edit a different counter, navigate to it first
  * Change counter name by tapping on the EditText, filling in a name, then click save
  * Zero Counter using the "Zero Count" button - this will delete all logs associated with the Counter
  * Delete the counter using the delete button
  * Optionally, tap in the upper left to navigate back to the main activity
3. List Counters
  * Lists all available counters sorted by number of counts, descending
  * Tap any counter to be brought to a statistics view.
  * Optionally, tap in the upper left to return to the previous view.
4. Actions with the Current Counter
  * If you have created at least one counter, you can click the increment button to increase the count of the counter by one.
  * If you have more than one counter you can use the previous/next buttons to switch your view to those other counters
 
  
Some notes: There is no data-validation done on the JSON. If the user manually enters the filesystem and edits the stored JSON files, there will be breakage/crashes. Don't do that. Also note, if you zero a counter, the logs will persist. This is intentional behaviour because just because you reset a counter doesn't mean the statistics of when you incremented it aren't valuable

##Class Responsibilities##

The primary data-holding models are Clicker.java and Log.java. Each of those are responsible for the getting/setting of their own data as well has housing static factories for themselves when passed a JSON string of an array of models. The modification of this data by the client is all passed through ClickerController/LogController.

Aggregation is handled through the TimeAggregateCount.java class. Display of these aggregations is handled by the Hourly, Daily, Weekly, etc AggregateCounts which override toString to more acurately display what they are in a listview. Initialization of this Aggregation is primarily handled by static methods in the AggregateCounts.java class.

Root interaction with the User is primarily handled through the Base Activity ClickerActivity.java. This Activity is the hierarchal parent to NewClickerActivity, EditClickerActivity and ClickerOverViewActivity.

ClickerOverView activity is the hierarchal parent of ClickerStatsActivity.

All saving is done by the client as soon as a change is made. eg. in ClickerActivity:

```java
clickerController.current().increment();
logController.logClick(clickerController.current().getId());
try {
	// We need to commit immediately
	File.writeString(openFileOutput("clickers.json", MODE_PRIVATE), clickerController.toJSON());
	File.writeString(openFileOutput("log.json", MODE_PRIVATE), logController.toJSON());
} catch (FileNotFoundException e) {
	System.err.println("error writing file - clicker not saved");
}
```

The file saving is handled by static methods in the File class.
