import {http} from './http';
export const authApi={
 async login(credentials){const {data}=await http.post('/auth/login',credentials);const token=data?.token||data?.accessToken;if(!token)throw new Error('La réponse de connexion ne contient pas de token JWT.');localStorage.setItem('portfolio_access_token',token);localStorage.setItem('portfolio_admin',JSON.stringify({username:data.username||data.email||credentials.email,role:data.role||'ADMIN'}));return data;},
 logout(){localStorage.removeItem('portfolio_access_token');localStorage.removeItem('portfolio_admin');},
 isAuthenticated(){return Boolean(localStorage.getItem('portfolio_access_token'));},
 getAdmin(){try{return JSON.parse(localStorage.getItem('portfolio_admin')||'null')}catch{return null}}
};
