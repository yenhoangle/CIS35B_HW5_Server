package server;

import adapter.BuildAuto;
import model.Automotive;

import java.util.Properties;

public class BuildCarModelOptions implements AutoServer{
    private int request;
    private static boolean DEBUG = false;

    public String setRequest (int requestNum) {
        this.request = requestNum;
        switch (request) {
            case 1:
                return ("Enter filename for car: ");
            case 2:
                return ("Enter name of car to configure: ");
            default: {
                request = -1;
                return ("Invalid option.");
            }
        }
    }

    public int getRequest() {
        return request;
    }
    //TODO: FIX
    public void addAuto(Automotive auto) {
        BuildAuto bAuto = new BuildAuto();
        bAuto.getAutoTemplate().addVehicle(auto.getName(), auto);
    }

    //TODO: TO FIX
    public String processRequest(Properties clientProps) {
        String response = "";
        return response;
    }

    //TODO: TO FIX
    public String processRequest(int requestNum) {
        String response = "";
        return response;
    }
}
