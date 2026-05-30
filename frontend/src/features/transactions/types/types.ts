

export type TransactionType =
  | "DEPOSIT"
  | "EXPENSE"
  | "TRANSFER_IN"
  | "TRANSFER_OUT"

export interface Transaction {

  id: number

  amount: number

  transactionType: TransactionType

  fromWalletId: number | null

  toWalletId: number | null

  description: string

  createdAt: string
}

export interface TransactionsResponse {

  content: Transaction[]

  totalPages: number

  totalElements: number

  number: number

  size: number
}

export interface ApiResponse<T> {

  message: string

  data: T

  status: boolean
}