import { Navigate, Outlet } from "react-router-dom";
import { useAuthStore } from "../features/auth/store/auth.store";

export function ProtectedRoute(){
    const isAuthenticated = useAuthStore((state) => state.isAuthanticated)

    if(!isAuthenticated){
        return <Navigate to="/login" replace/>
    }

    return <Outlet/>
}