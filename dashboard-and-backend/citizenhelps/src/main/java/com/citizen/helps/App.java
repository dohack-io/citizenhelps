package com.citizen.helps;

import com.citizen.helps.controllers.IndexController;
import com.citizen.helps.controllers.api.ReportsController;
import fi.iki.elonen.NanoHTTPD;
import org.vanautrui.vaquitamvc.VaquitaApp;
import org.vanautrui.vaquitamvc.controller.VaquitaController;

import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        HashMap<String,VaquitaController> routes = new HashMap<>();

        routes.put("/",new IndexController());
        routes.put("/api/reports",new ReportsController());

        VaquitaApp app = new VaquitaApp(3001,routes);

        app.startServer();

        Nanohttpdapp nanohttpdapp = new Nanohttpdapp(3002);
        nanohttpdapp.start(NanoHTTPD.SOCKET_READ_TIMEOUT,false);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                nanohttpdapp.stop();
            }
        });
    }
}
