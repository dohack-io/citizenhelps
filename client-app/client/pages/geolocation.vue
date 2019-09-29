<template>

</template>

<script>
  export default {
    name: "geolocation",
    data: () => ({
      location: {}
    }),
    methods: {
      showPosition: function (position) {
        let lat = position.coords.latitude;
        let lng = position.coords.longitude;

        this.location.lat = lat;
        this.location.lng = lng;
        console.log("Lat-Lng:", this.location.lat, this.location.lng);
        this.$emit('coords', this.location)
      }
    },
    mounted() {
      console.log('geolocation');

      // this.running_points();

      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(this.showPosition);
      } else {
        alert('Browser does not support geolocation.')
      }

    }
  }
</script>

<style scoped>

</style>