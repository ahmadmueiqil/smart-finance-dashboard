import {useMutation } from "@tanstack/react-query"
import { useNavigate } from "react-router-dom"

import { login } from "../services/auth.service"
import { useAuthStore } from "../store/auth.store"
import { AxiosError } from "axios"
import type { AuthError } from "../types/auth.types"
import { toast } from "sonner"
import { getProfile }
from "../../profile/services/profileService"



export function useLogin(){
    const setToken = useAuthStore((state)=> state.setToken)

    const setUser = useAuthStore((state)=> state.setUser)

    const navigate = useNavigate();

    return useMutation({
        mutationFn: login,

    onSuccess: async (data) => {

        setToken(data.data)

        try {

            const profile =
                await getProfile()

            setUser({

                username:
                    profile.username,

                email:
                    profile.email,

            })

        } catch (error) {

            console.error(
                "Failed to fetch profile",
                error
            )

        }

        toast.success(
            "Login successful"
        )

        navigate("/")

    },

        onError: (error) => {
        const axiosError = error as AxiosError<AuthError>
        toast.error(axiosError.response?.data.message || axiosError.message)
        }
    })
}