import {useMutation } from "@tanstack/react-query"
import { useNavigate } from "react-router-dom"

import { registerUser } from "../services/auth.service"
import { useAuthStore } from "../store/auth.store"
import { toast } from "sonner"
import { AxiosError } from "axios"
import type { AuthError } from "../types/auth.types"


export function useRegister(){
    const setToken = useAuthStore((state)=> state.setToken) 
    const navigate = useNavigate();

    return useMutation({
        mutationFn: registerUser,
        onSuccess: (data) =>{
            setToken(data.data)
            toast.success("Register successfully")
            navigate("/")
        },

        onError: (error) =>{
            const axiosError = error as AxiosError<AuthError>
            toast.error(axiosError.response?.data.message || axiosError.message)
        }
    })
}

