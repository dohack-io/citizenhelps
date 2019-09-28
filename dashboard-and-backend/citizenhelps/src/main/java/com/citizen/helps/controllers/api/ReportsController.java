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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
;import static com.citizen.helps.generated.tables.Incidents.INCIDENTS;

public class ReportsController extends VaquitaController {

    public static ObjectMapper mapper=new ObjectMapper();

    @Override
    public VaquitaHTTPResponse handleGET(VaquitaHTTPJustRequest vaquitaHTTPJustRequest, VaquitaApp vaquitaApp) throws Exception {
        String host = vaquitaHTTPJustRequest.getHost();
        ArrayNode nodes = fetchAlerts(host);

        //System.out.println(nodes);
        //System.out.println(nodes.toString().length());

        return new VaquitaJSONResponse(200,nodes);
    }

    public static String clean(String str){
        return str.replaceAll("ä","ae").replaceAll("ö","oe").replaceAll("ü","ue");
    }
    public static final String userName = "27WJWpOHnj";
    public static final String password = "7E7Fllxl56";
    public static final String url = "jdbc:mysql://remotemysql.com:3306/27WJWpOHnj";

    public static ArrayNode fetchAlerts(String host)throws Exception{

        ArrayNode arr = mapper.createArrayNode();

        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        Connection conn = DriverManager.getConnection(url, userName, password);
        DSLContext ctx = DSL.using(conn,SQLDialect.MYSQL);
        Result<Record> incidents = ctx.select(INCIDENTS.asterisk()).from(INCIDENTS).fetch();

            for(Record r  : incidents){
                try {
                    ObjectNode obj = mapper.createObjectNode();
                    obj.put("title", clean(r.get(INCIDENTS.BESCHREIBUNG) + ""));
                    obj.put("timestamp",clean(r.get(INCIDENTS.ZEITSTEMPEL).toString()));
                    obj.put("minutesago", 5 + "");
                    obj.put("imgsrc","http://"+host+":3002/?id="+r.get(INCIDENTS.ID));
                    arr.add(obj);
                    System.out.println(r.get(INCIDENTS.BESCHREIBUNG));
                }catch (Exception e){
                    //pass
                }

            }
        conn.close();

        return arr;
    }

    @Override
    public VaquitaHTTPResponse handlePOST(VaquitaHTTPEntityEnclosingRequest vaquitaHTTPEntityEnclosingRequest, VaquitaApp vaquitaApp) throws Exception {

        JsonNode new_record = vaquitaHTTPEntityEnclosingRequest.getJsonFromEntity();

        //TODO: handle request

        return new VaquitaTextResponse(200,"ok");
    }
}
