


import { axiosInstance }
from "../../../api/axios"

import type {
    ProfileResponse
    } from "../types/types"

    import type {
  UpdateProfileRequest
} from "../types/types"

    interface ApiResponse<T> {

    message: string

    data: T

    status: boolean

}

export async function getProfile() {

    const response =
        await axiosInstance.get<
        ApiResponse<
            ProfileResponse
        >
        >(
        "/profile"
        )

    return response.data.data

}


export async function updateProfile(
    request: UpdateProfileRequest
    ) {

    const response =
        await axiosInstance.put(
        "/profile",
        request
        )

    return response.data

}