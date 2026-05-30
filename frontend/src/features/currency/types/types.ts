

export type PopularCurrency = {

    code: string

    rate: number

}

export interface ApiResponse<T> {
    message: string

    data: T

    status: boolean
}