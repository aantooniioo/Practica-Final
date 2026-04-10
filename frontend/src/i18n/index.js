import { createI18n } from "vue-i18n";

import es from './es.json';
import en from './en.json';

const i18n = ({
  legacy: false,
  locale: localStorage.getItem('lang') || 'es',
  fallbackLocale: 'es',
  messages: {
    es,
    en
  }
})

export default createI18n(i18n);