import axios from 'axios';
export const http=axios.create({baseURL:(import.meta.env.VITE_API_BASE_URL||'http://localhost:8080/api').replace(/\/$/,''),timeout:15000,headers:{'Content-Type':'application/json'}});
http.interceptors.request.use(config=>{const token=localStorage.getItem('portfolio_access_token');if(token)config.headers.Authorization=`Bearer ${token}`;return config;});
http.interceptors.response.use(r=>r,e=>{e.userMessage=e.response?.data?.message||e.response?.data?.error||(!e.response?'Serveur inaccessible. Vérifie le backend et VITE_API_BASE_URL.':null);if(e.response?.status===401){localStorage.removeItem('portfolio_access_token');localStorage.removeItem('portfolio_admin');}return Promise.reject(e);});
export const getErrorMessage=(e,fallback='Une erreur est survenue.')=>e?.userMessage||e?.response?.data?.message||e?.response?.data?.error||fallback;
