/*
 * Yen Le
 * 20123455
 *
 * ProxyAuto.java
 * Abstract class which implements all the interface functions for BuildAuto. Interface methods are implemented here so
 * that implementations are hidden from the external environment. ProxyAuto cannot be instantiated and thus is hidden.
 * */

package adapter;
import exception.AutoException;
import model.AutoTemplate;
import model.Automotive;
import util.FileIO;
import scale.*;

import java.util.Properties;

public abstract class ProxyAutomotive {
    private static AutoTemplate at1 = new AutoTemplate();
    private static Automotive a1;

    public ProxyAutomotive() {
        a1 = new Automotive();
    }

    public AutoTemplate getAutoTemplate() {
        return at1;
    }

    //implements CreateAuto interface methods via subclass BuildAuto------------------------
    public void buildAuto(String filename) throws AutoException {
        FileIO fileIO = new FileIO();
        Automotive toBuild = fileIO.buildAutoObject(filename);
        at1.addVehicle(toBuild.getName(), toBuild); //also add a1 to the hash map
    }

    //new methods for proj 5: overloaded buildAuto to handle build from prop obj/file
    public void buildAuto(String filename, int fileType) throws AutoException {
        FileIO fileIO = new FileIO();
        Automotive toBuild = fileIO.buildAutoObject(filename, fileType);
        at1.addVehicle(toBuild.getName(), toBuild); //also add a1 to the hash map
    }

    public void buildAuto(Properties props) throws AutoException {
        FileIO fileIO = new FileIO();
        Automotive toBuild = fileIO.readPropData(props);
        at1.addVehicle(toBuild.getName(), toBuild); //also add a1 to the hash map
    }


    public synchronized void printAuto(String modelName) {
        at1.getVehicle(modelName).print();
    }

    //prints names of autos in the LHS
    public synchronized void printAutos() {
        at1.printAllVehicles();
    }

    //implements AutoServer interface methods via subclass BuildAuto---------------------------
    public Automotive getAuto(Properties props) {
        String make = props.getProperty("Make");
        String model = props.getProperty("Model");
        String year = props.getProperty("Year");
        String key = make + " " + model + " " + year;
        return getAutoTemplate().getVehicle(key);
    }
    //borrowing implementation of CreateAuto's method
    public void addAuto(Properties props) throws AutoException {
        buildAuto(props);
    }

    //implements UpdateAuto interface methods via subclass BuildAuto---------------------------
    public synchronized void updateOptionSetName(String key, String opsetName, String newOpsetName) {
        at1.getVehicle(key).updateOpsetName(opsetName, newOpsetName);
    }

    public synchronized void updateOptionPrice(String key, String opsetName, String opName, float newPrice) {
        at1.getVehicle(key).updateOpPrice(opsetName, opName, newPrice);
    }

    //implements FixAuto interface method
    public void fix(int errno)  {
        //implementation already included in fileIO
    }

    //implements ConfigureAuto interface methods----------------------------------------
    public void selectChoices(String key) {
        Automotive selectAuto = at1.getVehicle(key);
        System.out.println("\nSelecting options for: " + selectAuto.getName());
        selectAuto.selectChoices();
        //update hashmap entry to contain selected options
        at1.updateVehicle(selectAuto.getName(), selectAuto);
    }

    public void printChoices(String key) {
        at1.getVehicle(key).printChoices();
    }
    public float calculatePrice(String key) {
        return at1.getVehicle(key).getTotalPrice();
    }

    //implements EditThread interface methods--------------------------------------------------
    public synchronized void updateOptionSetName(int threadNum, String key, String opsetName, String newOpsetName) {
        //start thread with a number
        EditOptions thread = new EditOptions(threadNum, key, opsetName, newOpsetName);
        //run
        thread.start();
    }

    public synchronized void updateOptionName(int threadNum, String key, String opsetName, String opName,
                                              String newOpName) {
        //start thread with a number
        EditOptions thread = new EditOptions(threadNum, key, opsetName, opName, newOpName);
        //run
        thread.start();
    }

    public synchronized void updateOptionPrice(int threadNum, String key, String opsetName, String opName,
                                               float newPrice) {
        //start thread with a number
        EditOptions thread = new EditOptions(threadNum, key, opsetName, opName, newPrice);
        //run
        thread.start();
    }
}
