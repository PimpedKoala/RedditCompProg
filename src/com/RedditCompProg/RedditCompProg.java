package com.RedditCompProg;

/*
 * Created by /u/PimpedKoala 2015-2016
 * Public release v1.0
 * Any questions/comments PM me on reddit(username above) or email me at fat.fleabag@yahoo.com
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;

public class RedditCompProg extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//globals
	static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    static final int SCREEN_WIDTH = (int)(SCREEN_SIZE.getWidth()); 
    static final int SCREEN_HEIGHT = (int)(SCREEN_SIZE.getHeight()); 
    static final int FRAME_WIDTH = 600; 
    static final int MAIN_HEIGHT = 435; 
    static final int OUT_HEIGHT = 200;
    static final int FRAME_HEIGHT = MAIN_HEIGHT + OUT_HEIGHT;
    static final Color BG_COLOR = new Color(255, 210, 64), ERROR_COLOR = new Color(255, 115, 115);
    
    int place;
    JPanel contentPane, eventPanel;
    Highlighter hl;
    ArrayList<Competitor> people;
	int amount;
	static final String BR = "\n\n";
	JScrollPane inputScroll, errorOutScroll;
    JTextArea input, comp, errorOut, compOut;
    ArrayList<JTextArea> events;  
    JButton add, done, clear, addData, exData, remove;
    int colorNum = (int)(Math.random() * 6);
    
	public static void main(String[] args) {
		RedditCompProg frame = new RedditCompProg();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	public RedditCompProg() { //default constructor, sets up frame and such
    	people = new ArrayList<Competitor>();
		contentPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBounds(SCREEN_WIDTH / 2 - FRAME_WIDTH / 2, SCREEN_HEIGHT / 2 - FRAME_HEIGHT / 2, FRAME_WIDTH, FRAME_HEIGHT);
    	contentPane.setLayout(null);
    	setContentPane(contentPane);
    	contentPane.setBackground(BG_COLOR);
    	
    	input = new JTextArea();
    	input.setText("Paste the competitor's comment here...");
    	input.setSelectionColor(ERROR_COLOR);
    	inputScroll = new JScrollPane(input);
    	inputScroll.setBounds(5, 5, (int)(FRAME_WIDTH / 1.5), MAIN_HEIGHT - 115);
    	inputScroll.setBorder(null);
    	inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    	inputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    	
    	add = new JButton("Add");
    	add.setBounds(5, 10 + MAIN_HEIGHT - 115, (int)((FRAME_WIDTH - 25) / 3), 30);
    	add.addActionListener(this);
    	done = new JButton("Done");
    	done.setBounds(15 + 2 * (int)((FRAME_WIDTH - 25) / 3), 10 + MAIN_HEIGHT - 115, (int)((FRAME_WIDTH - 25) / 3), 30);
    	done.addActionListener(this);
    	clear = new JButton("Clear text");
    	clear.setBounds(5, 45 + MAIN_HEIGHT - 115, (int)((FRAME_WIDTH - 25) / 3), 30);
    	clear.addActionListener(this);
    	addData = new JButton("Import");
    	addData.setBounds(10 + (int)((FRAME_WIDTH - 25) / 3), 45 + MAIN_HEIGHT - 115, (int)((FRAME_WIDTH - 25) / 3), 30);
    	addData.addActionListener(this);
    	remove = new JButton("Remove last");
    	remove.setBounds(10 + (int)((FRAME_WIDTH - 25) / 3), 10 + MAIN_HEIGHT - 115, (int)((FRAME_WIDTH - 25) / 3), 30);
    	remove.addActionListener(this);
    	exData = new JButton("Export");
    	exData.setBounds(15 + 2 * (int)((FRAME_WIDTH - 25) / 3), 45 + MAIN_HEIGHT - 115, (int)((FRAME_WIDTH - 25) / 3), 30);
    	exData.addActionListener(this);
    	
    	eventPanel = new JPanel();
    	eventPanel.setLayout(null);
    	eventPanel.setBackground(BG_COLOR);
    	eventPanel.setBounds((int)(FRAME_WIDTH / 1.5 + 10), 5, (int)(FRAME_WIDTH - FRAME_WIDTH / 1.5 + 5), MAIN_HEIGHT - 40);
    	
    	events = new ArrayList<JTextArea>();
    	
    	JLabel eventLabel = new JLabel("Events");
    	eventLabel.setBounds(0, 50, FRAME_WIDTH, 15);
    	eventLabel.setBackground(BG_COLOR);
    	eventPanel.add(eventLabel);
    	
    	JLabel compLabel = new JLabel("Competition");
    	compLabel.setBounds(0, 0, FRAME_WIDTH, 15);
    	compLabel.setBackground(BG_COLOR);
    	eventPanel.add(compLabel);
    	
    	comp = new JTextArea();
    	comp.setBounds(0, 20, (int)(FRAME_WIDTH - FRAME_WIDTH / 1.5 - 30), 20);
    	eventPanel.add(comp);
    	
    	for(int a = 0; a < 10; a++) {
        	events.add(new JTextArea());
        	events.get(a).setBounds(0, 70 + a * 25, (int)(FRAME_WIDTH - FRAME_WIDTH / 1.5 - 30), 20);
        	if(a == 0)
        		events.get(a).setText("2x2");
        	else if(a == 1)
        		events.get(a).setText("3x3");
        	else if(a == 2)
        		events.get(a).setText("4x4");
        	else if(a == 3)
        		events.get(a).setText("3x3OH");
        	else if(a == 7)
        		events.get(a).setText("2GEN");
        	else if(a == 8)
        		events.get(a).setText("LSE");
        	else if(a == 9)
        		events.get(a).setText("OLL");
        	
    		eventPanel.add(events.get(a));
    	}
    	
    	errorOut = new JTextArea();
    	errorOut.setLineWrap(true);
    	errorOut.setWrapStyleWord(true);
    	errorOutScroll = new JScrollPane(errorOut);
    	errorOutScroll.setBounds((int)(FRAME_WIDTH / 2 + 2.5), MAIN_HEIGHT - 35, (int)(FRAME_WIDTH / 2 - 12.5), OUT_HEIGHT);
    	errorOutScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    	errorOutScroll.setBorder(null);
    	errorOut.setEditable(false);
    	
    	compOut = new JTextArea();
    	compOut.setLineWrap(true);
    	compOut.setWrapStyleWord(true);
    	compOut.setEditable(false);
    	compOut.setBounds(5, MAIN_HEIGHT - 35, (int)(FRAME_WIDTH / 2 - 7.5), OUT_HEIGHT);
    	
    	contentPane.add(add);
    	contentPane.add(done);
    	contentPane.add(clear);
    	contentPane.add(addData);
    	contentPane.add(exData);
    	contentPane.add(remove);
    	contentPane.add(eventPanel);
    	contentPane.add(inputScroll);
    	contentPane.add(errorOutScroll);
    	contentPane.add(compOut);
    }

	public Color getNextColor() { //for bg color
		colorNum++;
		if(colorNum % 6 == 0)
			return new Color(255, 210, 64);
		else if(colorNum % 6 == 1)
			return Color.white;
		else if(colorNum % 6 == 2)
			return new Color(8, 232, 0);
		else if(colorNum % 6 == 3)
			return new Color(0, 233, 245);
		else if(colorNum % 6 == 4)
			return new Color(255, 149, 0);
		else 
			return new Color(255, 84, 71);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { 
		if(e.getActionCommand().equals("Add")) {
			compOut.setText("");
			errorOut.setText("");
				String comment = process(input.getText());
				try {
					if(!testComment(comment)) {
						Competitor c = new Competitor(comment, findEvents(comment));
						people.add(c); //stores a giant list of Competitors
						Color bg = getNextColor();
						compOut.setText(c.getName() + "\n");
						for(int a = 0; a < c.getNumTimes(); a++) 
							compOut.setText(compOut.getText() + c.getEvents().get(a) + ": " + c.getTimes().get(a) + "\n");
						
						contentPane.setBackground(bg);
						eventPanel.setBackground(bg);
					}
				} catch (BadLocationException e1) {
					compOut.setText("User not added due to errors.");
				}
		}
		
		if(e.getActionCommand().equals("Remove last")) { //self explanatory
			input.setText("Paste the competitor's comment here..."); 
			errorOut.setText("");
			if(people.size() > 0) {
				compOut.setText("Removed " + people.remove(people.size() - 1).getName() + ".");
				Color bg = getNextColor();
				contentPane.setBackground(bg);
				eventPanel.setBackground(bg);
			}
			
			else
				compOut.setText("No more people to remove.");
		}
		
		else if(e.getActionCommand().equals("Done")) { //sends the list of competitors to be sorted and printed
			String s = "";
			for(int a = 0; a < events.size(); a++) 
				s += events.get(a).getText() + "=";			
			
			try {
				export(s + "-" + comp.getText() + "/" + people.size() + people.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			String full = sort(people);
			try {
				full += editTotals(people);
				writeToFile(full);
			} catch (IOException e2) {
				errorOut.setText(errorOut.getText() + "Error finding file.\n");
				compOut.setText("User not added due to errors.");
			}
		}
		
		else if(e.getActionCommand().equals("Clear text")) { //removes junk in between the event and the average. There are a couple of instances where this bugs out atm
			compOut.setText("");
			errorOut.setText("");
			input.setText("Paste the competitor's comment here...");
		}
		
		else if(e.getActionCommand().equals("Export")) {
			String s = "";
			for(int a = 0; a < events.size(); a++) 
				s += events.get(a).getText() + "=";
			
			try {
				export(s + "-" + comp.getText() + "/" + people.size() + people.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			input.setText(people.toString());
		}
		
		else if(e.getActionCommand().equals("Import")) { //I later realized this could have been *much* easier, but as of now it basically processes Competitors toString()'s
			String text = input.getText();
			int buff = 0;
			String[] lines = text.split("=");
			
			for(int a = 0; a < lines.length - 1; a++) {
				events.get(a).setText(lines[a]);
			}
			
			comp.setText(text.substring(text.indexOf("-") + 1, text.indexOf("/")).trim());
			
			for(int a = 0; a < Integer.parseInt(text.substring(text.indexOf("/") + 1, text.indexOf("["))); a++) {
				buff = text.indexOf("Username", buff);
				String name = text.substring(text.indexOf("Username", buff) + 8, text.indexOf("Events", buff));
				String nE;
				if(a != Integer.parseInt(text.substring(text.indexOf("/") + 1, text.indexOf("["))) - 1)
					nE = text.substring(text.indexOf("Number of events", buff) + 16, text.indexOf(",", text.indexOf("Number of events", buff)));
				else
					nE = text.substring(text.indexOf("Number of events", buff) + 16, text.indexOf("]", text.indexOf("Number of events", buff)));
				int n = Integer.parseInt(nE);
				ArrayList<String> events = new ArrayList<String>();
				ArrayList<String> times = new ArrayList<String>();
				int evBuff = 0, tBuff = 0;
				for(int b = 0; b < n; b++) {
					if(b < n - 1) {
						String ev = text.substring(text.indexOf("Events", buff) + 7 + evBuff, text.indexOf(",", text.indexOf("Events", buff) + 7 + evBuff)).trim();
						events.add(ev);
						evBuff += ev.length() + 2;
						
						ev = text.substring(text.indexOf("Times", buff) + 6 + tBuff, text.indexOf(",", text.indexOf("Times", buff) + 6 + tBuff)).trim();
						times.add(ev);
						tBuff += ev.length() + 2;
					}
					else {
						String ev = text.substring(text.indexOf("Events", buff) + 7 + evBuff, text.indexOf("]", text.indexOf("Events", buff) + 7 + evBuff));
						events.add(ev);
						
						ev = text.substring(text.indexOf("Times", buff) + 6 + tBuff, text.indexOf("]", text.indexOf("Times", buff) + 6 + tBuff));
						times.add(ev);
					}
						
				}
				people.add(new Competitor(name, events, times));
				buff++;
			}
			input.setText("Paste the competitor's comment here...");
		}
			
	}
	
	public void swap(ArrayList<Competitor> list, int index1, int index2) { //basic swap for sorting
		Competitor temp = list.get(index1);
		
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
	
	public void swapI(ArrayList<Integer> list, int index1, int index2) { //swap indexes of events for a competitor
		Integer temp = list.get(index1);
		
		list.set(index1, list.get(index2));
		list.set(index2, temp);
	}
	
	/* sorts stuff low -> high
	 * I recently was informed that there is a built in sort() method, but since I already wrote all of these methods I'm not bothering
	 */
	
	public void selectionSort(ArrayList<Competitor> list, ArrayList<Integer> index) {
		int minIndex;
		boolean newMinFound = false;
		
		for(int a = 0; a < list.size() - 1; a++) {
			minIndex = a;
			for(int b = a + 1; b < list.size(); b++) {
				if(Double.parseDouble(list.get(b).getTimes().get(index.get(b))) < Double.parseDouble(list.get(minIndex).getTimes().get(index.get(minIndex))))  {
					minIndex = b;
					newMinFound = true;
				}
			}
			
			if(newMinFound) {
				swap(list, a, minIndex);
				swapI(index, a, minIndex);
				a = 0;
			}
			newMinFound = false;
		}
	}
	
	public void scoreSelectionSort(ArrayList<Competitor> list) { //high -> low
		int maxIndex;
		boolean newMaxFound = false;
		
		for(int a = 0; a < list.size() - 1; a++) {
			maxIndex = a;
			for(int b = a + 1; b < list.size(); b++) {
				if(list.get(b).getPoints() > list.get(maxIndex).getPoints())  {
					maxIndex = b;
					newMaxFound = true;
				}
			}
			
			if(newMaxFound) {
				swap(list, a, maxIndex);
				a = 0;
			}
			newMaxFound = false;
		}
	}
	
	
	public String sort(ArrayList<Competitor> comps) { //the majority of stuff goes on here
		String full = "Results for Competition " + comp.getText();
		int compCount;
		ArrayList<Competitor> temp;
		ArrayList<Integer> index;
		
		for(int a = 0; a < events.size(); a++) { //sort each event
			temp = new ArrayList<Competitor>(); 
			index = new ArrayList<Integer>();
			place = 1;
			compCount = 0;
			full += BR + "**" + events.get(a).getText() + "**" + BR;
			for(Competitor competitor:comps) {
				if(competitor.getEvents().contains(events.get(a).getText())) {
					index.add(competitor.getEvents().indexOf(events.get(a).getText())); /*index of event for the competitor (ie if I only do 3x3, it will be at index 0 as opposed
																						 to someone who has 2x2 and 3x3, where 3x3 would be in index 1*/
					temp.add(competitor); //the list to hold all competitors that competed in the event
					compCount++;
				}				
			}
			
			selectionSort(temp, index);
			for(int b = 0; b < temp.size(); b++) {
				if(Double.parseDouble(temp.get(b).getTimes().get(index.get(b))) >= 60.0) { //converts times over a minute into normal format, which are originally stored as decimals
					int dec = Integer.parseInt(temp.get(b).getTimes().get(index.get(b)).substring(temp.get(b).getTimes().get(index.get(b)).length() - 2)); 
					int secs = Integer.parseInt(temp.get(b).getTimes().get(index.get(b)).substring(0, temp.get(b).getTimes().get(index.get(b)).length() - 3));
					int mins = secs / 60;
					secs %= 60;
					if(secs < 10) {
						if(dec < 10)
							temp.get(b).getTimes().set(index.get(b), mins + ":0" + secs + ".0" + dec);
						else
							temp.get(b).getTimes().set(index.get(b), mins + ":0" + secs + "." + dec);
							
					}
					else {
						if(dec < 10)
							temp.get(b).getTimes().set(index.get(b), mins + ":" + secs + ".0" + dec);
						else
							temp.get(b).getTimes().set(index.get(b), mins + ":" + secs + "." + dec);
					}
					
					full += place + ". /u/" + temp.get(b).getName() + ": " + (temp.get(b).getTimes().get(index.get(b))) + BR;
					temp.get(b).addPoints(1 + compCount - place);
					//convert back to decimal. I honestly forget why I did this
					mins = Integer.parseInt(temp.get(b).getTimes().get(index.get(b)).substring(0, temp.get(b).getTimes().get(index.get(b)).indexOf(":"))); 
					secs = Integer.parseInt(temp.get(b).getTimes().get(index.get(b)).substring(temp.get(b).getTimes().get(index.get(b)).indexOf(":") + 1, temp.get(b).getTimes().get(index.get(b)).indexOf(".")));
					dec = Integer.parseInt(temp.get(b).getTimes().get(index.get(b)).substring(temp.get(b).getTimes().get(index.get(b)).indexOf(".") + 1, temp.get(b).getTimes().get(index.get(b)).indexOf(".") + 3));
					
					secs += mins * 60;
					temp.get(b).getTimes().set(index.get(b), secs + "." + dec);
				}
				
				else {
					full += place + ". /u/" + temp.get(b).getName() + ": " + (temp.get(b).getTimes().get(index.get(b))) + BR;
					temp.get(b).addPoints(1 + compCount - place);
				}
				place++;
			}
		}

		full += BR + "**Points from this week: (Points = # of people - place + 1)** " + BR;
		place = 1;
		
		ArrayList<Competitor> scores = comps;
		scoreSelectionSort(scores);
		
		for(int a = 0; a < scores.size(); a++) {
			full += place + ". /u/" + scores.get(a).getName() + ": " + scores.get(a).getPoints() + BR;
			place++;
		}
		
		return full;
	}
	//I could have made one method for all this text stuff but that's too logical :p
	public void writeToFile(String text) throws IOException { //this one writes the actual results
		String path = "Competitions\\" + comp.getText() + ".dat";
		
		BufferedWriter writer = null;
        try {
            File logFile = new File(path);

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
	
	public void export(String text) throws IOException { //this one writes export data
		String path = "Exports\\comp_" + comp.getText() + "_data.dat";
		
		BufferedWriter writer = null;
        try {
            File logFile = new File(path);

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
	}
	
	public String editTotals(ArrayList<Competitor> comps) throws IOException { //writes the total cumulative score
		String oldPath = "totalScores.dat";
		String tempPath = "temp_totalScores.dat";
		
		BufferedWriter writer = null;
		BufferedReader reader = null;
        try {
        	reader = new BufferedReader(new FileReader(oldPath));
        	writer = new BufferedWriter(new FileWriter(tempPath));
        	ArrayList<String> lines = new ArrayList<String>(); //compiled list of lines
            String line = reader.readLine(); //single line
            
            while(line != null) {
            	lines.add(line);
            	line = reader.readLine();
            }
            
            
            lines = updatePoints(lines, comps);
            
            String full = BR + "**Total Points:** " + BR;
    		place = 1;
    		
    		ArrayList<Competitor> allComps = new ArrayList<Competitor>();
    		for(int a = 0; a < lines.size(); a++) {
    			int colInd = lines.get(a).indexOf(":");
            	allComps.add(new Competitor(lines.get(a).substring(0, colInd), Integer.parseInt(lines.get(a).substring(colInd + 2).trim())));
            }
            
    		scoreSelectionSort(allComps);
    		
    		for(int a = 0; a < lines.size(); a++)
    			writer.write(lines.get(a) + "\n");
    		
            for(int a = 0; a < allComps.size(); a++) {
            	full += place + ". " + allComps.get(a).getName() + ": " + allComps.get(a).getPoints() + BR;
            	place++;
            }

            Runtime.getRuntime().exec("cmd /c start rn.bat");
            return full;
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        } finally {
           try {
              if(reader != null)
                 reader.close();
           } catch (IOException e) {
              //
           }
           try {
              if(writer != null)
                writer.close();
          } catch (IOException e) {
              //
           }
        }
        
	}
	
	public ArrayList<String> updatePoints(ArrayList<String> lines, ArrayList<Competitor> comps) { //goes through and determines whether they have competed before, and adds points accordingly
		ArrayList<String> newLines = new ArrayList<String>();
		for(int b = 0; b < lines.size(); b++) {
			boolean found = false;
			String line = lines.get(b).trim();
			int colInd = line.indexOf(":");			
			String oldPoints = line.substring(colInd + 1).trim();
			int oldPointsI = Integer.parseInt(oldPoints);
			for(int a = 0; a < comps.size(); a++) {
	    		if(line.indexOf(comps.get(a).getName()) > -1) {
	    			int total = comps.get(a).getPoints() + oldPointsI;
	    			newLines.add("/u/" + comps.get(a).getName() + ": " + total);
	    			found = true;
	    			comps.get(a).isXP();
	    			comps.get(a).setPoints(total);
	    		}	    		
	    	}
			
			if(!found) {
				newLines.add(line);
			}
		}
		
		for(int a = 0; a < comps.size(); a++) {
			if(!comps.get(a).getIsXP()) {
				newLines.add("/u/" + comps.get(a).getName() + ": " + comps.get(a).getPoints());
			}
		}
		return newLines;
	}
	
	/* finds all of the events that a competitor competes in. 
	 *I forget why it was necessary to do this here and not in competitor (it probably wasn't necessary I'm not sure)
	 */
	
	public ArrayList<String> findEvents(String s) { 
		ArrayList<String> myEvents = new ArrayList<String>();
		
		for(int a = 0; a < events.size(); a++) {
			if(!events.get(a).getText().isEmpty() && s.indexOf(events.get(a).getText() + ":") >= 0) {
				myEvents.add(events.get(a).getText());
			}
		}
		return myEvents;
	}
	
	/* Fix errors:
	 * Flair
	 * Missing colons
	 * Bad spelling/capitalization
	 * Most equal sign errors
	 */
	
	public String process(String s) {
    	String[] lines = s.split("\n");
		s = "";
		for(int a = 0; a < lines.length; a++) {
			String line = lines[a].trim();
			if(a == 0) {
				if(line.endsWith("ago") || line.endsWith("ago*")) {
					if(line.indexOf("Sub") > 0) {
						line = line.substring(0,  line.indexOf("Sub"));
					}
					
					else if(line.indexOf("sub") > 0) {
						line = line.substring(0,  line.indexOf("sub"));
					}
					
					else if(line.indexOf("2015GIUN01") > 0) { // /u/Zoltorion
						line = line.substring(0, line.indexOf("2015GIUN01"));
					}	
					
					else if(line.indexOf("2014SCHO02") > 0) { // /u/calesch
						line = line.substring(0, line.indexOf("2014SCHO02"));
					}	
					
					else if(line.indexOf("CN CFOP - (1/100) 9.037/17.37") > 0) { // /u/mjmaher81
						line = line.substring(0, line.indexOf("CN CFOP - (1/100) 9.037/17.37"));
					}	
					
					/*else if(line.indexOf("(\\d{4})([A-Z]{4})(\\d{2})") > 0) { 
						line = line.substring(0, line.indexOf("(\\d{4})([A-Z]{4})(\\d{2})"));
					}
					* Any idea why this doesn't work? Does regex not apply in indexOf()?
					*/	
					
					else if(line.indexOf(" ") > 0) {
						line = line.substring(0,  line.indexOf(" "));
					}
				}
			}
			
			line = line.replaceAll("^3x3 OH", "3x3OH"); //3x3OH
			line = line.replaceAll("^3OH", "3x3OH:");
			line = line.replaceAll("^OH", "3x3OH");
			
			line = line.replaceAll("^5x5x5", "5x5"); //5x5
			line = line.replaceAll("^3x3BLD", "BLD"); //BLD
			line = line.replaceAll("^3BLD", "BLD");
			line = line.replaceAll("^bld", "BLD");
			line = line.replaceAll("^Skoob", "Skewb"); //Skewb
			line = line.replaceAll("^Pyra:", "Pyraminx"); //Pyraminx
			line = line.replaceAll("^pyra", "Pyraminx"); //Pyraminx
			
			line = line.replaceAll("^6x6x6", "6x6"); //6x6x6
			line = line.replaceAll("^7x7x7", "7x7"); //7x7x7
			line = line.replaceAll("^Mirror Blocks", "3x3 Mirror blocks/Bump"); //Mirror Cube
			line = line.replaceAll("^3x3 Mirror Blocks", "3x3 Mirror blocks/Bump"); //Mirror Cube
			line = line.replaceAll("^3x3 Mirror Blocks/Bump", "3x3 Mirror blocks/Bump"); //Mirror Cube
			line = line.replaceAll("^3x3 Mirror Blocks/bump", "3x3 Mirror blocks/Bump"); //Mirror Cube
			line = line.replaceAll("^3x3 Mirror blocks/bump", "3x3 Mirror blocks/Bump"); //Mirror Cube
			line = line.replaceAll("^square-one", "Square-1"); //Square-1
			line = line.replaceAll("^Relay", "3x3 Relay of 3"); //3x3 Relay of 3
			line = line.replaceAll("^2-3-4Relay", "2-3-4 Relay"); //2-3-4 Relay
			line = line.replaceAll("^Relay of 3:", "3x3 Relay of 3"); //3x3 Relay of 3
			line = line.replaceAll("^Relay of 3 (\\d)+", "3x3 Relay of 3"); //3x3 Relay of 3
			line = line.replaceAll("^3x3 relay of 3", "3x3 Relay of 3"); //3x3 Relay of 3
			line = line.replaceAll("^3x3 Relay:", "3x3 Relay of 3"); //3x3 Relay of 3
			line = line.replaceAll("^3x3 Relay (\\d)+", "3x3 Relay of 3"); //3x3 Relay of 3
			line = line.replaceAll("^Mega:", "Megaminx"); //Megaminx
			line = line.replaceAll("^f2l", "F2L"); //F2L
			line = line.replaceAll("^F2l", "F2L"); //F2L
			line = line.replaceAll("^4x4OH", "4x4 OH"); //4x4 OH
			
			
			line = line.replaceAll("^2-gen", "2GEN"); //2GEN 
			line = line.replaceAll("^2-GEN", "2GEN");
			line = line.replaceAll("^2 Gen", "2GEN");
			line = line.replaceAll("^2Gen", "2GEN");
			line = line.replaceAll("^2gen", "2GEN");
			line = line.replaceAll("^2 gen", "2GEN");
			
			line = line.replaceAll("^L6E", "LSE");
			
			line = line.replaceAll("^OLL[0-9]+", "OLL"); // OLL
			line = line.replaceAll("^Oll", "OLL");
			line = line.replaceAll("^OLL\\s[0-9]+", "OLL");
			line = line.replaceAll("oll", "OLL");
			
			line = line.replaceAll("->", "=");
			line = line.replaceAll("([0-9])min([0-9])", "$1:$2.00");       // 2min27 -> 2:27
			line = line.replaceAll("$(\\d+)\\x{2E}(\\d{1})\\s*", "$1.$20"); //21.1 -> 21.10
			line = line.replaceAll("^([0-9])X([0-9])", "$1x$2"); // 4X4 -> 4x4
			
			for (int b = 0; b < events.size(); b++) {
				String event = events.get(b).getText().trim();
				line = line.replaceAll("^(" + event + ")\\s+([0-9])", "$1: $2");
				
				if(line.startsWith(event + ":") && !event.isEmpty()) 
					if(line.indexOf("=") >= 0 && line.indexOf("=") < line.length() - 2) {
						String before = line.substring(line.indexOf(event + ":") + 1, line.indexOf("=")).trim();
						String after = line.substring(line.indexOf("=") + 1).trim();
						if(before.length() > after.length()) {
							String newLine = event + ":" + after;
							errorOut.setText(errorOut.getText() + "[Warning]Fixed suspected equal sign error on line " + (a + 1) + ".\n");
							line = newLine;
						}
					}
			}
			s += line + "\n";
		}
		
		input.setText(s);
		return s;
	}
	
	/* Warn of typos in username/averages. Going to maybe add a misspelled event checker here*/
	public boolean testComment(String comment) throws BadLocationException { //returns true if there are errors
		String[] lines = comment.split("\n");
		boolean bad = false;
		input.requestFocus();
		input.setSelectionColor(ERROR_COLOR);
		String line = "";
		for(int a = 0; a < lines.length; a++) {
			line = lines[a].trim();
			if(a == 0 && line.contains(" ")) {
				input.setCaretPosition(0);
				input.moveCaretPosition(input.getCaretPosition() + line.length());
				errorOut.setText(errorOut.getText() + "[Error]Bad user: "  + line + " on line " + (a + 1) + "\n");
				compOut.setText("User not added due to errors.");
				bad = true;
			}
			//if(bad)
				//break;
			for(int b = 0; b < events.size(); b++) {
				String cEvent = events.get(b).getText();
				if(line.startsWith(cEvent + ":") && !cEvent.isEmpty()) {
					try {
						if(!line.substring(cEvent.length() + 1, line.indexOf(".") + 3).contains(":")) {
							Double.parseDouble(line.substring(cEvent.length() + 1, line.indexOf(".") + 3).trim());
						}
						else {
							String time = line.substring(cEvent.length() + 1).trim();
							int mins = Integer.parseInt(time.substring(0, time.indexOf(":")));
							int secs = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.indexOf(".")));
							int dec = Integer.parseInt(time.substring(time.indexOf(".") + 1, time.indexOf(".") + 3));
						}
					} catch(Exception e) {
						input.setCaretPosition(input.getText().indexOf(cEvent + ":"));
						input.moveCaretPosition(input.getCaretPosition() + cEvent.length() + 1);
						errorOut.setText(errorOut.getText() + "[Error]Bad average for " + cEvent + " on line " + (a + 1) + "\n");
						compOut.setText("User not added due to errors.");
						bad = true;
					}
					//if(bad)
						//break;
				}
			}
		}
		if(bad)
			errorOut.setText(errorOut.getText() + "Last error highlighted\n");
		else if(!errorOut.getText().contains("Fixed"))
			errorOut.setText("No detected errors.\n");
		else 
			errorOut.setText(errorOut.getText() + "All detected errors corrected.\n");
		return bad;
	}	
}

/*To do:
* Fix the > 1:00.00 decimal bug
*/