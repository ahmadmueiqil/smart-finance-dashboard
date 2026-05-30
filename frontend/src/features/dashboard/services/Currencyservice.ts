import { axiosInstance }
from "../../../api/axios"

import type {
    CurrencyConversionResponse,
} from "../types/types"

interface ApiResponse<T> {

    message: string

    data: T

    status: boolean

}

type ConvertCurrencyRequest = {

    fromCurrency: string

    toCurrency: string

    amount: number

}

export async function convertCurrency(
    request: ConvertCurrencyRequest
    ) {

    const response =
        await axiosInstance.post<
        ApiResponse<
            CurrencyConversionResponse
        >
        >(
        "/currency/convert",
        request
        )

    return response.data.data

}