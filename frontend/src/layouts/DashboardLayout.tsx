import { Outlet } from "react-router-dom"

import { Sidebar } from "../component/shared/Sidebar" 
import {Navbar} from "../component/shared/Navbar"
import { useState } from "react"

import {
    motion,
    AnimatePresence,
} from "framer-motion"

function DashboardLayout() {
    const [isSidebarOpen, setIsSidebarOpen] = useState(false)

    return (
        <div className="min-h-screen bg-black text-white">

        <div className="hidden md:flex">

            <Sidebar />

            <div className="flex-1 flex flex-col min-h-screen">

            <Navbar 
            onMenuClick={() => setIsSidebarOpen(true)}
            />

            <main className="flex-1 p-4 md:p-6 bg-zinc-950">
                <AnimatePresence mode="wait">

                    <motion.div
                        initial={{
                        opacity: 0,
                        y: 10,
                        }}

                        animate={{
                        opacity: 1,
                        y: 0,
                        }}

                        exit={{
                        opacity: 0,
                        y: -10,
                        }}

                        transition={{
                        duration: 0.25,
                        }}
                    >

                        <Outlet />

                    </motion.div>

                    </AnimatePresence>
            </main>

            </div>

        </div>

        {/* Mobile Layout */}
        <div className="lg:hidden">

            <Navbar
            onMenuClick={() => setIsSidebarOpen(true)}
            />

            <main className="p-4 bg-zinc-950 min-h-screen">
            <AnimatePresence mode="wait">

                <motion.div
                    initial={{
                    opacity: 0,
                    y: 10,
                    }}

                    animate={{
                    opacity: 1,
                    y: 0,
                    }}

                    exit={{
                    opacity: 0,
                    y: -10,
                    }}

                    transition={{
                    duration: 0.25,
                    }}
                >

                    <Outlet />

                </motion.div>

                </AnimatePresence>
            </main>

        </div>

            {isSidebarOpen && (
                <div
                className="
                    fixed
                    inset-0
                    bg-black/50
                    z-40
                    md:hidden
                "
                onClick={() => setIsSidebarOpen(false)}
                />
        )}

            <div
                className={`
                fixed
                top-0
                left-0
                h-full
                z-50
                transform
                transition-transform
                duration-300
                md:hidden
                ${
                    isSidebarOpen
                    ? "translate-x-0"
                    : "-translate-x-full"
                }
                `}
            >
                <Sidebar
                mobile
                onClose={() => setIsSidebarOpen(false)}
                />
        </div>

        </div>
    )
}

export default DashboardLayout