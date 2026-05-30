

export interface Transaction {
    id: number
    amount: number
    description: string
    transactionType: string
    fromWalletId?: number
    toWalletId?: number
}


export interface Bill{
    id : number
    billType: string
    status: string
    amount: number
    billTitle: string
}

export interface PageResponse<T> {
    content: T[]
    totalElements: number
    totalPages: number
    number: number
    size: number
}

export interface DashboardResponse {
    balance: number

    pindingBills: number

    totalDeposits: number

    totalExpanses: number

    lastTransactions: PageResponse<Transaction>

    totalTransferIn: number

    totalTransferOut: number

    lastBills: PageResponse<Bill>
}

export type CurrencyConversionResponse = {

    amount: number

    convertedAmount: number

    exchangeRate: number

    fromCurrency: string

    toCurrency: string

}