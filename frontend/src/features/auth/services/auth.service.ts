import {axiosInstance} from "../../../api/axios"
import type { LoginRequest, AuthResponse, RegisterRequest } from "../types/auth.types"


export const login = async (data: LoginRequest) =>{

    const response = await axiosInstance.post<AuthResponse>(
        "/auth/login",
        data
    )
    return response.data

}


export const registerUser = async (data: RegisterRequest) => {
    const response = await axiosInstance.post<AuthResponse>(
        "/auth/register",
        data
    )

    return response.data
}