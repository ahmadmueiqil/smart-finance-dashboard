

import { useState }
from "react"

import Card
from "../component/ui/Card"

import { useTransactions }
from "../features/transactions/hooks/useTransactions"

import { TransactionCard }
from "../features/transactions/components/TransactionCard"

import { TransactionFilters }
from "../features/transactions/components/TransactionFilters"
import { Skeleton } from "../component/ui/Skeleton"


export function TransactionsPage(){

    const [page, setPage] =
    useState(0)

    const [filter, setFilter] = useState("ALL")


    const {
        data,
        isLoading,
    } = useTransactions(
        page,
        filter === "ALL"
            ? undefined
            : filter
        )


    

    return(

        <div className="space-y-6">

        {/* Header */}
        <div>

            <h1
            className="
                text-3xl
                font-bold
                text-white
            "
            >
            Transactions
            </h1>

            <p className="text-zinc-400 mt-2">
            Track all your financial activity
            </p>

        </div>



        <TransactionFilters
                        selected={filter}
                        onChange={(value) => {

                            setFilter(value)

                            setPage(0)

                        }}
                        />

        {/* Transactions */}
        <div className="space-y-4">

            {isLoading ? (

<div className="space-y-4">

    {[...Array(5)].map((_, i) => (

        <Card key={i}>

        <div
            className="
            flex
            items-center
            justify-between
            "
        >

            <div className="space-y-3">

            <Skeleton
                className="
                h-6
                w-52
                "
            />

            <Skeleton
                className="
                h-4
                w-28
                "
            />

            </div>

            <Skeleton
            className="
                h-10
                w-24
            "
            />

        </div>

        </Card>

    ))}

    </div>

            ) : data?.content.length === 0 ? (

            <Card>
                No transactions found
            </Card>

            ) : (

                data?.content.map((transaction) => (

                <TransactionCard
                    key={transaction.id}
                    transaction={transaction}
                />

                ))

            )}

        </div>

        {/* Pagination */}
        <div
            className="
            flex
            items-center
            justify-center
            gap-3
            pt-2
            "
        >

            <button
            onClick={() =>
                setPage((prev) =>
                Math.max(prev - 1, 0)
                )
            }
            disabled={page === 0}
            className="
                px-4
                py-2
                rounded-xl
                bg-zinc-900
                border
                border-zinc-800
                text-white
                disabled:opacity-40
                disabled:cursor-not-allowed
                hover:bg-zinc-800
                transition
            "
            >
            Previous
            </button>

            <div
            className="
                px-4
                py-2
                rounded-xl
                bg-purple-600/20
                border
                border-purple-500/20
                text-purple-400
                font-medium
            "
            >
            Page {page + 1}
            </div>

            <button
            onClick={() =>
                setPage((prev) =>
                prev + 1
                )
            }
            disabled={
                page + 1 >=
                (data?.totalPages ?? 0)
            }
            className="
                px-4
                py-2
                rounded-xl
                bg-zinc-900
                border
                border-zinc-800
                text-white
                disabled:opacity-40
                disabled:cursor-not-allowed
                hover:bg-zinc-800
                transition
            "
            >
            Next
            </button>

        </div>

        </div>
        
    )
}