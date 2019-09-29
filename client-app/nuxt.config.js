module.exports = {
  mode: 'spa',

  head: {
    title: 'Grow',
    dir: {
      // assets: 'custom-assets',
      // layouts: 'custom-layouts',
      // middleware: 'custom-middleware',
      // pages: 'custom-pages',
      static: 'static',
      // store: 'custom-store'
    },
    script: [
      {
        src: 'https://unpkg.com/vue-lazyload/vue-lazyload.js'
      }
    ],
    meta: [
      {charset: 'utf-8'},
      {name: 'viewport', content: 'width=device-width, initial-scale=1'},
      {hid: 'description', name: 'description', content: 'Always here for you.'},
      {httpequiv: "Content-Security-Policy", content: "default-src 'self'; font-src 'self' data: fonts.gstatic.com;"}
    ],
    link:
        [
          {rel: 'icon', type: 'image/x-icon', href: '/comp/heart.svg'},
          {rel: 'icon', type: 'image/x-icon', href: '/heart.svg'},
          {
            rel: 'stylesheet',
            href: 'https://fonts.googleapis.com/icon?family=Material+Icons'
          },
          {
            rel: 'stylesheet',
            href: 'https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700,900'
          },
          {
            rel: 'stylesheet',
            href: 'https://cdn.jsdelivr.net/npm/vuetify@1.x/dist/vuetify.min.css'
          },
          {
            rel: 'stylesheet',
            href: 'https://use.fontawesome.com/releases/v5.8.2/css/all.css'
          },
          {
            rel: 'stylesheet',
            href: 'https://unpkg.com/leaflet@1.5.1/dist/leaflet.css'
          }
        ]
  },
  build: {
    transpile: ['vuetify/lib'],
    vendor:
        ['axios', 'vuetify', 'leaflet'],
    publicPath:
        `/${require('./secrets.json').NODE_ENV}/_nuxt/`
  }
  ,
  srcDir: 'client/',
  performance:
      {
        gzip: false
      }
  ,
  router: {
    base: `/`
  }
  ,
  dev: false,
  plugins:
      ['~/plugins/vuetify.js'
        , {
        src: "~/plugins/leaflet",
        ssr: false
      }
        , {
        src: "~/plugins/link-prevue",
        ssr: false
      }
      ],
  modules: [],
  server: {
    port: 3000, // default: 3000
    host: "0.0.0.0", // default: localhost,
    timing: false
  }
  // ,serverMiddleware: [
  //   // API middleware
  //   './api/index.js'
  // ]
}
