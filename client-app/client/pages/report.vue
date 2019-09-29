<template>
    <v-container>
        <Video style="height: 100%" @photo_url="photo_url" @close="take_video=!take_video"
               v-if="take_video"></Video>

        <v-row justify="space-around" align="start" v-if="!take_video">
            <v-col cols="6">
                <v-card flat style="height: 100%;">
                    <v-card-text>User-ID: 485JDAL849D</v-card-text>
                </v-card>
            </v-col>
            <v-col cols="6">
                <v-card style="height: 100%; width: 100%; text-align: center" v-if="!take_video"
                        @click="take_video=!take_video">
                    <v-icon x-large class="my-5">{{camera_icon}}</v-icon>
                </v-card>
            </v-col>
            <v-divider></v-divider>
            <v-col cols="12">
                <v-row>
                    Typ des Vorfalls
                </v-row>
                <v-row>
                    <v-col cols="12" style="text-align: center">
                        <v-btn-toggle v-model="toggle_multiple" multiple>
                            <v-btn :color="(btn1?'blue':'')" @click="btn1=!btn1" :depressed="btn1"
                            >Personenschaden
                            </v-btn>
                            <v-btn :color="(!btn1?'blue':'')" @click="btn1=!btn1" :depressed="!btn1"
                            >Sachschaden
                            </v-btn>
                        </v-btn-toggle>
                    </v-col>
                    <v-col cols="12">
                        <v-switch
                                color="blue"
                                v-model="comments_on"
                                label="Zusätliche Anmerkungen"
                                value="John"></v-switch>

                        <v-text-field
                                v-if="comments_on"
                                label="Anmerkung"
                                solo
                        ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                        Sendeoptionen
                        <v-switch
                                color="blue"
                                v-model="icon1"
                                append-icon="far fa-compass"
                                label="Meinen Standort" value="John"></v-switch>
                        <v-switch
                                color="blue"
                                v-model="icon2"
                                append-icon="far fa-user"
                                label="Perönliche Daten" value="Jacob"></v-switch>
                    </v-col>
                </v-row>
            </v-col>
            <v-col cols="10" style="text-align: center; ">
                <v-btn block large :loading="loading" @click="send_form()">
                    senden
                    <v-icon class="pl-2" small color="blue" v-if="photo_sent">{{camera_icon}}</v-icon>

                    <v-icon class="pl-2" small color="blue" v-if="icon1">far fa-compass</v-icon>
                    <v-icon class="pl-2" v-if="icon2" small color="blue">far fa-user</v-icon>
                </v-btn>
            </v-col>
        </v-row>
    </v-container>

</template>

<script>
  import Video from "./video.vue";


  import axios from "axios";

  axios.defaults.headers.post = {
    'Access-Control-Allow-Origin': "*",
    'Access-Control-Allow-Credentials': 'true',
    'Content-Type': 'application/json',
  };

  function sleep(s) {
    return new Promise(resolve => setTimeout(resolve, s * 1000));
  }

  import {mdiCamera, mdiCrosshairsGps} from '@mdi/js'


  export default {
    name: "report",
    components: {
      Video,

    },
    data: () => ({
      photo_sent: false,
      toggle_multiple: [],
      icon1: false,
      icon2: false,
      btn1: true,
      loading: false,
      //Form steering
      case_type: false,
      comments_on: false,
      mdiCrosshairsGps: mdiCrosshairsGps,
      camera_icon: mdiCamera,
      take_video: false,
      img_url: null,
      valid: true,
      name: '',
      nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters',
      ],
      email: '',
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      ],
      select: null,
      items: [
        'Item 1',
        'Item 2',
        'Item 3',
        'Item 4',
      ],
      checkbox: false,
      lazy: false,
    }),
    computed: {},
    methods: {
      photo_url: function (val) {
        this.img_url = val;
      },
      close: async function () {
        this.loading = true;
        await sleep(1);
        this.loading = false;
        this.$emit('close');
      },


      validate() {
        if (this.$refs.form.validate()) {
          this.snackbar = true
        }
      },
      reset() {
        this.$refs.form.reset()
      },
      resetValidation() {
        this.$refs.form.resetValidation()
      },
      // Sends picture to the local server
      send_form: function () {
        // let dateObj = new Date(unixTimestamp * 1000);
        // let utcString = dateObj.toUTCString();
        let time = Math.round(Date.now() / 1000);
        console.log('Time:', Math.round(Date.now() / 1000));
        console.log('Image as String:', this.img_url);

        try {
          const data = postData('http://134.209.232.135:3001/api/reports', {
            art: "hi",
            lat: 43.3,
            lon: 34.3,
            bild: this.img_url,
            beschreibung: "beschreibung"+time,
            did_send_personal_data: true,
            zeitstempel: 1
          });
          console.log('r',data);
          console.log(JSON.stringify(data)); // JSON-string from `response.json()` call
        } catch (error) {
          console.error(error);
        }

        async function postData(url = '', data = {}) {
          // Default options are marked with *
          const response = await fetch(url, {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            mode: 'no-cors', // no-cors, *cors, same-origin
            cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
            credentials: 'same-origin', // include, *same-origin, omit
            headers: {
              'Content-Type': 'application/json'
              // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            redirect: 'follow', // manual, *follow, error
            body: JSON.stringify(data) // body data type must match "Content-Type" header
          });
          return await response; // parses JSON response into native JavaScript objects
        }

//         let payload = JSON.stringify({
//           art: "hi",
//           lat: 43.3,
//           lon: 34.3,
//           bild: this.img_url,
//           beschreibung: "beschreibung",
//           did_send_personal_data: true,
//           zeitstempel: time
//         });
//         // "http://localhost:3000/api/hackathons",
// // "http://134.209.232.135:3001/api/reports",
//         axios
//             .post(
//                 "http://134.209.232.135:3001/api/reports",
//                 payload,
//                 {
//                   headers: {
//                     'Content-Type': 'application/json',
//                     // 'Accept-Language': 'en-US,en;q=0.8',
//                     // 'Content-Type': `multipart/form-data; boundary=${data._boundary}`,
//                   }
//                 })
//             .then(response => {
//               console.log("Axios:", response);
//               this.$emit('photo_sent');
//               this.photo_sent = true;
//               this.loading = false;
//               this.close();
//             })
//             .catch(error => {
//               // Handle Errors here.
//               this.loading = false;
//
//               var errorCode = error.code;
//               var errorMessage = error.message;
//               console.log('Error', errorCode, errorMessage);
//               // ...
//             });
      },
    }
  }
</script>

<style scoped>

</style>