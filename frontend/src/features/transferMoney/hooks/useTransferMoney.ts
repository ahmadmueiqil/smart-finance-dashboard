


import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";

import {
    transferMoney
} from "../services/TransferService";

export function useTransferMoney() {

    const queryClient = useQueryClient();

    return useMutation({

        mutationFn: transferMoney,

        onSuccess: () => {

            toast.success(
                "Transfer completed successfully"
            );

            queryClient.invalidateQueries({
                queryKey: ["dashboard"],
            });

            queryClient.invalidateQueries({
                queryKey: ["transactions"],
            });
        },

        onError: () => {
            toast.error(
                "Transfer failed"
            );
        },
    });
}