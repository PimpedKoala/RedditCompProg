# RedditCompProg
This program was created by Joe Finn (/u/PimpedKoala) to aid in the sorting and scoring of reddit.com/r/Cubers weekly competitions. It's free to use and manipulate, however I simply ask that you notify me of any improvements or changes you may make.

How to use:
1) Fill in event names and competition number on right hand side. Be wary of spelling and capitalization
2) Paste a competitor's comment in the main input box and hit add.
3) The program will try to correct any misspellings, bad formatting, or capitalization errors automatically. If there is an error that it detects that it cannot fix, it will notify you before adding. To know a user has been added, the background of the program will cycle between 6 colors.  
4) When all competitors are added, press done and the program will write a file containing the competition results in /Competitions
5) If need be, export the current competition using 'Export', and 'Import' it back when you are ready to continue.

* 'Add' starts the processing of the comment and adds the user to the list of competitors.
* 'Remove last' will remove the most recently added competitor from the list and display the competitor's username
* 'Done' will start the sorting process, update the competitor's total scores, and write the final file
* 'Clear text' will clear all text in the main input panel and both output panels
* 'Import' will process the text copied and pasted from an export file.
* 'Export' will write a file that contains an import code which can be used to save progress
* Left output panel displays an added user's name, their detected events, and times
* Right output panel displays any errors you may encounter
