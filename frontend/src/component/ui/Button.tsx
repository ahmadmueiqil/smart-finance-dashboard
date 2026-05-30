import type { ButtonHTMLAttributes, ReactNode } from "react";


type Props = {
    children: ReactNode
    variant?: "primary" | "secondary" | "danger"
} & ButtonHTMLAttributes<HTMLButtonElement>

 function Button({
    children,
    variant = "primary",
    className = "",
    ...props
}: Props){
    const baseStyles =
    "px-4 py-2 rounded-lg font-medium transition disabled:opacity-50 disabled:cursor-not-allowed"

   const variants = {

  primary:
    `
      bg-purple-600
      text-white
      hover:bg-purple-500
      shadow-lg
      shadow-purple-500/20
    `,

  secondary:
    `
      bg-zinc-800
      text-white
      hover:bg-zinc-700
    `,

  danger:
    `
      bg-red-500
      text-white
      hover:bg-red-400
      shadow-lg
      shadow-red-500/20
    `,
}

    return(
        <button className={`
            cursor-pointer
            ${baseStyles}
            ${variants[variant]}
            ${className}
        `}
            {...props}>
                {children}
        </button>
    )
}

export default Button;