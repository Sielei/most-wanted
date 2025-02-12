import {useLocation} from "react-router-dom";

const useAuthRoute = () => {
    const location = useLocation();
    const authRoutes = ['/login'];
    return authRoutes.includes(location.pathname);
};

export default useAuthRoute;