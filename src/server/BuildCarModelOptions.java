package server;
import adapter.*;
import exception.AutoException;
import model.Automotive;

import java.util.Properties;

public class BuildCarModelOptions extends ProxyAutomotive {

    ////////// PROPERTIES //////////

    private static final int WAITING = 0;
    private static final int REQUEST_BUILD_AUTO = 1;
    private static final int REQUEST_CONFIGURE_AUTO = 2;

    private int state = WAITING;

    ////////// CONSTRUCTORS //////////

    public BuildCarModelOptions() {

    }

    ////////// INSTANCE METHODS //////////

    public Object processRequest(Properties props) throws AutoException {
        Object toClient = null;
        Automotive auto;
        try {

            if (state == REQUEST_BUILD_AUTO) {
                //build auto and add to database
                addAuto(props);
                toClient = "Automobile object successfully added to database\n"
                        + "Press any key to return to main menu";
            } else if (state == REQUEST_CONFIGURE_AUTO) {
                //add code for configureauto
                toClient = getAuto(props);
            } else {
                System.out.println("Cannot process request");
            }

            this.state = WAITING;
        } catch (AutoException ae) {

        }

        return toClient;
    }

    public int getRequest() {
        return state;
    }
    public String setRequest(int i) {
        String output = null;

        if (i == 1) {
            this.state = REQUEST_BUILD_AUTO;
            output = "Upload a file to create an Automotive";
        }
        else if (i == 2) {
            this.state = REQUEST_CONFIGURE_AUTO;
            output = "Select an Automotive from the following list to configure: \n";
            super.printAutos();
        }
        else {
            output = "Invalid request";
        }

        return output;
    }
}
