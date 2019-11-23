package model;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import model.*;

public class Automotive implements Serializable {
    //separate variables to hold make and model, make + model = full name
    private String make;
    private String model;
    private String year;
    private float baseprice;
    private ArrayList<OptionSet> optionSets;
    private ArrayList<Option> choices; //choices is an array list of options chosen

    //constructors
    public Automotive() {
        this("", "", "",0);
    }

    public Automotive(float baseprice) {
        this("","", "", baseprice);
    }

    public Automotive(String make, String model, String year, float baseprice ) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.baseprice = baseprice;
        optionSets = new ArrayList<OptionSet>();
        choices = new ArrayList<Option>();
    }

    //getters
    public synchronized String getName() {
        return(make + " " + model + " " + year);
    }
    public synchronized String getMake() {
        return make;
    }
    public synchronized String getModel() {
        return model;
    }
    public synchronized String getYear() {return year; }
    public synchronized float getBaseprice() {
        return baseprice;
    }

    public synchronized ArrayList<OptionSet> getOpsets() {
        return optionSets;
    }

    public synchronized ArrayList<Option> getChoices() {
        return choices;
    }

    public synchronized OptionSet getOpSet(int index) {
        return optionSets.get(index);
    }

    public synchronized String getOpSetName(int index) {
        return getOpSet(index).getName();
    }

        //get option chosen for a given option set
    public synchronized Option getOptionChoice(String opsetName) {
        int opsetIndex = findOpsetIndex(opsetName);
        return optionSets.get(opsetIndex).getOpChoice();
    }

    public synchronized String getChoiceName(String opsetName) {
        return getOptionChoice(opsetName).getName();
    }

    public synchronized float getChoicePrice(String opsetName) {
        return getOptionChoice(opsetName).getPrice();
    }

    //get opchoice, get opchoiceprice, set opchoice, get totalprice
    //setters
    public synchronized void setMake(String make) {
        this.make = make;
    }

    public synchronized void setModel(String model) {
        this.model = model;
    }

    public synchronized void setYear(String year) { this.year = year; }

    public synchronized void setBaseprice(float price) {
        this.baseprice = price;
    }

    public synchronized void setChoices(ArrayList<Option> choices) {
        this.choices = choices;
    }

    public synchronized int findOpsetIndex(String opsetName) {
        for (OptionSet opset : optionSets) {
            if (opset.getName().equals(opsetName)) {
                //use built in arraylist method to find index
                return optionSets.indexOf(opset);
            }
        }
        //not found
        return -1;
    }

    public synchronized OptionSet findOpset(String opsetName) {
        for (OptionSet opset : optionSets) {
            if (opset.getName().equals(opsetName)) {
                return opset;
            }
        }
        return null;
    }

    public synchronized Option findOption(String opsetName, String opName) {
        if (opsetName != null && opName != null) {
            OptionSet opset = findOpset(opsetName);
            return opset.findOption(opName);
        }
        return null;
    }

    //add methods
    public synchronized boolean addOpset(String name){
        if (name != null) {
            optionSets.add(new OptionSet(name));
            return true;
        }
        //do we have to do anything with choice here
        return false;
    }

    public synchronized boolean addOption(int opsetIndex, String opname, float opPrice) {
        if (opname != null && opname != null) {
            if (opsetIndex != -1) {
                return optionSets.get(opsetIndex).addOption(opname, opPrice);

            }
        }
        return false;
    }

    public synchronized void addOptionChoice(Option choice) {
        choices.add(choice);
    }

    //update methods
    public synchronized void updateOpsetName(String name, String newName) {
        if (name != null && newName != null) {
            OptionSet op = findOpset(name);
            if (op != null) {
                op.setName(newName);
            }
        }
    }

    public synchronized void updateOpset(String name, OptionSet newOpset) {
        if (name != null) {
            int opsetIndex = findOpsetIndex(name);
            if (opsetIndex != -1) {
                optionSets.set(opsetIndex, newOpset);
            }
        }
    }
    //update the entire option
    public synchronized void updateOp(String opsetName, String opname, String newOpname, float newPrice ) {
        if (opsetName != null && opname != null && newOpname!= null) {
            int opsetIndex = findOpsetIndex(opsetName);
            if (opsetIndex != -1) {
                optionSets.get(opsetIndex).updateOption(opname, newOpname, newPrice);
            }
        }
    }

    public synchronized void updateOpname (String opsetName, String opname, String newOpname) {
        if (opsetName != null && opname != null && newOpname!= null) {
            int opsetIndex = findOpsetIndex(opsetName);
            if (opsetIndex != -1) {
                optionSets.get(opsetIndex).updateOpName(opname, newOpname);
            }
        }
    }

    public synchronized void updateOpPrice (String opsetName, String opname, float newPrice) {
        if (opsetName != null && opname != null) {
            int opsetIndex = findOpsetIndex(opsetName);
            if (opsetIndex != -1) {
                optionSets.get(opsetIndex).updateOpPrice(opname, newPrice);
            }
        }
    }

    //delete methods
    public synchronized boolean deleteOpset(String name) {
        if (optionSets != null) {
            for(int i = 0; i < optionSets.size(); i++) {
                if (optionSets.get(i).getName().equals(name)) {
                    //use existing arraylist set method to set item to null
                    optionSets.set(i, null);
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean deleteOption(String opsetName, String opName) {
        if (optionSets != null) {
            for (int i = 0; i < optionSets.size(); i++) {
                if(optionSets.get(i).getName().equals(opsetName)) {
                    return optionSets.get(i).deleteOption(opName);
                }
            }
        }
        return false;
    }

    public synchronized void clearChoices() {
        this.choices.clear();
    }

    public synchronized void selectChoices() {
        Scanner scanner = new Scanner(System.in);
        //clear existing choices so user can pick again
        if (getChoices().size() > 0) {
            clearChoices();
        }
        for (int i = 0; i < getOpsets().size(); i++) {
            boolean done = false;
            while(!done) {
                String opsetName = getOpSetName(i);
                System.out.println("Please enter choice for " + opsetName);
                String opNameChosen = scanner.nextLine().trim();
                //search optionset for option choice with name to check validity
                Option chosen = findOption(opsetName, opNameChosen);
                if (chosen != null) {
                    //put that choice in choices arraylist
                    addOptionChoice(chosen);
                    done = true;
                }
                else {
                    System.out.println("Invalid choice");
                }
            }
        }
    }

    public synchronized float getTotalPrice() {
        float total = this.getBaseprice();
        for(int i = 0; i < choices.size(); i++) {
            total += choices.get(i).getPrice();
        }
        return total;
    }



    //print method for the Automotive object
    public synchronized void print() {
        System.out.printf("%s\nBase Price: $%.2f\n", getName(), baseprice);
        if (optionSets != null) {
            for(OptionSet opset : optionSets) {
                opset.print();
            }
        }
    }

    public synchronized void printChoices() {
        System.out.printf("%s with selected options:\n", getName());
        if (choices != null) {
            for (Option op : choices) {
                op.print();
            }
        }
    }
}
