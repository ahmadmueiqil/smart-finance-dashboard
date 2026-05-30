import { useAuthStore } from "../../features/auth/store/auth.store";
import Button from "../ui/Button"
import {Menu } from "lucide-react"
import { useLocation } from "react-router-dom"


type Props ={
    onMenuClick: () => void
}


export function Navbar({onMenuClick}: Props){
    const logout = useAuthStore((state)=> state.logout)

    const location = useLocation()

        const pageTitles: Record<string, string> = {

            "/": "Dashboard Overview",

            "/bills": "Bills Management",

            "/transactions": "Transactions History",

            "/currency": "Currency Converter",

            "/profile": "My Profile",

        }


    return(
        <header className="h-16 border-b border-zinc-800 bg-black/70 backdrop-blur-xl flex items-center justify-between px-6 md:px-6">
            <div className="flex items-center gap-3">
                <button onClick={onMenuClick} 
                    className="
                        md:hidden
                        w-10 h-10
                        rounded-full
                        bg-zinc-800
                        flex
                        items-center
                        justify-center
                    "
                        ><Menu size={20}/></button>

            <h1 className="text-lg font-semibold">

                {
                    pageTitles[
                    location.pathname
                    ] ?? "Smart Finance"
                }

            </h1>

            </div>
            <div className="flex items-center gap-3 md:gap-4">


                <Button
                    onClick={logout}
                    variant="danger"
                    >
                    Logout
                </Button>
                
            </div>

        </header>
    )
}