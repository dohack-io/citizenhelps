<!--

Requetes this librery:
https://sdk.amazonaws.com/js/aws-sdk-2.16.0.min.js
More info here:         // https://docs.aws.amazon.com/rekognition/latest/dg/image-bytes-javascript.html

Designed to work with Nuxt / Vue.js / AWS Rekognition and Lambda

-->


<template>
    <v-container>


        <v-row column>

            <v-row justify="center" wrap>
                <v-col pl-2 v-if="stage==='photo_taken'">
                    <v-img contain width="100%" :height="video_height" :src="url"></v-img>
                </v-col>
                <v-col pl-2 v-if="stage==='start'">
                    <video ref="video" width="100%" :height="video_height" autoplay></video>
                </v-col>

            </v-row>
            <v-row align="center" justify="center">
                <v-col v-if="stage==='start'" cols="10">

                    <v-btn block @click="(make_photo(),stage='photo_taken')">Make Photo</v-btn>
                </v-col>
                <v-col v-if="stage==='photo_taken'" cols="10">
<!--                    <v-btn block @click="send_picture()">Save&Send</v-btn>-->
                    <v-btn block @click="(stage='start',stop_video(),go())">New Image</v-btn>
                </v-col>
            </v-row>
            <v-row align="center" justify="end" style="position: absolute;width: 100%" wrap>
                <v-col cols="3" style="text-align: left">
                    <v-btn @click="close()"> {{flipBtn?'o':'x'}}</v-btn>
                </v-col>

            </v-row>

        </v-row>


        <canvas style="display: none" ref="canvas" width="320"
                :height="video_height"></canvas>
    </v-container>

</template>

<script>

  var the_stream = undefined;
  import axios from "axios";

  async function getImage({
                            canvas,
                            width,
                            height,
                            mime = 'image/jpeg',
                            quality = 0.8,
                          }) {
    return new Promise(resolve => {
      const tmpCanvas = document.createElement('canvas');
      tmpCanvas.width = width;
      tmpCanvas.height = height;

      const ctx = tmpCanvas.getContext('2d');
      ctx.drawImage(
          canvas,
          0,
          0,
          canvas.width,
          canvas.height,
          0,
          0,
          width,
          height,
      );

      tmpCanvas.toBlob(resolve, mime, quality);
    });
  }


  axios.defaults.headers.post = {
    'Access-Control-Allow-Origin': "*",
    'Access-Control-Allow-Credentials': 'true',
    'Content-Type': 'application/json',
  };

  export default {
    name: "Video",

    data: () => ({
      //Stages
      stage: 'start',
      //Camera
      flipBtn: false,
      //Video
      stream: undefined,
      video: undefined,
      url: undefined,
      canvas: undefined,
      loading: false,
      video_width: 320,
      video_on: false,
      video_ratio: 320 / 240,

    }),
    methods: {
      close: function () {
        this.stop_video();
        this.$emit('close');
      },
      //Playing Video

      stop_video: function () {
        this.video.srcObject.getVideoTracks().forEach(track => track.stop());
        this.video_on = false;
      },
      go: function () {
        if (process.client) {
          console.log('this.$navigator.mediaDevices.getUserMedia:');

          console.log('Navigator:', navigator);
          if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
            // Not adding `{ audio: true }` since we only want video now
            navigator.mediaDevices.getUserMedia({
                  video: true
                })
                .then(stream => {
                  //FlipBtn
                  let supports = navigator.mediaDevices.getSupportedConstraints();
                  if (supports['facingMode'] === true) {
                    this.flipBtn = false;
                  } else {
                    this.flipBtn = true;
                  }
                  //video.src = window.URL.createObjectURL(stream);
                  console.log('Try to do stuff');
                  // if (stream) {
                  //   stream.getTracks().forEach(track => track.stop())
                  // }
                  the_stream = stream;
                  let tracks = stream.getTracks();
                  for (let i = 0; i < tracks.length; i++) {
                    this.video_ratio = tracks[i].getSettings().aspectRatio;
                    console.log(tracks[i].getSettings());
                  }
                  this.video = this.$refs.video;

                  this.video.srcObject = the_stream;
                  this.video.play();
                  this.video_on = true;
                  // alert(true);
                })
                .catch(error => {
                  // Handle Errors here.
                  this.loading = false;
                  // alert(error.message);
                  var errorCode = error.code;
                  var errorMessage = error.message;
                  console.log(errorCode, errorMessage);
                  // ...
                });
            ;
          }
        }
      },
      // Sends picture to the local server
      send_picture: function (file) {
        if(!file){
          file = this.photo;
        }
        let data = new FormData();
        data.append('file', file, file.fileName);
        console.log(data);
        let payload = {
          "art": "personalschaden",
          "lat": 51.002,
          "lon": 8.023,
          "img": data,
          "beschreibung": "Test Nummer 1",
          "did_send_personal_data": true,
          "zeitstempel": 1569726392
        };

        axios
            .post(
                "http://134.209.232.135:3001/api/reports",
                payload,
                {
                  headers: {
                    'accept': 'application/json',
                    'Accept-Language': 'en-US,en;q=0.8',
                    'Content-Type': `multipart/form-data; boundary=${data._boundary}`,
                  }
                })
            .then(response => {
              console.log("Axios:", response);
              this.loading = false;
            })
            .catch(error => {
              // Handle Errors here.
              this.loading = false;

              var errorCode = error.code;
              var errorMessage = error.message;
              console.log('Error', errorCode, errorMessage);
              // ...
            });
      },
      //Making Photo
      make_photo: async function () {

        this.loading = true;

        this.canvas = this.$refs.canvas;
        this.canvas.getContext('2d').drawImage(this.video, 0, 0, this.video_width, this.video_height);
        // Get an image dataURL from the canvas.
        console.log('this.canvas', this.canvas);
        let imageDataURL = this.canvas.toDataURL('image/jpg');
        var can = this.canvas;
        const photo = await getImage({canvas: can, width: this.video_width, height: this.video_height});

        // let imageBlob = this.canvas.toBlob('image/jpg');
        this.url = imageDataURL;
        console.log('imageBlob', photo);
        console.log('The image URL:', this.url);
        this.photo = photo;
        // this.send_picture(photo);
        this.loading = false;
      },

    },
    computed: {
      device_horisontal: function () {
        return this.$vuetify.breakpoint.width / this.$vuetify.breakpoint.height > 1
      },
      video_height: function () {
        console.log(this.$vuetify.breakpoint.width, this.$vuetify.breakpoint.height);

        console.log(this.video_ratio);
        console.log(this.device_horisontal);
        if (this.device_horisontal) {
          return parseInt(320 / this.video_ratio)

        } else {
          return parseInt(320 * this.video_ratio)

        }
      }

    },
    created() {
      this.go();
      // Get access to the camera!


      // Trigger photo take
      // this.$refs.snap.addEventListener("click", function () {
      //   context.drawImage(video, 0, 0, 640, 480);
      // });

    }
  }
</script>

<style scoped>

</style>