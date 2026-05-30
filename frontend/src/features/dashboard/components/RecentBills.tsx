import Button from "../../../component/ui/Button"
import Card from "../../../component/ui/Card"

import type { Bill } from "../types/types"

type Props = {
    bills: Bill[]
}

import { Link } from "react-router-dom"


export function RecentBills({bills} : Props){
    return(
        <Card className="break-inside-avoid">
                <div className="flex items-center justify-between mb-6">

                    <h2 className="text-xl font-bold text-white">
                    Recent Bills
                    </h2>

            <Link to="/bills">

                <Button
                    className="
                    text-sm
                    text-purple-500
                    hover:text-purple-400
                    transition
                    bg-[#372656]
                    "
                >
                    View All
                </Button>

            </Link>

                </div>

                {bills.length === 0 ? (

                    <div
                    className="
                        h-40
                        flex
                        items-center
                        justify-center
                        text-zinc-500
                    "
                    >
                    No bills found
                    </div>

                        ) : (

                    <div className="space-y-4">

                    {bills.map((bill) => (

                        <div
                            key={bill.id}
                            className="
                                flex
                                items-center
                                justify-between
                                bg-black/30
                                hover:bg-zinc-800/40
                                transition-all
                                duration-300
                                border
                                border-zinc-800/70
                                hover:border-purple-500/20
                                rounded-2xl
                                p-4
                            "
                            >

                            {/* Left */}
                            <div className="min-w-0">

                                <p
                                className="
                                    text-white
                                    font-semibold
                                    truncate
                                "
                                >
                                {bill.billTitle}
                                </p>

                                <div
                                className="
                                    flex
                                    items-center
                                    gap-2
                                    mt-1
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
                                    #{bill.id}
                                </p>

                                </div>

                            </div>

                            {/* Right */}
                            <div className="text-right shrink-0 ml-4">

                                <p className="text-white font-bold text-lg">
                                {bill.amount} JOD
                                </p>

                                <div
                                className={`
                                    mt-2
                                    text-xs
                                    px-3
                                    py-1
                                    rounded-full
                                    inline-flex
                                    items-center
                                    gap-1
                                    font-medium
                                    ${
                                    bill.status === "PAID"
                                        ? `
                                        bg-emerald-500/15
                                        text-emerald-400
                                        border
                                        border-emerald-500/20
                                        `
                                        : `
                                        bg-yellow-500/15
                                        text-yellow-400
                                        border
                                        border-yellow-500/20
                                        `
                                    }
                                `}
                                >
                                {bill.status}
                                </div>

                            </div>

                            </div>

                    ))}

                    </div>

                )}

        </Card>
    )
}