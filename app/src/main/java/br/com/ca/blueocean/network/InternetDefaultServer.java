package br.com.ca.blueocean.network;

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

    //public static String defaultServer = "192.168.0.6:8080"; // local
    public static String defaultServer = "177.71.205.59:8080"; // remote Hive Server

}
