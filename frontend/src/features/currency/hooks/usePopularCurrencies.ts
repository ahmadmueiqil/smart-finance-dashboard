
import { useQuery }
from "@tanstack/react-query"

import {
    getPopularCurrencies
    } from "../services/Currencyservice"

    export function usePopularCurrencies() {

    return useQuery({

        queryKey: [
        "popular-currencies"
        ],

        queryFn:
        getPopularCurrencies,

    })

}