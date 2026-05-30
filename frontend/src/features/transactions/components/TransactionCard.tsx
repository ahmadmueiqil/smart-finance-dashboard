
import {
    ArrowDownLeft,
    ArrowUpRight,
    } from "lucide-react"

    import Card
    from "../../../component/ui/Card"

    import type {
    Transaction
    } from "../types/types"

    import {
    formatDistanceToNow
    } from "date-fns"

    type Props = {
    transaction: Transaction
    }

    export function TransactionCard({
    transaction,
    }: Props) {

    const isIncome =
        transaction.transactionType ===
        "TRANSFER_IN"
        ||
        transaction.transactionType ===
        "DEPOSIT"

    return (

        <Card
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
            className={`
            absolute
            top-0
            right-0
            w-40
            h-40
            blur-3xl
            rounded-full
            pointer-events-none
            ${
                isIncome
                ? "bg-emerald-500/10"
                : "bg-red-500/10"
            }
            `}
        />

        <div
            className="
            relative
            flex
            flex-col
            sm:flex-row
            sm:items-center
            sm:justify-between
            gap-5
            "
        >

            {/* Left */}
            <div
            className="
                flex
                items-start
                gap-4
                min-w-0
            "
            >

            {/* Icon */}
            <div
                className={`
                w-14
                h-14
                rounded-2xl
                border
                flex
                items-center
                justify-center
                shrink-0
                ${
                    isIncome
                    ? `
                        bg-emerald-500/10
                        border-emerald-500/20
                        text-emerald-400
                    `
                    : `
                        bg-red-500/10
                        border-red-500/20
                        text-red-400
                    `
                }
                `}
            >

                {
                isIncome
                    ? <ArrowDownLeft size={24} />
                    : <ArrowUpRight size={24} />
                }

            </div>

            {/* Content */}
            <div className="min-w-0">

                <h2
                className="
                    text-lg
                    font-semibold
                    text-white
                    truncate
                "
                >
                {transaction.description}
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

                <p
                    className="
                    text-zinc-400
                    text-sm
                    "
                >
                    {
                    transaction.transactionType
                    }
                </p>

                <span className="text-zinc-600">
                    •
                </span>

                <p
                    className="
                    text-zinc-500
                    text-sm
                    "
                >
                    {
                    formatDistanceToNow(
                        new Date(
                        transaction.createdAt
                        ),
                        {
                        addSuffix: true,
                        }
                    )
                    }
                </p>

                </div>

            </div>

            </div>

            {/* Right */}
            <div
            className="
                flex
                flex-col
                items-start
                sm:items-end
                gap-2
            "
            >

            <h2
                className={`
                text-3xl
                font-bold
                ${
                    isIncome
                    ? "text-emerald-400"
                    : "text-red-400"
                }
                `}
            >

                {
                isIncome
                    ? "+"
                    : "-"
                }

                {transaction.amount} JOD
                

            </h2>

            </div>

        </div>

        </Card>

    )
}