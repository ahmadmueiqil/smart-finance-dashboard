import { useState } from "react"

import { useBills }
from "../features/bills/hooks/useBills"

import Card from "../component/ui/Card"

import { usePayBill }
from "../features/bills/hooks/usePayBill"
import Button from "../component/ui/Button"
import { CreateBillModal } from "../features/bills/components/CreateBillModal"
import { Skeleton } from "../component/ui/Skeleton"



export function BillsPage(){


    const [page, setPage] =
        useState(0)

    const [status, setStatus] =
    useState("")
    
    const [open, setOpen] = useState(false)

    const {
        data,
        isLoading,
    } = useBills(page, status)

    const [pendingBillId, setPendingBillId] = useState<number | null>(null)

    const payBillMutation = usePayBill()

    const handlePayBill = (
                billId: number
                ) => {

                setPendingBillId(billId)

                payBillMutation.mutate(
                    billId,
                    {
                    onSettled: () => {
                        setPendingBillId(null)
                    },
                    }
                )
                }

    return(
        <div className="space-y-6">

        <div
            className="
                flex
                items-center
                justify-between
                gap-4
                flex-wrap
            "
            >

            <div>

                <h1
                className="
                    text-3xl
                    font-bold
                    text-white
                "
                >
                Bills
                </h1>

                <p className="text-zinc-400 mt-2">
                Manage and track your bills
                </p>

            </div>

            <Button
                onClick={() => setOpen(true)}
                className="
                    shadow-lg
                    shadow-purple-500/20
                    hover:shadow-purple-500/40
                    transition-all
                    duration-300
                "
                >
                + Create Bill
            </Button>

            </div>

            <div className="flex gap-3 flex-wrap">

                <button
                        onClick={() => {
                        setStatus("")
                        setPage(0)
                        }}
                    className={`
                        px-4
                        py-2
                        rounded-xl
                        transition-all
                        duration-300
                        border

                        ${
                        status === ""
                            ? `
                            bg-purple-600
                            text-white
                            border-purple-500
                            shadow-lg
                            shadow-purple-500/20
                            `
                            : `
                            bg-zinc-900
                            text-zinc-400
                            border-zinc-800
                            hover:bg-zinc-800
                            `
                        }
                    `}
                    >
                    All
                </button>

                <button
                    onClick={() => {
                                setStatus("PENDING")
                                setPage(0)
                                }}
                    className={`
                        px-4
                        py-2
                        rounded-xl
                        transition-all
                        duration-300
                        border

                        ${
                        status === "PENDING"
                            ? `
                            bg-yellow-500/20
                            text-yellow-400
                            border-yellow-500/30
                            shadow-lg
                            shadow-yellow-500/10
                            `
                            : `
                            bg-zinc-900
                            text-zinc-400
                            border-zinc-800
                            hover:bg-zinc-800
                            `
                        }
                    `}
                    >
                    Pending
                </button>

                <button
                        onClick={() => {
                        setStatus("PAID")
                        setPage(0)
                        }}
                    className={`
                        px-4
                        py-2
                        rounded-xl
                        transition-all
                        duration-300
                        border

                        ${
                        status === "PAID"
                            ? `
                            bg-emerald-500/20
                            text-emerald-400
                            border-emerald-500/30
                            shadow-lg
                            shadow-emerald-500/10
                            `
                            : `
                            bg-zinc-900
                            text-zinc-400
                            border-zinc-800
                            hover:bg-zinc-800
                            `
                        }
                    `}
                    >
                    Paid
                </button>

                    </div>

                    

        <div className="space-y-4">

            {isLoading ? (
<div className="space-y-4">

    {[...Array(4)].map((_, i) => (

        <Card
        key={i}
        className="
            relative
            overflow-hidden
        "
        >

        <div
            className="
            flex
            flex-col
            lg:flex-row
            lg:items-center
            lg:justify-between
            gap-6
            "
        >

            {/* Left */}
            <div
            className="
                flex
                items-start
                gap-4
                flex-1
            "
            >

            {/* Icon */}
            <Skeleton
                className="
                w-14
                h-14
                rounded-2xl
                shrink-0
                "
            />

            {/* Content */}
            <div className="space-y-3 w-full">

                <Skeleton
                className="
                    h-6
                    w-52
                "
                />

                <Skeleton
                className="
                    h-4
                    w-32
                "
                />

            </div>

            </div>

            {/* Right Panel */}
            <div
            className="
                min-w-[220px]
                space-y-4
            "
            >

            <Skeleton
                className="
                h-10
                w-28
                ml-auto
                "
            />

            <Skeleton
                className="
                h-8
                w-20
                ml-auto
                "
            />

            <Skeleton
                className="
                h-11
                w-full
                "
            />

            </div>

        </div>

        </Card>

    ))}

    </div>

            ) : data?.content.length === 0 ? (

            <Card>
                No bills found
            </Card>

            ) : (

            data?.content.map((bill) => (

                <Card
                key={bill.id}
                className="
                    relative
                    overflow-hidden
                    hover:border-purple-500/20
                    transition-all
                    duration-300
                    hover:-translate-y-1
                "
                >

                {/* Glow */}
                <div
                    className="
                    absolute
                    top-0
                    right-0
                    w-40
                    h-40
                    bg-purple-500/10
                    blur-3xl
                    rounded-full
                    pointer-events-none
                    "
                />

                <div
                    className="
                    relative
                    flex
                    flex-col
                    lg:flex-row
                    lg:items-center
                    lg:justify-between
                    gap-6
                    "
                >

                    {/* Left Side */}
                    <div className="flex-1 min-w-0">

                    <div
                        className="
                        flex
                        items-start
                        gap-4
                        "
                    >

                        {/* Icon */}
                        <div
                        className="
                            w-14
                            h-14
                            rounded-2xl
                            bg-purple-500/10
                            border
                            border-purple-500/20
                            flex
                            items-center
                            justify-center
                            text-purple-400
                            text-xl
                            shrink-0
                        "
                        >
                        💳
                        </div>

                        {/* Content */}
                        <div className="min-w-0">

                        <h2
                            className="
                            text-xl
                            font-semibold
                            text-white
                            truncate
                            "
                        >
                            {bill.title}
                        </h2>

                        <div
                            className="
                            flex
                            items-center
                            gap-2
                            mt-2
                            flex-wrap
                            "
                        >

                            <p className="text-zinc-400 text-sm">
                            {bill.billType}
                            </p>

                            <span className="text-zinc-600">
                            •
                            </span>

                            <p className="text-zinc-500 text-sm">
                            Bill #{bill.id}
                            </p>

                        </div>

                        </div>

                    </div>

                    </div>

                    {/* Right Panel */}
                    <div
                    className="
                        bg-black/30
                        border
                        border-zinc-800/70
                        rounded-2xl
                        p-4
                        min-w-[220px]
                        flex
                        flex-col
                        items-start
                        lg:items-end
                        gap-3
                        backdrop-blur-sm
                    "
                    >

                    {/* Amount */}
                    <div>

                        <p className="text-zinc-500 text-sm">
                        Amount
                        </p>

                        <h2
                        className="
                            text-3xl
                            font-bold
                            text-white
                            mt-1
                        "
                        >
                        {bill.amount} JOD
                        </h2>

                    </div>

                    {/* Status */}
                    <div
                        className={`
                        text-xs
                        px-3
                        py-1
                        rounded-full
                        border
                        font-medium
                        ${
                            bill.status === "PAID"
                            ? `
                                bg-emerald-500/15
                                text-emerald-400
                                border-emerald-500/20
                            `
                            : `
                                bg-yellow-500/15
                                text-yellow-400
                                border-yellow-500/20
                            `
                        }
                        `}
                    >
                        {bill.status}
                    </div>

                    {/* Action */}
                    {
                        bill.status === "PENDING" && (

                        <button
                            onClick={() =>
                            handlePayBill(bill.id)
                            }
                            disabled={
                            pendingBillId === bill.id
                            }
                            className="
                            w-full
                            lg:w-auto
                            px-5
                            py-2.5
                            rounded-xl
                            bg-gradient-to-r
                            from-purple-600
                            to-fuchsia-600
                            hover:opacity-90
                            text-white
                            font-medium
                            transition-all
                            duration-300
                            disabled:opacity-50
                            disabled:cursor-not-allowed
                            shadow-lg
                            shadow-purple-500/20
                            "
                        >

                            {
                            pendingBillId === bill.id
                                ? "Paying..."
                                : "Pay Bill"
                            }

                        </button>

                        )
                    }

                    </div>

                </div>

                </Card>

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
                pt-4
            "
            >

            {/* Previous */}
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

            {/* Current Page */}
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

            {/* Next */}
            <button
                onClick={() =>
                setPage((prev) => prev + 1)
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

            <CreateBillModal
            open={open}
            onClose={() => setOpen(false)}
            />
        </div>
    )
}