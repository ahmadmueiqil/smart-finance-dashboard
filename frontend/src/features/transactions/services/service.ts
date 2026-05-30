import { axiosInstance }
from "../../../api/axios"

import type {
    ApiResponse,
    TransactionsResponse,
    } from "../types/types"

    export async function getTransactions(
    page = 0,
    type?: string
    ) {

    const params =
        new URLSearchParams()

    params.append(
        "page",
        page.toString()
    )

    if (
        type &&
        type !== "ALL"
    ) {

        params.append(
        "type",
        type
        )

    }

    const response =
        await axiosInstance.get<
        ApiResponse<TransactionsResponse>
        >(
        `/transactions?${params.toString()}`
        )

    return response.data.data
}