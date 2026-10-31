import {http} from './http';

export const authApi = {
  async login(credentials) {
    const {data: csrf} = await http.get('/auth/csrf');
    const {data} = await http.post('/auth/login', credentials, {
      headers: {'X-XSRF-TOKEN': csrf.token},
    });
    localStorage.removeItem('portfolio_access_token');
    localStorage.setItem('portfolio_admin', JSON.stringify({
      username: data.username || data.email || credentials.email,
      role: data.role || 'ADMIN',
    }));
    return data;
  },
  async logout() {
    try {
      const {data: csrf} = await http.get('/auth/csrf');
      await http.post('/auth/logout', null, {
        headers: {'X-XSRF-TOKEN': csrf.token},
      });
    } finally {
      localStorage.removeItem('portfolio_access_token');
      localStorage.removeItem('portfolio_admin');
    }
  },
  isAuthenticated() {
    return Boolean(localStorage.getItem('portfolio_admin'));
  },
  getAdmin() {
    try {
      return JSON.parse(localStorage.getItem('portfolio_admin') || 'null');
    } catch {
      return null;
    }
  },
};
