import type { ReactNode }
from "react"

import { motion }
from "framer-motion"

type Props = {
  children: ReactNode
  className?: string
}

function Card({
  children,
  className = "",
}: Props) {

  return (

    <motion.div

      initial={{
        opacity: 0,
        y: 20,
      }}

      animate={{
        opacity: 1,
        y: 0,
      }}

      transition={{
        duration: 0.35,
      }}

      className={`
        bg-gradient-to-br
        from-zinc-900
        to-zinc-950
        border
        border-zinc-800/80
        rounded-3xl
        p-6
        h-fit
        break-inside-avoid
        shadow-lg
        shadow-black/20
        backdrop-blur-sm
        hover:border-zinc-700
        transition-all
        duration-300
        ${className}
      `}
    >

      {children}

    </motion.div>

  )
}

export default Card