<template>
    <v-app>

        <v-container fluid>
            <v-row v-if="stage!='start'">
                <v-btn @click="stage='start'" block>         Stage:   {{stage}}
</v-btn>
            </v-row>
            <v-row v-if="loading" align="center" justify="center" style="height:100%;">
                <v-col style="height: 200px;text-align: center">
                    <v-progress-circular
                            :size="70"
                            :width="7"
                            color="purple"
                            indeterminate
                    ></v-progress-circular>
                </v-col>
            </v-row>
            <v-row v-if="stage==='start'"
                   no-gutters class="pa-1" :style="button.style" v-for="(button,key) in first_buttons" :key="key"
                   align="stretch">
                <v-col>
                    <v-btn :color="button.color" x-large style="height: 100%;font-size: 35px" block
                           @click="switch_stage(button.target_stage)">
                        {{button.text}}
                    </v-btn>
                </v-col>

            </v-row>
            <Help @close="stage='start'" v-if="stage==='help'"></Help>
            <Report @close="stage='start'" v-if="stage==='report'"></Report>

        </v-container>

    </v-app>

</template>

<script>
  import axios from "axios";
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
      first_buttons: [
        {
          color: 'red',
          text: 'Hilfe anfragen',
          style: {
            height: '60%',
          },
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
          color: 'orange',
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

    