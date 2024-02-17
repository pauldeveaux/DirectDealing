package eu.telecomnancy.directdealing.utils;

public class Config {

    private static Config instance;

    public int initial_florains = 50;

    private Config(){

    }

    public static Config getInstance(){
        if(instance == null)
            instance = new Config();
        return instance;
    }
}
