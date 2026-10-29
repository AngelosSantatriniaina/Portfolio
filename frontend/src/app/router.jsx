import {createBrowserRouter} from 'react-router-dom';
import {SiteLayout} from '../components/layout/SiteLayout';
import {HomePage} from '../pages/HomePage';
import {AdminLoginPage} from '../pages/AdminLoginPage';
import {AdminDashboardPage} from '../pages/AdminDashboardPage';
import {ProtectedRoute} from '../components/layout/ProtectedRoute';
import {NotFoundPage} from '../pages/NotFoundPage';
export const router=createBrowserRouter([{element:<SiteLayout/>,children:[{path:'/',element:<HomePage/>},{path:'/admin/login',element:<AdminLoginPage/>},{path:'/admin',element:<ProtectedRoute/>,children:[{index:true,element:<AdminDashboardPage/>}]},{path:'*',element:<NotFoundPage/>}]}]);
