<template>
    <v-app>
        <v-container style="max-width: 300px">
            <v-row justify="stretch">

                <v-row justify="center">
                    <v-card>
                        <v-tabs
                                v-model="tab"
                                background-color="rgb(244,58,0)"
                                dark
                        >
                            <v-menu bottom right>
                                <template v-slot:activator="{ on }">
                                    <v-btn
                                            dark
                                            icon
                                            v-on="on"
                                    >
                                        <v-icon>mdi-dots-vertical</v-icon>
                                    </v-btn>
                                </template>
                                <v-card>
                                    <v-card-title>Welcome to TIROLERHOF</v-card-title>
                                    <v-list>
                                        <v-list-item
                                                v-for="(item, i) in menus"
                                                :key="i"
                                                @click="view='invoices'"
                                        >
                                            <v-list-item-title>
                                                {{ item[0] }}
                                                <span style="color: #6d6f74">{{item[1]}}</span>
                                            </v-list-item-title>
                                        </v-list-item>
                                    </v-list>
                                </v-card>

                            </v-menu>
                            <v-tabs-slider></v-tabs-slider>

                            <v-tab v-for="(apps_group,key) in icons" @click="view='apps'" :href="'#'+key" :key="key">
                                {{key}}
                                <!--                            <v-icon>mdi-phone</v-icon>-->
                            </v-tab>

                        </v-tabs>

                        <v-tabs-items v-model="tab" v-if="view==='apps'">
                            <v-tab-item
                                    v-for="(apps_group,key) in icons"
                                    :key="key"
                                    :value="key"
                            >
                                <v-col cols="12" v-for="(apps, category) in apps_group" :key="category">
                                    <v-card class="px-1" flat>
                                        <h3 style="text-align: center">
                                            {{category}}
                                        </h3>
                                        <v-row justify="center">
                                            <v-card class="ma-1" v-for="(val, key) in apps" :key="key">
                                                <v-img contain height="50" width="50" :src="val"/>
                                            </v-card>
                                        </v-row>
                                    </v-card>
                                </v-col>
                            </v-tab-item>

                        </v-tabs-items>
                        <Invoices v-if="view==='invoices'"/>
                        <Adds/>

                    </v-card>

                </v-row>
                <v-row>
                </v-row>

            </v-row>

            <!--            <Help @close="stage='start'" v-if="stage==='help'"></Help>-->
            <!--            <Report @close="stage='start'" v-if="stage==='report'"></Report>-->

        </v-container>

    </v-app>

</template>

<script>
  import Vue from "vue";
  import Help from "./help.vue";
  import Report from "./report.vue";
  import Invoices from "./invoices.vue";
  import Adds from "./adds.vue";
  //import {LMap,LTileLayer,LControl} from "./../../node_modules/vue2-leaflet"

  var api_host = 'https://ohsrb65n38.execute-api.eu-central-1.amazonaws.com/proxy/'

  ;

  function sleep(s) {
    return new Promise(resolve => setTimeout(resolve, s * 1000));
  }

  // var api_host = "http://localhost:5000/";
  // if (!location.hostname.includes("127.0.0.1") && !location.hostname.includes("localhost")) {
  // api_host = "http://localhost:5000";
  // }

  // import {icon} from "leaflet";
  import axios from "axios";

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
      Help, Report, Invoices, Adds

    },
    data: () => ({
      tab: null,
      view: 'apps',
      stage: 'start',
      loading: false,
      icons: {
        "Enjoy": {
          "Hotel Services": [
            "apps/spa.png",
            "apps/bar.png",
            "apps/chat_bot.png",
            "apps/room_service.png",
          ],
          "Digital Guides": [
            "apps/audio_guide.png",
            "apps/app1.png",
            "apps/ulmon.png",
          ],
          "City Discovery": [
            "apps/trip_advisor.png",
            "apps/tripcase.png",
            "apps/yelp.jpg",
            "apps/expedia.png",
          ],
        },
        "Travel": {
          "Green Travel": [
            "apps/radl_karte.png",
            "apps/salzburg_opnv.png",
            "apps/salzburg_bahnen.png",
          ],
          "Car Rental": [
            // "apps/booking.jpg",
            "apps/sixt.png",
            "apps/hertz.jpg",
            "apps/obb_rail_drive.jpg",
            // "apps/drive_now.jpg",
            "apps/car2go.png",
          ],
          "Taxi Services": [
            "apps/uber.jpg",
            "apps/free_now.png",
          ],
        },
        "新": {
          "翻譯": [
            "apps/google_translator.png",
          ],
          "地圖": [
            "apps/google_maps.jpg",
          ],
          "購物": [
            "apps/amazon.jpg",
          ],
        }

      },
      first_buttons: [
        {
          color: 'red',
          text: 'Hilfe anfragen',
          style: {
            height: '60%',
          },
          icon: 'fas fa-phone-volume',
          target_stage: 'help'

        },
        // {
        //   color: 'green',
        //   text: 'Erste Hilfe',
        //   style: {
        //     height: '30%',
        //   },
        //   target_stage: 'help'
        // },
        {
          color: 'blue',
          icon: 'fas fa-file-invoice',
          text: 'Meldung erfassen',
          style: {
            height: '40%',
          },
          target_stage: 'report'
        },

      ],
      menus: [
        ['Your Phone Number:', '+4915773674378'],
        ['Your Credit Card Balance:', '150 EUR'],
        ['View Invoices']
      ],

      visited: [''],
      data1: 0,
      logged_in: false,
      card_before: ['home'],
      button: "home",
      view_data: {},

    }),
    methods: {

      get_coupon: function () {
      },
      view_invoices: function () {

      },
      switch_stage: async function (stage_name) {
        this.loading = true;
        this.stage = "";
        await sleep(0.5);
        this.loading = !this.loading;
        this.stage = stage_name;
      }
    },
    watch: {
      button() {

      },
      stage() {
        console.log(this.stage);
      }
    },
    computed: {},
    created() {
      this.view_data = this.start_card;
      this.card_before.push(this.button);
    }
  };
</script>

    