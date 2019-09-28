var mymap = L.map('mapid').setView([51.50432, 7.527], 13);



L.tileLayer( 'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    subdomains: ['a','b','c']
}).addTo( mymap );

var detail_description = document.getElementById("description");

detail_description.innerHTML="hello frontend appp";