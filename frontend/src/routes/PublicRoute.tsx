import { Navigate, Outlet } from "react-router-dom";
import { useAuthStore } from "../features/auth/store/auth.store";


export function PublicRoute(){
    const isAuthanticated = useAuthStore((state)=> state.isAuthanticated)

    if(isAuthanticated){
        return <Navigate to={"/"} replace/>
    }

    return <Outlet/>
}