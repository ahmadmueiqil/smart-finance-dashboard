

import { axiosInstance } from "../../../api/axios";

export interface TransferMoneyRequest {
    toWalletId: number;
    amount: number;
}

export async function transferMoney(
    data: TransferMoneyRequest
) {
    const response = await axiosInstance.post(
        "/transactions/transfer",
        data
    );

    return response.data;
}