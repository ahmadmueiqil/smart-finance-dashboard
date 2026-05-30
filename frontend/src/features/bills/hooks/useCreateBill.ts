import {
    useMutation,
    useQueryClient,
    } from "@tanstack/react-query"

    import { toast } from "sonner"

    import { createBill }
    from "../services/service"

    export function useCreateBill() {

    const queryClient =
        useQueryClient()

    return useMutation({

        mutationFn: createBill,

        onSuccess: () => {

        toast.success(
            "Bill created successfully"
        )

        queryClient.invalidateQueries({
            queryKey: ["bills"],
        })

        queryClient.invalidateQueries({
            queryKey: ["dashboard"],
        })

        },

        onError: () => {

        toast.error(
            "Failed to create bill"
        )

        },

    })
}