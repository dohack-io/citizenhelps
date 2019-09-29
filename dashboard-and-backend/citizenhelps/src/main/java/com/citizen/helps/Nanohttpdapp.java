package com.citizen.helps;
import com.citizen.helps.controllers.api.ReportsController;
import fi.iki.elonen.NanoHTTPD;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;

import static com.citizen.helps.controllers.api.ReportsController.userName;
import static com.citizen.helps.generated.tables.Incidents.INCIDENTS;

public class Nanohttpdapp extends NanoHTTPD {

    public Nanohttpdapp(int port) {
        super(port);
        System.out.println("nanohttpd running");
    }

    @Override
    public NanoHTTPD.Response serve(IHTTPSession session){
        try(Connection conn = DriverManager.getConnection(ReportsController.url, userName, ReportsController.password);) {
            System.out.println(session.getQueryParameterString());
            String[] params = session.getQueryParameterString().split("&");
            long id = Long.parseLong(params[0].split("=")[1]);
            //connect to the db to get the images

            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            Record incident = ctx.select(INCIDENTS.BILD).from(INCIDENTS).where(INCIDENTS.ID.eq(UInteger.valueOf(id))).fetchOne();

            byte[] imagebytespng = incident.get(INCIDENTS.BILD);

            InputStream is = new ByteArrayInputStream(imagebytespng);
            //File file = new File("img.jpg");
            //FileInputStream fis = new FileInputStream(file);
            //return new Response(Response.Status.OK,"image/png",fis,file.length());
            //return newFixedLengthResponse(Response.Status.OK, "image/jpg", fis, file.length());
            Response res =  newFixedLengthResponse(Response.Status.OK, "image/png", is, imagebytespng.length);
            res.addHeader("Access-Control-Allow-Origin", "*");
            return res;
        }catch (Exception ee){
            //
            ee.printStackTrace();
        }
        return newFixedLengthResponse("error ");
    }
}
