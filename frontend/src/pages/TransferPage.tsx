import { useState } from "react";
import { Send } from "lucide-react";

import Card from "../component/ui/Card";
import Input from "../component/ui/Input";
import Button from "../component/ui/Button";

import {
    useTransferMoney
} from "../features/transferMoney/hooks/useTransferMoney";

export function TransferPage() {

    const [toWalletId, setToWalletId] = useState("");
    const [amount, setAmount] = useState("");

    const transferMutation = useTransferMoney();

    function handleTransfer() {

        if (!toWalletId || !amount) return;

        transferMutation.mutate({
            toWalletId: Number(toWalletId),
            amount: Number(amount),
        });
    }

    return (

        <div className="max-w-2xl mx-auto">

            <Card className="p-8">

                <div className="flex items-center gap-3 mb-8">

                    <div
                        className="
                        w-12 h-12
                        rounded-2xl
                        bg-purple-600/20
                        flex
                        items-center
                        justify-center
                        "
                    >
                        <Send
                            size={22}
                            className="text-purple-400"
                        />
                    </div>

                    <div>

                        <h1
                            className="
                            text-3xl
                            font-bold
                            text-white
                            "
                        >
                            Transfer Money
                        </h1>

                        <p className="text-zinc-400">
                            Send funds securely between wallets
                        </p>

                    </div>

                </div>

                <div className="space-y-5">

                    <div>

                        <label
                            className="
                            block
                            text-sm
                            text-zinc-400
                            mb-2
                            "
                        >
                            Receiver Wallet ID
                        </label>

                        <Input
                            placeholder="Enter wallet id"
                            value={toWalletId}
                            onChange={(e) =>
                                setToWalletId(
                                    e.target.value
                                )
                            }
                        />

                    </div>

                    <div>

                        <label
                            className="
                            block
                            text-sm
                            text-zinc-400
                            mb-2
                            "
                        >
                            Amount (JOD)
                        </label>

                        <Input
                            type="number"
                            placeholder="0.00"
                            value={amount}
                            onChange={(e) =>
                                setAmount(
                                    e.target.value
                                )
                            }
                        />

                    </div>

                    <Button
                        onClick={handleTransfer}
                        disabled={
                            transferMutation.isPending
                        }
                        className="w-full"
                    >
                        {
                            transferMutation.isPending
                                ? "Processing..."
                                : "Transfer Money"
                        }
                    </Button>

                </div>

            </Card>

        </div>
    );
}