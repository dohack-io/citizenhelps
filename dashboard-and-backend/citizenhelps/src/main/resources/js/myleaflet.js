var mymap = L.map('mapid').setView([51.50432, 7.527], 13);



L.tileLayer( 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    subdomains: ['a','b','c']
}).addTo( mymap );
var myicon = L.icon({
    iconUrl: 'https://image.flaticon.com/icons/svg/564/564619.svg',
    iconSize: [38, 95],
    iconAnchor: [22, 94],
    popupAnchor: [-3, -76]
});
var marker = L.marker([51.50439, 7.52767],{icon:myicon}).addTo(mymap);

var detail_description = document.getElementById("description");

detail_description.innerHTML="hello frontend appp";