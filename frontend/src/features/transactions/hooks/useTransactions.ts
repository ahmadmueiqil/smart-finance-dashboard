import { useQuery }
from "@tanstack/react-query"

import { getTransactions }
from "../services/service"

export function useTransactions(
    page: number,
    type?: string
    ) {

    return useQuery({

        queryKey: [
        "transactions",
        page,
        type,
        ],

        queryFn: () =>
        getTransactions(
            page,
            type
        ),

    })
}