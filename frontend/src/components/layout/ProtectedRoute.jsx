import {Navigate,Outlet,useLocation} from 'react-router-dom';
import {authApi} from '../../api/authApi';
export function ProtectedRoute(){const location=useLocation();return authApi.isAuthenticated()?<Outlet/>:<Navigate to="/admin/login" replace state={{from:location}}/>;}
