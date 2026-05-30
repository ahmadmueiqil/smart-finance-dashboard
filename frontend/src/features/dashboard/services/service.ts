
import {axiosInstance} from "../../../api/axios"

import type { DashboardResponse } from "../types/types"

interface ApiResponse<T>{
    message: string
    data: T
    state: boolean
}

export async function getDashboard() {
    const response =
        await axiosInstance.get<ApiResponse<DashboardResponse>>(
        "/dashboard"
        )
        console.log(response)
    return response.data.data
}