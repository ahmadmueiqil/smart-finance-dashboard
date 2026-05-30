export interface Bill {
    id: number

    title: string

    billType: string

    status: "PENDING" | "PAID"

    amount: number

    createdAt: string
}

export interface BillsResponse {
    content: Bill[]

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