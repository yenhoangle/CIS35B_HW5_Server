package server;

import exception.AutoException;
import model.Automotive;

public interface AutoServer {
    public void addAuto(Automotive auto) throws AutoException;
    public void getAuto(String autoname);
}
