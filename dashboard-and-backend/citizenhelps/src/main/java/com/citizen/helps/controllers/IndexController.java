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
import sun.reflect.generics.tree.ArrayTypeSignature;

import static j2html.TagCreator.*;

public class IndexController extends VaquitaController {
    @Override
    public VaquitaHTTPResponse handleGET(VaquitaHTTPJustRequest vaquitaHTTPJustRequest, VaquitaApp vaquitaApp) throws Exception {

        ContainerTag page = html(
                head(
                        title("CityHelps"),
                        meta().withName("viewport").withContent("width=device-width, initial-scale=1, shrink-to-fit=no"),
                        link().withRel("stylesheet").withHref("/css/main.css"),
                        link().withRel("stylesheet").withHref("https://unpkg.com/leaflet@1.5.1/dist/leaflet.css"),
                        script().withSrc("https://unpkg.com/leaflet@1.5.1/dist/leaflet.js"),
                        link().withRel("stylesheet").withHref("https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css")
                ),
                body(
                        nav(
                                span("CityHelps").withClasses("h1 navbar-brand"),
                                p("Max Musterman")
                        ).withClasses("navbar","navbar-light","bg-light"),
                        main(attrs("#main.content"),
                                div(
                                        div(
                                                h2("Alerts").withClasses("text-center"),
                                                hr(),
                                                div(

                                                ).withId("alerts_container")
                                        ).withClasses("col-md-3 bg-light").withStyle(""),
                                        div(
                                            div().withId("mapid")
                                        ).withClasses("col-md-6 p-0 m-0"),
                                        div(makeDetailView()).withClasses("col-md-3 bg-light")
                                ).withClasses("row")
                        ).withClasses("container-fluid"),
                        script().withSrc("/js/myleaflet.js")
                )
        );

        return new VaquitaHTMLResponse(200, page.render());
    }

    private static ContainerTag makeAlertCard(String title, int minutesAgo){
        return div(
                div(
                    h5(title).withClasses("card-title").withStyle("color:red;"),

                    div(p("accident alert"),
                            p("3 Beteiligte")
                    ).withClasses("card-text")
                ).withClasses("card-body"),
                div(
                    div(
                        div(
                            span(minutesAgo+" minutes ago")
                        ).withClasses("col-md-6"),
                        div(
                            button("view").withClasses("btn btn-block btn-sm btn-outline-primary")
                        ).withClasses("col-md-6")
                    ).withClasses("row align-items-center justify-content-center")
                ).withClasses("card-footer","text-muted")
        ).withClasses("card","m-2");
    }

    @Override
    public VaquitaHTTPResponse handlePOST(VaquitaHTTPEntityEnclosingRequest vaquitaHTTPEntityEnclosingRequest, VaquitaApp vaquitaApp) throws Exception {
        return null;
    }

    private static ContainerTag makeDetailView() {
        ContainerTag details = div(
                div(
                        h2("Details")
                ).withClasses("text-center"),
                hr(),
                h5(
                        "~"
                ).withId("description"),
                div(
                        p("~").withId("timeagostring"),
                        p("~").withId("timestamp")
                ).withClasses("text-muted"),
                div(

                    img().withSrc("https://via.placeholder.com/320")
                            .withStyle("width:100%")
                            .withAlt("Image of the accident").withId("image")

                ),
                div().withClasses("m-3"),

                div(
                        button("Wagen losschicken").withClasses("btn btn-block btn-sm btn-outline-primary")
                )

        ).withStyle("width:100%");
        return details;
    }

}
