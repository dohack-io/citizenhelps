package com.citizen.helps.controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.entity.ContentType;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.vanautrui.vaquitamvc.VaquitaApp;
import org.vanautrui.vaquitamvc.controller.VaquitaController;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPEntityEnclosingRequest;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPJustRequest;
import org.vanautrui.vaquitamvc.responses.VaquitaHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaJSONResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaTextResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
;import static com.citizen.helps.generated.tables.Incidents.INCIDENTS;

public class ReportsController extends VaquitaController {

    public static ObjectMapper mapper = new ObjectMapper();

    @Override
    public VaquitaHTTPResponse handleGET(VaquitaHTTPJustRequest vaquitaHTTPJustRequest, VaquitaApp vaquitaApp) throws Exception {
        String host = vaquitaHTTPJustRequest.getHost();
        ArrayNode nodes = fetchAlerts(host);

        //System.out.println(nodes);
        //System.out.println(nodes.toString().length());

        return new VaquitaJSONResponse(200, nodes);
    }

    public static String clean(String str) {
        return str.replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue");
    }

    public static final String userName = "27WJWpOHnj";
    public static final String password = "7E7Fllxl56";
    public static final String url = "jdbc:mysql://remotemysql.com:3306/27WJWpOHnj";

    public static ArrayNode fetchAlerts(String host) throws Exception {

        ArrayNode arr = mapper.createArrayNode();

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally


        try (Connection conn = DriverManager.getConnection(url, userName, password);) {
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> incidents = ctx.select(INCIDENTS.asterisk()).from(INCIDENTS).orderBy(INCIDENTS.ZEITSTEMPEL.desc()).fetch();

            for (Record r : incidents) {
                ObjectNode obj = mapper.createObjectNode();

                Date date = r.get(INCIDENTS.ZEITSTEMPEL);
                Duration elapsed = Duration.between(date.toInstant(), Instant.now());
                String timeagostring = "error";

                if (elapsed.toDays() > 0) {
                    timeagostring = elapsed.toDays() + " days ago";
                } else if (elapsed.toHours() > 0) {
                    timeagostring = elapsed.toHours() + " hours ago";
                } else if (elapsed.toMinutes() > 0) {
                    timeagostring = elapsed.toMinutes() + " minutes ago";
                } else {
                    timeagostring = elapsed.getSeconds() + " seconds ago";
                }

                obj.put("title", clean(r.get(INCIDENTS.BESCHREIBUNG) + ""));
                obj.put("timestamp", clean(r.get(INCIDENTS.ZEITSTEMPEL).toString()));
                obj.put("timeagostring", timeagostring);
                obj.put("imgsrc", "http://" + host + ":3002/?id=" + r.get(INCIDENTS.ID));

                obj.put("longitude", r.get(INCIDENTS.LON));
                obj.put("latitude", r.get(INCIDENTS.LAT));

                arr.add(obj);
                System.out.println(r.get(INCIDENTS.BESCHREIBUNG));
            }
            conn.close();
        }


        return arr;
    }

    @Override
    public VaquitaHTTPResponse handlePOST(VaquitaHTTPEntityEnclosingRequest vaquitaHTTPEntityEnclosingRequest, VaquitaApp vaquitaApp) throws Exception {

        JsonNode r = vaquitaHTTPEntityEnclosingRequest.getJsonFromEntity();

        //TODO: handle request
        try(Connection conn = DriverManager.getConnection(url, userName, password)){
            DSLContext ctx = DSL.using(conn,SQLDialect.MYSQL);

            String base_64_img_str=r.get("bild").asText();

            String base64Image = base_64_img_str.split(",")[1];
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);


            byte[] img_bytes=imageBytes;

            ctx.insertInto(INCIDENTS).columns(
                    INCIDENTS.ART,INCIDENTS.BESCHREIBUNG,
                    INCIDENTS.BILD,INCIDENTS.LAT,INCIDENTS.LON,
                    INCIDENTS.WEITERE_INFOS,INCIDENTS.ZEITSTEMPEL)
                    .values(
                            r.get("art").asText(),r.get("beschreibung").asText(),
                            img_bytes,r.get("lat").asDouble(),r.get("lon").asDouble(),
                            "",new Timestamp(r.get("zeitstempel").asInt())
                    ).execute();
        }catch (Exception e){
            e.printStackTrace();
            return new VaquitaTextResponse(500,e.toString());
        }

        //String s = r.toString();
        //System.out.println(s);

        return new VaquitaTextResponse(200, "ok");
    }
}
