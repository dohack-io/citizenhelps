<template>
    <v-app>
        <v-container fluid>

            <v-row no-gutters class="pa-1" :style="button.style" v-for="(button,key) in first_buttons" :key="key" align="stretch">
                <v-col>
                    <v-btn :color="button.color"  style="height: 100%" block @click="button='my_account'">
                        {{button.text}}
                    </v-btn>
                </v-col>

            </v-row>


        </v-container>
    </v-app>

</template>

<script>
  import axios from "axios";
  import Vue from "vue";

  //import {LMap,LTileLayer,LControl} from "./../../node_modules/vue2-leaflet"

  var api_host = 'https://ohsrb65n38.execute-api.eu-central-1.amazonaws.com/proxy/'

  ;
  // var api_host = "http://localhost:5000/";
  // if (!location.hostname.includes("127.0.0.1") && !location.hostname.includes("localhost")) {
  // api_host = "http://localhost:5000";
  // }

  // import {icon} from "leaflet";

  axios.defaults.headers.post = {
    // 'Access-Control-Allow-Origin': "*",
    'Access-Control-Allow-Credentials': 'true',
    'Content-Type': 'application/json',
  };
  axios.defaults.method = 'post';
  var api_aindex = axios.create({
    url: api_host,
  });

  export default {
    components: {},
    data: () => ({
      first_buttons: [
        {
          color: 'red',
          text: 'Hilfe holen',
          style: {
            height: '50%',
          }

        },
        {
          color: 'green',
          text: 'Erste Hilfe',
          style: {
            height: '30%',
          }
        },
        {
          color: 'orange',
          text: 'Bericht erfassen',
          style: {
            height: '20%',
          }
        },

      ],
      me: {
        name: 'Herr Bogomolov'
      },
      visited: [''],
      data1: 0,
      logged_in: false,
      card_before: ['home'],
      button: "home",
      view_data: {},
      start_card: {
        title: "Hallo Mieter!",
        cards: [{
          subtitle: "Nachrichten",
          texts: [
            {
              text: "Willkommen unseren neunen Mitbewohner, Herr Bogomolov 😃❤️",
              img: "herrbogomolov.jpg",
              btn: "Sag Hi!",
              at: 'hi',
            },
            {
              text: "Nächste Reinigung diesen Freitag um 16:00 Uhr bei ISD",
              btn: "info",
              img: "isd_logo_small.jpg"
            },
            {
              text: "Birne defekt - repariert!",
              btn: "info",
              at: "info_defect",
              img: "isd_logo_small.jpg"
            },
          ]
        },
          {
            subtitle: "Werbung",
            texts: [
              {
                text: 'Neue Pizzeria "NeuNeu" 🍕',
                btn: "info",
                at: 'get_pizza',
                img: "pizza.png"
              },
              {
                text: "Cafe ToGo. Fair Trade. Columbia ☕",
                btn: "info",
                at: "ad",
                img: "starbucks.jpg"
              },
              {
                text: "Ich geh Gassie mit Ihrem Hund oder Katze 😉 🐶",
                btn: "Kontakt",
                at: "ad",
                img: "girl.jpg"
              },

            ]
          }
        ],

      },
    }),
    methods: {
      get_coupon: function () {

      },
    },
    watch: {
      button() {

      }
    },
    computed: {},
    created() {
      this.view_data = this.start_card;
      this.card_before.push(this.button);
    }
  };
</script>

    