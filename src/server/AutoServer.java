package server;

import exception.AutoException;
import model.Automotive;
import java.util.Properties;

public interface AutoServer {
    public Automotive getAuto(Properties props);
    public void listAutos();
    public void addAuto(Object input);
}
