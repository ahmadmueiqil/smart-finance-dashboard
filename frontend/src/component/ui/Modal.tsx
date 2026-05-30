import {
    useEffect,
    type ReactNode,
    } from "react"
    
    import {
    AnimatePresence,
    motion,
    } from "framer-motion"

    type Props = {
    open: boolean
    onClose: () => void
    children: ReactNode
    }

    function Modal({
    open,
    onClose,
    children,
    }: Props) {

    useEffect(() => {

        if (!open) return

        const handleEscape = (
        e: KeyboardEvent
        ) => {

        if (e.key === "Escape") {
            onClose()
        }

        }

        document.body.style.overflow =
        "hidden"

        window.addEventListener(
        "keydown",
        handleEscape
        )

        return () => {

        document.body.style.overflow =
            "auto"

        window.removeEventListener(
            "keydown",
            handleEscape
        )

        }

    }, [open, onClose])

    return (

            <AnimatePresence>

                {open && (

                <motion.div
                    initial={{
                    opacity: 0,
                    }}

                    animate={{
                    opacity: 1,
                    }}

                    exit={{
                    opacity: 0,
                    }}

                    className="
                    fixed
                    inset-0
                    z-50
                    flex
                    items-center
                    justify-center
                    bg-black/70
                    backdrop-blur-md
                    p-4
                    "
                    onClick={onClose}
                >

                    <motion.div
                    initial={{
                        opacity: 0,
                        scale: 0.95,
                        y: 20,
                    }}

                    animate={{
                        opacity: 1,
                        scale: 1,
                        y: 0,
                    }}

                    exit={{
                        opacity: 0,
                        scale: 0.95,
                        y: 20,
                    }}

                    transition={{
                        duration: 0.25,
                    }}

                    className="
                        w-full
                        max-w-lg
                        bg-gradient-to-br
                        from-zinc-900
                        to-black
                        border
                        border-zinc-800/80
                        rounded-3xl
                        p-6
                        shadow-2xl
                        shadow-black/50
                    "
                    onClick={(e) =>
                        e.stopPropagation()
                    }
                    >

                    {children}

                    </motion.div>

                </motion.div>

                )}

            </AnimatePresence>

)
}

export default Modal