<template>
    <v-data-table
            :headers="headers"
            :items="transactions"
            :items-per-page="5"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar flat color="white">
                <i class="caption">powered by <br/> <span style="color: blue"> American Express</span></i>
                <v-spacer></v-spacer>
                <v-btn color="blue" small class="mt-2">Get all invoices</v-btn>
            </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="open_link('/apps/uber.pdf')"
            >
                mdi-file
            </v-icon>
        </template>

    </v-data-table>
</template>

<script>
  export default {
    name: "invoices",
    data: () => ({
      transactions: [
        {
          date: '2019-11-08',
          text: 'Top Up by TIROLSHOF',
          value: '+300,00'
        }, {
          date: '2019-11-08',
          text: 'Spa at TIROLSHOF',
          value: '-49,99'
        }, {
          date: '2019-11-09',
          text: 'Uber ride',
          value: '-13.57'
        }, {
          date: '2019-11-09',
          text: 'Salrburg Bahn',
          value: '-13,00',
        }, {
          date: '2019-11-10',
          text: 'Mozart Museum',
          value: '-10,00'
        },
        // ['2019-11-09', 'Uber', '23,00 EUR'],
        // ['2019-11-09', 'Salrburg Bahn', '13,00 EUR'],
        // ['2019-11-10', 'Spa at TIROLSHOF', '49,99 EUR'],
        // ['2019-11-10', 'Mozart Museum', '10,00 EUR'],
      ],
      headers: [
        {
          text: 'Date',
          align: 'left',
          sortable: false,
          value: 'date',
        },
        {text: 'Text', value: 'text'},
        {text: 'EUR', value: 'value'},
        {text: '', value: 'action', sortable: false},
      ],
    }),
    methods: {
      open_link: function (link) {
        window.location.href = link;
      }
    }
  }
</script>

<style scoped>

</style>