package br.com.ca.asap.network;

/**
 * InternetDefaultServer
 *
 * Use this class to define the http server instance
 *
 * @author Rodrigo Carvalho
 */
public class InternetDefaultServer {

    public static String getDefaultServer() {
        return defaultServer;
    }

    //public static String defaultServer = "192.168.0.7:8080"; // local
    public static String defaultServer = "54.94.205.241:8080"; // remote Hive Server

}
