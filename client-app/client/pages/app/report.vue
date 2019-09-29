<template>
    <v-app>
        <v-container>
            <Video style="height: 100%" @close="take_video=!take_video" v-if="take_video"></Video>

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
                                >Peronenschaden
                                </v-btn>
                                <v-btn :color="(!btn1?'blue':'')" @click="btn1=!btn1" :depressed="!btn1" v-model="btn2"
                                >Sachschaden
                                </v-btn>
                            </v-btn-toggle>
                        </v-col>
                        <v-col cols="12">
                            <v-checkbox v-model="comments_on" label="Zusätliche Anmerkungen"></v-checkbox>
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
                    <v-btn block large :loading="loading" @click="close()">
                        senden
                        <v-icon class="pl-2" small color="blue" v-if="icon1">far fa-compass</v-icon>
                        <v-icon class="pl-2" v-if="icon2" small color="blue">far fa-user</v-icon>
                    </v-btn>
                </v-col>


            </v-row>
        </v-container>


    </v-app>


</template>

<script>
  import Video from "./video.vue";

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
    }
  }
</script>

<style scoped>

</style>