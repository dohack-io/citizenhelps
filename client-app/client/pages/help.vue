<template>
    <v-app>
        <div>
            <v-row no-gutters style="height: 100vh;"
                   align="stretch">
                <v-card style="height: 100vh;font-size: 90px" block>
                    <v-row justify="center" align="center" style="height: 40%;">
                        <v-col style="height: 100px;text-align: center">
                            <v-col cols="12">
                                <v-icon style="font-size: 90px; color: black">call</v-icon>

                            </v-col>
                            <v-col cols="12" style="margin-top: -100px">
                                {{points}}

                            </v-col>
                        </v-col>
                    </v-row>
                    <v-row justify="center" align="center" style="height: 10%;">

                    </v-row>
                    <v-row justify="center" align="center" style="height: 10%;">
                        <v-col cols="10" v-if="coords" style="font-size: 15px;text-align: center">
                            (lat: {{Math.round(coords.lat * 10000) /10000 }},lng:
                            {{Math.round(coords.lng * 10000) /10000 }})
                            <v-icon v-if="green_visible" x-small color="green">far fa-check-circle</v-icon>
                        </v-col>
                        <v-col cols="10">


                            <v-btn :loading="loading" :disabled="disabled0"
                                   @click="(disabled0=true,get_geolocation=true)" large block>
                                standort
                                senden
                                <v-icon class="pl-2" small color="blue" v-if="disabled0">far fa-compass</v-icon>
                            </v-btn>
                        </v-col>
                        <v-col cols="10" style="text-align: center">
                            <v-btn :disabled="disabled1" @click="disabled1=true" large block>persönliche daten senden
                                <v-icon class="pl-2" v-if="disabled1" small color="blue">far fa-user</v-icon>

                            </v-btn>
                            <v-btn fab>
                                <v-icon color="red" @click="close()">fas fa-phone-slash</v-icon>
                            </v-btn>

                        </v-col>


                    </v-row>


                </v-card>
            </v-row>

            <Geolocation @coords="got_coords" v-if="get_geolocation"></Geolocation>
        </div>

    </v-app>

</template>

<script>
  import {mdiFormatClear, mdiCallMade} from '@mdi/js';
  import Geolocation from "./geolocation.vue";


  function sleep(s) {
    return new Promise(resolve => setTimeout(resolve, s * 1000));
  }

  export default {
    name: "help",
    components: {
      Geolocation,

    },
    data: () => ({
      loading: false,
      green_visible: false,
      get_geolocation: false,
      coords: undefined,
      points: ".",
      calling_icon: mdiCallMade,
      disabled1: false,
      disabled0: false,
    }),
    methods: {
      got_coords: async function (val) {
        this.loading = true;
        await sleep(0.9);
        this.coords = val;
        console.log('got coords', this.coords);
        this.loading = false;
        await sleep(0.4);
        this.green_visible=true;

      },
      close: function () {
        this.$emit('close');
      },
      alert: function (msg) {
        alert(msg)
      },
      running_points: async function () {
        while (true) {
          await sleep(0.5);
          if (this.points.length > 3) {
            this.points = "";
          }
          this.points = this.points + ".";
        }

      }
    },
    mounted() {
      this.running_points();


    }
  }
</script>

<style scoped>

</style>