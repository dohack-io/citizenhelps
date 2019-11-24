<template>
    <v-app>
        <v-container style="max-width: 600px">
            <span class="subtitle-2">
                            Die Eingaben werden nicht gespeichert.
            </span>
            <v-row>
                <v-col>
                    <v-text-field
                            :counter="50"
                            v-model="text"></v-text-field>
                    <v-btn @click="evaluate(text)">evaluate</v-btn>
                    <!--                    is offensive: {{is_offensive_boolen}}-->
                    <v-row v-for="(val,key) in results.slice().reverse()" :key="key">
                        <v-col>
                            <v-card class="px-2">
                                <v-row justify="space-between">
                                    <v-col>
                                        {{val.text}}

                                    </v-col>
                                    <v-col class="flex-grow-0 flex-shrink-1">
                                        {{val.is_offensive?'böse':'gut'}}

                                    </v-col>
                                </v-row>
                            </v-card>
                        </v-col>

                    </v-row>
                </v-col>
            </v-row>

            <!--            <Help @close="stage='start'" v-if="stage==='help'"></Help>-->
            <!--            <Report @close="stage='start'" v-if="stage==='report'"></Report>-->

        </v-container>

    </v-app>

</template>

<script>
  import Vue from "vue";

  //import {LMap,LTileLayer,LControl} from "./../../node_modules/vue2-leaflet"

  // var api_host = 'https://ohsrb65n38.execute-api.eu-central-1.amazonaws.com/proxy/'

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
  var api_aindex = axios.create({});

  export default {
    components: {},
    data: () => ({
      text: null,
      is_offensive_boolen: null,
      results: []


    }),
    methods: {
      is_offensive_func: function (val) {
        val = val.body.attributeScores;
        for (let key in val) {
          if (val.hasOwnProperty(key)) {
            if (val[key].summaryScore.value > 0.75) {
              return true
            }
          }
        }
        return false
      },
      evaluate: function (text) {
        let payload = {
          "text": text,
          "lang": "de"
        };
        axios
            .post(
                "/app/api/comment",
                payload,
                // {
                //   headers: google.headers
                // }
            )
            .then(response => {
              console.log("Axios:", response.data);
              this.is_offensive_boolen = this.is_offensive_func(response.data);
              this.results.push({
                text: text,
                is_offensive: this.is_offensive_boolen
              });
              this.text = null;
              // console.log(this.results);
            })
            .catch(error => {
              // Handle Errors here.
              let errorCode = error.code;
              let errorMessage = error.message;
              console.log('Error', errorCode, errorMessage);
              // ...
            });
      }
    },
    watch: {},
    computed: {},
    created() {

    }
  };
</script>

    