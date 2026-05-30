import {axiosInstance} from "../../../api/axios"


import type {
  ApiResponse,
  BillsResponse,
} from "../types/types"

export async function getBills(
    page = 0,
    status?: string
){
    const params = new URLSearchParams()

    params.append("page", page.toString())

    if (status) {
        params.append("status", status)
    }

    const response =
    await axiosInstance.get<
        ApiResponse<BillsResponse>
        >(
        `/bills?${params.toString()}`
    )
    console.log(response.data.data)

    return response.data.data
}


export async function payBill(
    billId: number
    ) {

    const response =
        await axiosInstance.post(
        "/bill/payments",
        {
            billId,
        }
        )

    return response.data
}


interface CreateBillData {
    billTitle: string
    category: string
    amount: number
}

export async function createBill(
    data: CreateBillData
    ) {

    const response =
        await axiosInstance.post(
        "/bill",
        data
        )

    return response.data
}