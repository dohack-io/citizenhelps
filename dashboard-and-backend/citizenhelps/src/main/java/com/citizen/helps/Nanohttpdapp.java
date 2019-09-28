package com.citizen.helps;
import fi.iki.elonen.NanoHTTPD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class Nanohttpdapp extends NanoHTTPD {

    public Nanohttpdapp(int port) {
        super(port);
        System.out.println("nanohttpd running");
    }

    @Override
    public NanoHTTPD.Response serve(IHTTPSession session){

        File file = new File("img.jpg");
        try {
            FileInputStream fis = new FileInputStream(file);
            //return new Response(Response.Status.OK,"image/png",fis,file.length());
            return newFixedLengthResponse(Response.Status.OK, "image/jpg", fis, file.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFixedLengthResponse("error");
    }
}
