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
      pizza_coupon: {
        title: "Hallo Mieter!",
        cards: [{
          subtitle: "Pizza in der Nähe mit 15% Rabatt",
          texts: [
            {
              text: "Rabatt Code: PIZZAFÜRMIETER",
              // btn: "Click",
              img: "pizza.png",
              map_view: "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5028.365765168572!2d6.942892934643382!3d50.93883080425342!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47bf2507d2ab6415%3A0xe883fab399a3b7df!2sNeoNeo!5e0!3m2!1sen!2sde!4v1569145015261!5m2!1sen!2sde"
            },

          ]
        }
        ]
      },
      my_account: {
        title: "Hallo Mieter!",
        cards: [
          {
            subtitle: "Sofort-WiFi",
            texts: [
              {
                text: "Upgrade zu High-Speed",
                btn: "buchen",
                at: "upgrade_wifi",
                // img: "KIT.png"
              },
              {
                text: "Schnell Internetvertrag abschließen 📡",
                btn: "buchen",
                at: "buchen",
                img: "check24.gif"
              },
              {
                text: "Schnell Stromvertrag abschließen 🔋☀️☘️",
                btn: "buchen",
                // at: "upgrade_wifi",
                img: "check24.gif"
              },


            ]
          },
          {
            subtitle: "Miet-Konto",
            texts: [
              {
                text: "Heizung 🌡.️ Werte von 15.08.2019",
                btn: "info",
                at: "upgrade_wifi",
                img: "techem_logo.jpg"
              },
              {
                text: "Miete-Zahlungen. Daten von 15.08.2019",
                btn: "info",
                // at: "info",
                // img: "check24.gif"
              },
              {
                text: "Strom. Stromvertrag abschließen 🔋☀️☘️",
                btn: "buchen",
                at: "upgrade_wifi",
                img: "check24.gif"
              },


            ]
          },
          {
            subtitle: "Besser-Leben Dienste",
            texts: [
              {
                text: "Wohnungsreinigung bei ISD",
                btn: "buchen",
                at: "cleaning",
                img: "isd_logo_small.jpg"
              },
              {
                text: "Wohnung von Profis streichen lassen 🎨",
                btn: "buchen",
                at: "upgrade_wifi",
                img: "isd_logo_small.jpg"
              },
              {
                text: "Paketenempfang",
                btn: "buchen",
                at: "upgrade_wifi",
                // img: "isd_logo_small.jpg"
              },
              {
                text: "Küche in 3D aufbauen und bestellen",
                btn: "plannen",
                at: "ikea_planner",
                img: "ikea_logo.png"
              },


            ]
          },
        ]
      },
      upgrade_wifi: {
        title: "Hallo Mieter!",
        cards: [
          {
            subtitle: "Dein Sofort-WiFi ist nun High-Speed",
            texts: [
              {
                // at: "upgrade_wifi",
                img: "meinhotspot.jpg",
                text: 'Service geliefert bei Meinhotspot GmbH'
              }

            ]
          },
        ]
      },
      cleaning: {
        title: "Hallo Mieter!",
        cards: [
          {
            subtitle: "Einfach ISD Ihre Wohnung schön sauber machen lassen.",
            texts: [
              {
                text: '1x Wochentlich',
                btn: 'buchen',

                at: "cleaning_booked",
                img: "isd_logo_small.jpg"
              },
              {
                text: '2x Wochentlich',
                btn: 'buchen',
                at: "cleaning_booked",
                img: "isd_logo_small.jpg"
              },
              {
                text: '1x Monatlich',
                btn: 'buchen',
                at: "cleaning_booked",
                img: "isd_logo_small.jpg"
              },

            ]
          },
        ]
      },
      cleaning_booked: {
        title: "Hallo Mieter!",
        cards: [
          {
            subtitle: "Ihre Reinigung wurde gebucht!",
            texts: [
              {
                text: 'Nächster Termin am 31.09.2019',
                btn: 'info',

                at: "",
                img: "isd_logo_small.jpg"
              },
              {
                btn: 'kontakt',
                at: ""
              },


            ]
          },
        ]
      },
      info_defect: {
        title: "Hallo Mieter!",
        cards: [
          {
            subtitle: "Birne defekt - repariert!",
            texts: [
              {
                text: 'Gemelder von Zybulski am 10.09.2019',
                // btn: 'buchen',

                // at: "cleaning_booked",
                // img: "KIT.png"
              },
              {
                text: 'Beauftragt von Mieter GmbH am 18.09.2019',
                // btn: 'buchen',

                // at: "cleaning_booked",
                // img: "KIT.png"
              },
              {
                text: 'Repariert von ISD am 19.09.2019',
                img: "isd_logo_small.jpg"
              },
              {
                btn: 'Feedback geben',
                at: "feedback"
              },


            ]
          },
        ]
      },
      ikea_planner: {
        title: "Hallo Mieter!",
        cards: [
          {
            subtitle: "Planne DEIN Küche in 3D",
            texts: [
              {
                text: 'Die Massen sind von uns bereits angegeben',
                big_img: 'ikea_planer.JPG'
              },


            ]
          },
        ]
      },


    }),
    methods: {
      get_coupon: function () {

      },
    },
    watch: {
      button() {
        //Control moving back prinziple
        if (this.button === this.card_before [this.card_before.length - 2]) {
          this.card_before.pop()
        } else {
          console.log(this.card_before.length);
          console.log(this.card_before);
          this.card_before.push(this.button);
        }
        this.visited.push(this.button);
        //Andere Process
        if (this.button === 'get_pizza') {
          console.log('pizza_coupon', this.pizza_coupon);
          this.view_data = this.pizza_coupon;
          console.log('view_data', this.view_data);
        } else if (this.button === 'home') {
          this.view_data = this.start_card;
        } else if (this.button === 'my_account') {
          this.view_data = this.my_account;
        } else if (this.button === 'upgrade_wifi') {
          this.view_data = this.upgrade_wifi;
        } else if (this.button === 'cleaning') {
          this.view_data = this.cleaning;
        } else if (this.button === 'cleaning_booked') {
          this.view_data = this.cleaning_booked;
        } else if (this.button === 'info_defect') {
          this.view_data = this.info_defect;
        } else if (this.button === 'ikea_planner') {
          this.view_data = this.ikea_planner;
        }
        // this.button = '';
      }
    },
    computed: {},
    created() {
      this.view_data = this.start_card;
      this.card_before.push(this.button);
    }
  };
</script>

    