<template>
    <v-app>

        <v-container>
            <v-row v-if="loading" align="center" justify="center" style="height:100%;">
                <v-col style="height: 200px;text-align: center">
                    <v-progress-circular
                            :size="70"
                            :width="7"
                            color="blue"
                            indeterminate
                    ></v-progress-circular>
                </v-col>
            </v-row>
            <v-row v-if="stage==='start'"
                   align="center"
                   v-for="(apps, category) in icons" :key="category"
            >
                <v-card class="ma-1" >
                    {{category}}
                    <v-row >
                        <v-card class="ma-1" v-for="(val, key) in apps" :key="key">
                            <v-img contain height="40" width="40" :src="val"/>
                        </v-card>
                    </v-row>
                </v-card>


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
      Help, Report

    },
    data: () => ({
      stage: 'start',
      loading: false,
      icons: {
        "Car Rental": [
          "apps/app1.png",
          "apps/audio_guide.png",
          "apps/booking.jpg",
          "apps/expedia.png",
          "apps/radl_karte.png",
          "apps/salzburg_opnv.png",
          "apps/trip_advisor.png",
          "apps/tripcase.png",
          "apps/uber.jpg",
          "apps/sixt.png",
          "apps/hertz.jpg",
          "apps/obb_rail_drive.jpg",
          "apps/free_now.png",
          "apps/drive_now.jpg",
          "apps/car2go.png",
          "apps/yelp.jpg",
        ],
        "Western Apps": [
          "apps/google_maps.jpg",
          "apps/google_translator.png",
        ]
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

    