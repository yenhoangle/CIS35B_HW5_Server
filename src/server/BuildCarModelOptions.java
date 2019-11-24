package server;
import adapter.*;
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

    public String processRequest(Properties prop) {
        String toClient = null;
        Automotive auto;

        if (state == REQUEST_BUILD_AUTO) {
            //add code to buildauto
            //auto = buildAuto(props);
            toClient = "Automobile object successfully added to database\n"
                    + "Press any key to return to main menu";
        }
        else if (state == REQUEST_CONFIGURE_AUTO) {
            //add code for configureauto
        }
        else {

        }

        this.state = WAITING;

        return toClient;
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
