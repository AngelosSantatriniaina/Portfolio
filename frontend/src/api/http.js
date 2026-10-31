import axios from 'axios';

const configuredApiUrl = (import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1').replace(/\/$/, '');
const apiBaseUrl = configuredApiUrl.endsWith('/api') ? `${configuredApiUrl}/v1` : configuredApiUrl;

export const http = axios.create({
  baseURL: apiBaseUrl,
  timeout: 15000,
  withCredentials: true,
  headers: {'Content-Type': 'application/json'},
});

http.interceptors.request.use(async config => {
  if (['POST', 'PUT', 'PATCH', 'DELETE'].includes(config.method?.toUpperCase())) {
    const {data: csrf} = await http.get('/auth/csrf');
    config.headers['X-XSRF-TOKEN'] = csrf.token;
  }
  return config;
});

http.interceptors.response.use(
  response => response,
  error => {
    error.userMessage = error.response?.data?.message
      || error.response?.data?.error
      || (error.response?.status === 403
        ? 'Accès refusé. La session administrateur ou le jeton CSRF a expiré ; reconnecte-toi puis réessaie.'
        : null)
      || (!error.response ? 'Serveur inaccessible. Vérifie le backend et VITE_API_BASE_URL.' : null);
    if (error.response?.status === 401) {
      localStorage.removeItem('portfolio_access_token');
      localStorage.removeItem('portfolio_admin');
    }
    return Promise.reject(error);
  },
);

export const getErrorMessage = (error, fallback = 'Une erreur est survenue.') =>
  error?.userMessage || error?.response?.data?.message || error?.response?.data?.error || error?.message || fallback;

export const assetUrl = path => {
  if (!path) return '';
  if (/^https?:\/\//i.test(path)) return path;
  const apiOrigin = new URL(http.defaults.baseURL, window.location.origin).origin;
  return new URL(path, apiOrigin).toString();
};
