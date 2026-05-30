import Button from "../../../component/ui/Button";
import Card from "../../../component/ui/Card";


type Props = {
    transactions: Transaction[]
}

import { Link } from "react-router-dom"
import type { Transaction } from "../types/types";

export function RecentTransactions({transactions } : Props){

    return( 
    <Card className="break-inside-avoid">

        <div className="flex items-center justify-between mb-6">

            <h2 className="text-xl font-bold text-white">
            Recent Transactions
            </h2>

            <Link to="/transactions">

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

        {transactions.length === 0 ? (

            <div
            className="
                h-40
                flex
                items-center
                justify-center
                text-zinc-500
            "
            >
            No transactions found
            </div>

        ) : (

            <div className="space-y-4">

            {transactions.map((transaction) => (

                <div
                key={transaction.id}
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
                    rounded-2xl
                    p-4
                "
                >

                {/* Left */}
                <div>

                    <p className="text-white font-medium">
                    {transaction.description}
                    </p>

                    <p className="text-zinc-400 text-sm mt-1">
                    {transaction.transactionType}
                    </p>

                </div>

                {/* Right */}
                <div
                    className={`
                    font-bold
                    ${
                        (transaction.transactionType === "TRANSFER_IN" || transaction.transactionType=== "DEPOSIT")
                        ? "text-green-500"
                        : "text-red-500"
                        
                    }
                    `}
                >
                    {transaction.amount} JOD
                </div>

                </div>

            ))}

            </div>

        )}

    </Card>
    )

}