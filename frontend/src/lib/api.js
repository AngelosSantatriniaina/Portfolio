// URL racine du backend : configurable dans le fichier .env du frontend.
const API_URL = (import.meta.env.VITE_API_URL || 'http://localhost:8080').replace(/\/$/, '');

// Lit le cookie CSRF créé par Spring Security.
function readCookie(name) {
  const row = document.cookie.split('; ').find((item) => item.startsWith(`${name}=`));
  return row ? decodeURIComponent(row.split('=').slice(1).join('=')) : '';
}

async function request(path, options = {}) {
  const method = (options.method || 'GET').toUpperCase();
  const headers = new Headers(options.headers || {});
  if (options.body && !(options.body instanceof FormData)) headers.set('Content-Type', 'application/json');

  // Pour les écritures, on demande d'abord à Spring un token CSRF.
  if (!['GET', 'HEAD', 'OPTIONS'].includes(method) && !path.endsWith('/auth/login')) {
    await fetch(`${API_URL}/api/v1/auth/csrf`, { credentials: 'include' });
    const token = readCookie('XSRF-TOKEN');
    if (token) headers.set('X-XSRF-TOKEN', token);
  }

  const response = await fetch(`${API_URL}${path}`, {
    ...options, method, headers, credentials: 'include',
  });
  if (response.status === 204) return null;
  const raw = await response.text();
  let data = null;
  try { data = raw ? JSON.parse(raw) : null; } catch { data = raw; }
  if (!response.ok) {
    const message = data?.message || data?.detail || (typeof data === 'string' ? data : `Erreur HTTP ${response.status}`);
    throw new Error(message);
  }
  return data;
}

export const api = {
  get: (path) => request(path),
  post: (path, body) => request(path, { method: 'POST', body: JSON.stringify(body) }),
  put: (path, body) => request(path, { method: 'PUT', body: JSON.stringify(body) }),
  delete: (path) => request(path, { method: 'DELETE' }),
  login: (email, password) => request('/api/v1/auth/login', { method: 'POST', body: JSON.stringify({ email, password }) }),
  logout: () => request('/api/v1/auth/logout', { method: 'POST' }),
  contact: (body) => request('/api/v1/contact', { method: 'POST', body: JSON.stringify(body) }),
};
export const assetUrl = (path) => path ? (path.startsWith('http') ? path : `${API_URL}${path.startsWith('/') ? '' : '/'}${path}`) : '';
