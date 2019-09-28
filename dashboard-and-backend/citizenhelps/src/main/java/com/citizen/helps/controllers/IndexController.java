package com.citizen.helps.controllers;

import j2html.tags.ContainerTag;
import j2html.*;
import org.vanautrui.vaquitamvc.VaquitaApp;
import org.vanautrui.vaquitamvc.controller.VaquitaController;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPEntityEnclosingRequest;
import org.vanautrui.vaquitamvc.requests.VaquitaHTTPJustRequest;
import org.vanautrui.vaquitamvc.responses.VaquitaHTMLResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaHTTPResponse;
import org.vanautrui.vaquitamvc.responses.VaquitaTextResponse;

import static j2html.TagCreator.*;

public class IndexController extends VaquitaController {
    @Override
    public VaquitaHTTPResponse handleGET(VaquitaHTTPJustRequest vaquitaHTTPJustRequest, VaquitaApp vaquitaApp) throws Exception {
        ContainerTag page = html(
                head(
                        title("CityHelps"),
                        link().withRel("stylesheet").withHref("/css/main.css"),
                        link().withRel("stylesheet").withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
                ),
                body(
                        nav(
                                span("CityHelps").withClasses("navbar-brand")
                        ).withClasses("navbar","navbar-light","bg-light"),
                        main(attrs("#main.content"),
                                div(
                                        div("notifications").withClasses("col-md-3"),
                                        div("map").withClasses("col-md-6"),
                                        div("details").withClasses("col-md-3")
                                ).withClasses("row")
                        )
                )
        );

        return new VaquitaHTMLResponse(200,page.render());
    }

    @Override
    public VaquitaHTTPResponse handlePOST(VaquitaHTTPEntityEnclosingRequest vaquitaHTTPEntityEnclosingRequest, VaquitaApp vaquitaApp) throws Exception {
        return null;
    }
}
