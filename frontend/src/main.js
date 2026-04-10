import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import i18n from './i18n'

import 'vuetify/styles'
import '@mdi/font/css/materialdesignicons.css'

import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// Tema personalizado
const vuetify = createVuetify({
  components,
  directives,
  theme: {
    defaultTheme: 'futureSpaceTheme',
    themes: {
      futureSpaceTheme: {
        dark: true,
        colors: {
          primary: '#026FC1',
          secondary: '#008ED6',
          background: '#001021',    // fondo general
          surface: '#012033',       // tarjetas
          error: '#ef4444',         // errores
          success: '#22c55e',       // exito
          warning: '#f59e0b',       // advertencia
          info: '#38bdf8'           // información
        }
      }
    }
  }
})

const app = createApp(App)

app.use(router)
app.use(vuetify)
app.use(i18n)

app.mount('#app')