import type { InputHTMLAttributes } from "react"

type Props = InputHTMLAttributes<HTMLInputElement>

function Input({className = "", ...props}: Props){
    return (
        <input 
            className={`
            w-full
            bg-zinc-900
            border border-zinc-800
            rounded-lg
            px-4 py-3
            text-white
            outline-none
            focus:border-white
            transition
            ${className}
        `} {...props}/>
    )
}

export default Input;