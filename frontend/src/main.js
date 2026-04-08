import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

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
          primary: '#2563eb',     
          secondary: '#1e40af',   
          background: '#0b1a2b',  // fondo general
          surface: '#1e293b',     // tarjetas
          error: '#ef4444',       // errores
          success: '#22c55e',     // exito
          warning: '#f59e0b',     // advertencia
          info: '#38bdf8'         // informacion
        }
      }
    }
  }
})

createApp(App)
  .use(router)
  .use(vuetify)
  .mount('#app')