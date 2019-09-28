<template>
    <v-app>
        <v-row no-gutters class="pa-1" style="height: 90%;"
               align="stretch">
            <v-col>
                <v-card x-large style="height: 100%;font-size: 90px" block>
                    <v-row justify="center" align="center" style="height: 50%;">
                        <v-col style="height: 100px;text-align: center">
                            <v-col cols="12">
                                <v-icon style="font-size: 90px; color: black">call</v-icon>

                            </v-col>
                            <v-col cols="12">
                                {{points}}

                            </v-col>
                        </v-col>
                    </v-row>
                    <v-row justify="center" align="center" style="height: 10%;">

                    </v-row>
                    <v-row justify="center" align="start" style="height: 10%;" wrap>
                        <v-col cols="10">
                            <v-btn :disabled="disabled0" @click="disabled0=true" large block>standort senden</v-btn>
                        </v-col>
                        <v-col cols="10">
                            <v-btn :disabled="disabled1" @click="disabled1=true" large block>persönliche daten senden
                            </v-btn>
                        </v-col>
                    </v-row>


                </v-card>
            </v-col>
        </v-row>
    </v-app>

</template>

<script>
  import {mdiFormatClear, mdiCallMade} from '@mdi/js'

  function sleep(s) {
    return new Promise(resolve => setTimeout(resolve, s * 1000));
  }

  export default {
    name: "help",
    data: () => ({
      points: ".",
      calling_icon: mdiCallMade,
      disabled1: false,
      disabled0: false,
    }),
    methods: {
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