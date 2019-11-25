package server;

import exception.AutoException;
import model.Automotive;
import java.util.Properties;

public interface AutoServer {
    public void addAuto(Properties props) throws AutoException;
    public Automotive getAuto(Properties props);
}
