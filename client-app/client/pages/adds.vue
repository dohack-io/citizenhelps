<template>
    <v-col>
        <v-row v-if="!loading" no-gutters align="center" justify="space-between">
            <i>powered by </i> <i class="caption" style="color: green"> CJ Affiliate</i>
            <v-btn-toggle
                    v-model="weather"
                    tile
                    color="deep-purple accent-3"
                    group
            >

                <v-btn small value="sunny">
                    <v-icon>mdi-weather-sunny</v-icon>
                </v-btn>

                <v-btn small value="cloudy">
                    <v-icon>mdi-weather-partly-cloudy</v-icon>
                </v-btn>
            </v-btn-toggle>
            <v-card
                    class="mt-1"
                    max-width="300"
                    v-if="ad.price && weather==='sunny' || ad.discount && weather==='cloudy'"
                    v-for="(ad,key) in ads"
                    :key="key"
            >
                <v-img
                        class="white--text align-end"
                        height="130px"
                        :src="ad.src"
                >
                    <v-card-title></v-card-title>
                </v-img>

                <v-card-subtitle class="pb-0">{{ad.title}}</v-card-subtitle>
                <v-row no-gutters justify="space-between" align="center" class="caption">
                    <v-col class="text--primary pa-1" v-if="weather==='sunny' && ad.price">
                        {{ad.price}}
                    </v-col>
                    <v-col class="text--primary pa-1" v-if="weather==='cloudy' && ad.price!= ad.discount && ad.price"
                           style=" text-decoration: line-through;color: red!important;">
                        {{ad.price}}
                    </v-col>
                    <v-col class="text--primary pa-1" v-if="weather==='cloudy'&&ad.discount">
                        {{ad.discount}}
                    </v-col>
                    <v-col class="flex-grow-0">
                        <v-btn color="rgb(255,127,79)" small><span style="color: #f7f8fb!important">book</span></v-btn>

                    </v-col>
                </v-row>


            </v-card>
        </v-row>
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

    </v-col>

</template>

<script>
  function sleep(s) {
    return new Promise(resolve => setTimeout(resolve, s * 1000));
  }

  export default {
    name: "adds",
    data: () => ({
      weather: 'sunny',
      ads: [
        {
          src: "https://media-cdn.tripadvisor.com/media/photo-s/16/32/91/00/the-original-sound-of.jpg",
          price: '€ 45 pro Person',
          title: 'Sound of Music die Orignal-Tour durch Salzburg',
          discount: null
        },
        {
          src: "https://media-cdn.tripadvisor.com/media/photo-s/19/49/94/0b/best-of-mozart-concert.jpg",
          discount: '€ 60 pro Person',
          title: 'Das Beste von Mozart: Konzert und Abendessen',
          price: null,
        },
        {
          src: "https://media-cdn.tripadvisor.com/media/photo-s/19/80/3d/0f/salzburg-lakes-and-mountains.jpg",
          discount: '€ 25 pro Person',
          title: 'Salzburg: Seen und Bergregion - Hop-on-Hop-off-Bustour',
          price: '€ 33 pro Person',
        },
        {
          src: "https://media-cdn.tripadvisor.com/media/photo-s/16/fc/9f/7f/untersberg-cable-car.jpg",
          price: '€ 25 pro Person',
          title: 'Untersberg Seilbahn in Salzburg',
          discount: '€ 25 pro Person',

        },
      ]
    }),
    methods: {
      open_link: function (link) {
        window.open(link, '_blank');
        // window.location.href = link;
      },
      switch_weather: async function (weather) {
        this.loading = true;
        // await sleep(0.4);
        this.weather = weather;
        console.log(this.weather, this.loading);
        this.loading = false;
        console.log(this.weather, this.loading);

      }
    },
    watch: {
      // weather: async function () {
      //   console.log(this.weather, this.loading);
      //   this.loading = true;
      //   await sleep(0.4);
      //   this.loading = false;
      //   console.log(this.weather, this.loading);
      // }

    }
  }
</script>

<style scoped>

</style>