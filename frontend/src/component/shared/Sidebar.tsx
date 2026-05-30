import { Link, useLocation } from "react-router-dom";
import { X } from "lucide-react"
import {
  LayoutDashboard,
  Receipt,
  ArrowLeftRight,
  DollarSign,
  User,
} from "lucide-react"

type Props = {
    onClose?: () => void,
    mobile?: boolean
}

const Links = [

    {
        label: "Dashboard",
        path: "/",
        icon: LayoutDashboard,
    },

    {
        label: "Bills",
        path: "/bills",
        icon: Receipt,
    },

    {
        label: "Transactions",
        path: "/transactions",
        icon: ArrowLeftRight,
    },

    {
        label: "Currency",
        path: "/currency",
        icon: DollarSign,
    },

    {
        label: "Profile",
        path: "/profile",
        icon: User,
    },

]

export function Sidebar({
    mobile = false,
    onClose,
} : Props){
    const location = useLocation()


    return(
                <aside className="
                w-64
                bg-black
                border-r
                border-zinc-800/70
                p-5
                min-h-screen
                ">

            {mobile && (
            <div className="flex items-center justify-between mb-8">

            <h1 className="text-xl font-bold">
                Smart Finance
            </h1>

            <button
                onClick={onClose}
                className="
                w-10 h-10
                rounded-full
                bg-zinc-800
                flex
                items-center
                justify-center
                "
            >
                <X size={18} />
            </button>

            </div>
        )}

                {!mobile && (
                    <h1 className="text-2xl font-bold mb-10">
                    Smart Finance
                    </h1>
                )}

            <nav className="flex flex-col gap-2">

                {Links.map((link) => {

                    const isActive =
                    location.pathname === link.path

                    const Icon = link.icon

                    return (

                    <Link
                        to={link.path}
                        key={link.path}
                        className={`
                        flex
                        items-center
                        gap-3
                        px-4
                        py-3
                        rounded-2xl
                        transition-all
                        duration-300

                        ${
                            isActive
                            ? `
                                bg-purple-600/20
                                text-purple-400
                                border
                                border-purple-500/20
                                shadow-lg
                                shadow-purple-500/10
                            `
                            : `
                                text-zinc-400
                                hover:bg-zinc-800/70
                                hover:text-white
                            `
                        }
                        `}
                    >

                        <Icon size={18} />

                        <span>
                        {link.label}
                        </span>

                    </Link>

                    )

                })}

                </nav>
        </aside>
    )
}