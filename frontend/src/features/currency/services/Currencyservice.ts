import { axiosInstance } from "../../../api/axios"
import type { ApiResponse } from "../../currency/types/types"
import type {
  PopularCurrency
} from "../types/types"

export async function getPopularCurrencies() {

    const response =
        await axiosInstance.get<
        ApiResponse<
            PopularCurrency[]
        >
        >(
        "/currency/popular"
        )

    return response.data.data

}