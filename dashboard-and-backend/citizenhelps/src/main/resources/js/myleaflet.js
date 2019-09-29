var mymap = L.map('mapid').setView([51.50432, 7.527], 13);



L.tileLayer( 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    subdomains: ['a','b','c']
}).addTo( mymap );
var myicon = L.icon({
    iconUrl: 'https://image.flaticon.com/icons/svg/564/564619.svg',
    iconSize: [38, 95],
    iconAnchor: [0, -175],
    popupAnchor: [0, 0]
});
//var marker = L.marker([51.50439, 7.52767],{icon:myicon}).addTo(mymap);

var detail_description = document.getElementById("description");
var detail_timeagostring  = document.getElementById("timeagostring");

var detail_timestamp  = document.getElementById("timestamp");

var detail_img  = document.getElementById("image");

var alerts_container = document.getElementById("alerts_container");


function createAlertItem(item){
    var res = document.createElement("div");
    res.className="card m-2";

    var footer  = document.createElement("div");
    footer.className="card-footer";

    var mybtn = document.createElement("button");
    mybtn.className="btn btn-sm btn-block btn-outline-primary";
    mybtn.innerHTML="view";
    mybtn.addEventListener("click",function(){
        detail_description.innerHTML=item.title;
        detail_timeagostring.innerHTML =item.timeagostring;
        detail_timestamp.innerHTML  ="Timestamp: "+item.timestamp+"";
        detail_img.src=item.imgsrc;
        mymap.setView([item.latitude, item.longitude], 14);
    });

    var footerrow = document.createElement("div");
    footerrow.className="row";

    var col1=document.createElement("div");
    col1.className="col-md-6 text-muted";
    col1.innerHTML=item.timeagostring;

    var col2=document.createElement("div");
        col2.className="col-md-6";
        col2.appendChild(mybtn);

    footerrow.appendChild(col1);
    footerrow.appendChild(col2);

    footer.appendChild(footerrow);

    //item.timestamp

    var body = document.createElement("div");
    body.className="card-body";
    body.innerHTML=item.title;

    res.appendChild(body);
    res.appendChild(footer);

    L.marker([item.latitude, item.longitude],{icon:myicon}).addTo(mymap);

    return res;
}

function pollServerForAlerts(){
    console.log("poll server for new alerts");



    fetch("/api/reports")
        .then(data=>data.json())
        .then(data=>{
            //console.log(data);
            var alerts;
            alerts=data;

            alerts_container.innerHTML="";

            for(var i=0;i<alerts.length;i++){
                var item = createAlertItem(alerts[i]);
                alerts_container.appendChild(item);
            }

            //wait with polling until request completes
            setTimeout(pollServerForAlerts,2000);
        }
    );




}

setTimeout(pollServerForAlerts,1000);