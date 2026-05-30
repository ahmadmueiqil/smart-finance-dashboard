import { useQuery } from "@tanstack/react-query"

import { getBills }
from "../services/service"


export function useBills(
    page:number,
    status: string
){

    return(
        useQuery({
            queryKey: [
                "bills",
                page,
                status
            ],

            queryFn: () => 
                getBills(page, status)
            ,
        })
    )

}