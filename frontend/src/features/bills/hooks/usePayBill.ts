import { useMutation, useQueryClient }
from "@tanstack/react-query"

import { payBill }
from "../services/service"

import { toast } from "sonner"

export function usePayBill() {

    const queryClient =
        useQueryClient()

    return useMutation({

        mutationFn: payBill,

        onSuccess: () => {

        queryClient.invalidateQueries({
            queryKey: ["bills"],
        })

        queryClient.invalidateQueries({
            queryKey: ["dashboard"],
        })

        toast.success(
                    "Bill paid successfully"
                    )
        },
        onError: () => 
        toast.error(
                    "Failed to pay bill"
                    )
    })
}