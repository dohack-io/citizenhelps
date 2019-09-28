<template>
    <v-app>


        <v-row no-gutters class="pa-1" style="height: 100%;"
               align="stretch">
            <v-col>
                <v-card color="red" x-large style="height: 100%;font-size: 35px" block>
                    <v-row justify="center" align="center" style="height: 100%;">
                        <v-col style="height: 100px;text-align: center">
                            <v-icon style="font-size: 60px">call</v-icon>
                            {{points}}
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
    }),
    methods: {
      close:function(){
        this.$emit('close');
      },
      alert:function(msg){
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