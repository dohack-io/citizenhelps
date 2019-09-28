package com.citizen.helps.controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.vanautrui.vaquitamvc.VaquitaApp;
import org.vanautrui.vaquitamvc.controller.VaquitaController;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPEntityEnclosingRequest;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPJustRequest;
import org.vanautrui.vaquitamvc.responses.VaquitaHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaJSONResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaTextResponse;

public class ReportsController extends VaquitaController {

    public static ObjectMapper mapper=new ObjectMapper();

    @Override
    public VaquitaHTTPResponse handleGET(VaquitaHTTPJustRequest vaquitaHTTPJustRequest, VaquitaApp vaquitaApp) throws Exception {


        ArrayNode node = mapper.createArrayNode();

        node.add(makeAlert("Motorcycle Accident",5));
        node.add(makeAlert("Train Station Vandalized",35));

        return new VaquitaJSONResponse(200,node);
    }

    public static ObjectNode makeAlert(String title, int minutesAgo){
        ObjectNode res = mapper.createObjectNode();

        res.put("title",title);
        res.put("minutesago",minutesAgo);

        return res;
    }

    @Override
    public VaquitaHTTPResponse handlePOST(VaquitaHTTPEntityEnclosingRequest vaquitaHTTPEntityEnclosingRequest, VaquitaApp vaquitaApp) throws Exception {

        JsonNode new_record = vaquitaHTTPEntityEnclosingRequest.getJsonFromEntity();

        //TODO: handle request

        return new VaquitaTextResponse(200,"ok");
    }
}
