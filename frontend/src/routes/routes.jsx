import Login from "@/features/auth/pages/Login.jsx";
import MostWanted from "@/features/wanted/pages/MostWanted.jsx";
import MostWantedDetail from "@/features/wanted/pages/MostWantedDetail.jsx";

let routes = {
    default: [
        {
            path: '/login',
            element: <Login />,
        },
        {
            path: '/wanted',
            element: <MostWanted/>,
            // protected: true
        },
        {
            path: '/wanted/:uid',
            element: <MostWantedDetail/>,
            // protected: true
        }
    ]
}

export default routes;