import { Outlet } from "react-router-dom";


export function AuthLayout(){
    return(
        <div className="min-h-screen bg-zinc-950 text-white">
            <Outlet/>
        </div>
    )
}