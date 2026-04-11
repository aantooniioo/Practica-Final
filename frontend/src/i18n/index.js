import { createI18n } from "vue-i18n";

// Importa las traducciones
import es from './es.json';
import en from './en.json';

// Configura el i18n
const i18n = ({
  legacy: false,
  locale: localStorage.getItem('lang') || 'es',
  fallbackLocale: 'es',
  messages: {
    es,
    en
  }
})

// Exporta el i18n para poder usarlo en la app

export default createI18n(i18n);