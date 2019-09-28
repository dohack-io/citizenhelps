<template>
    <v-app>
        <v-container style="overflow-wrap: anywhere; ">
            <h1>{{(logged_in?('Hallo '+me.name):view_data.title)}}</h1>
            <v-card v-if="false">
                <v-card-title>Dev Area</v-card-title>
                <v-card-text>
                    <h3>Dev Area</h3>
                    card_before:{{card_before}}
                    <br/>
                    visited:{{visited}}
                    <br/>
                    button:{{button}} <br/>
                    logged_in:{{logged_in}}
                    <br/>
                    {{(button in visited)}}
                </v-card-text>

            </v-card>
            <v-btn @click="button='my_account'" v-if="logged_in">
                mein konto
            </v-btn>
            <v-btn @click="logged_in=!logged_in" v-if="!logged_in">{{(logged_in?'logout':'login')}}</v-btn>
            <v-btn @click="button=card_before [card_before.length-2]">zurück</v-btn>
            <div v-for="(card, idx) in view_data.cards" :key=idx>

                <v-card>
                    <v-card-title style="overflow-wrap: anywhere;">{{card.subtitle}}</v-card-title>
                    <v-card v-for="(text1, idx1) in card.texts" :key="idx1">
                        <v-card-text>
                            <v-row justify="center">
                                <v-col v-if="text1.text"> {{text1.text}}
                                </v-col>
                                <v-col cols="2" v-if="text1.img">
                                    <v-img :src="text1.img" contain width="40px"></v-img>
                                </v-col>
                                <v-col cols="4" v-if="text1.btn">
                                    <v-badge overlap>
                                        <template v-slot:badge>
                                            <div v-if="false"
                                                 style="color: #b94810;font-size: 16px;"> !
                                            </div>
                                        </template>
                                        <v-btn @click="button=text1.at" :disabled="!text1.at">
                                            {{text1.btn}}
                                        </v-btn>
                                    </v-badge>

                                </v-col>
                            </v-row>
                            <v-row v-if="text1.big_img">
                                <v-col>
                                    <v-img :src="text1.big_img" contain height="100%"></v-img>
                                </v-col>
                            </v-row>
                            <v-row v-if="text1.map_view">
                                <v-col>
                                    <iframe :src="text1.map_view"
                                            width="400" height="300" frameborder="0" style="border:0;"
                                            allowfullscreen=""></iframe>
                                </v-col>
                            </v-row>
                        </v-card-text>

                    </v-card>
                </v-card>
            </div>

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
    components: {

    },
    data: () => ({
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

    