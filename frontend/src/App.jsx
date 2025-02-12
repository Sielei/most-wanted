import {Suspense} from "react";
import {Route, Routes} from "react-router-dom";
import ProtectedRoute from "@/routes/ProtectedRoute.jsx";
import routes from "@/routes/routes.jsx";
import useAuthRoute from "@/hooks/useAuthRoute.js";

function App() {
    const isAuthPage = useAuthRoute();
  return (
    <>
      <div className='flex h-screen bg-gray-50'>
          <Suspense fallback={<div>Loading...</div>}>
              <div className='flex flex-1 flex-col'>
                  <main className='flex-1 overflow-auto p-6'>
                      <Routes>
                          {routes.default.map((route, index) => {
                              return (
                                  <Route
                                      key={index}
                                      path={route.path}
                                      element={route.protected ?
                                          <ProtectedRoute>{route.element}</ProtectedRoute> : route.element}
                                  />
                              );
                          })}
                      </Routes>
                  </main>
              </div>
          </Suspense>
      </div>
    </>
  )
}

export default App
