import { useState }
from "react"

import Card
from "../../../component/ui/Card"

import {
    ChevronDown,
    ArrowRightLeft,
    } from "lucide-react"

    import {
    useCurrencyConversion
    } from "../hooks/useCurrencyConversion"

    import {
    Skeleton
    } from "../../../component/ui/Skeleton"

    export function QuickCurrencyConverter() {

    const [fromCurrency, setFromCurrency] =
        useState("USD")

    const [toCurrency, setToCurrency] =
        useState("EUR")

    const [amount, setAmount] =
        useState(1)

    const {
        data,
        isLoading,
    } = useCurrencyConversion({
        fromCurrency,
        toCurrency,
        amount,
    })

    function handleSwap() {

        const oldFrom = fromCurrency

        setFromCurrency(toCurrency)

        setToCurrency(oldFrom)

    }

    return (

        <Card className="break-inside-avoid">

        {/* Header */}
        <div className="mb-6">

            <h2
            className="
                text-2xl
                font-bold
                text-white
            "
            >
            Quick Currency Converter
            </h2>

            <p
            className="
                text-zinc-400
                text-sm
                mt-1
            "
            >
            Convert currencies instantly
            </p>

        </div>

        <div className="space-y-5">

            {/* Amount */}
            <input
            type="number"
            value={amount}
            onChange={(e) =>
                setAmount(
                Number(e.target.value)
                )
            }
            placeholder="Amount"
            className="
                w-full
                bg-zinc-900/90
                border
                border-zinc-700
                rounded-2xl
                px-5
                py-4
                text-white
                text-lg
                outline-none
                transition-all
                duration-300
                focus:border-purple-500
                focus:ring-4
                focus:ring-purple-500/10
            "
            />

            {/* Currency Selects */}
            <div className="relative">

            <div className="grid grid-cols-2 gap-4">

                {/* From */}
                <div className="relative">

                <p
                    className="
                    text-xs
                    text-zinc-500
                    mb-2
                    ml-1
                    "
                >
                    From
                </p>

                <select
                    value={fromCurrency}
                    onChange={(e) =>
                    setFromCurrency(
                        e.target.value
                    )
                    }
                    className="
                    w-full
                    appearance-none
                    cursor-pointer
                    bg-zinc-900/90
                    border
                    border-zinc-700
                    rounded-2xl
                    px-4
                    py-4
                    pr-10
                    text-white
                    outline-none
                    transition-all
                    duration-300
                    hover:border-purple-500/40
                    focus:border-purple-500
                    focus:ring-4
                    focus:ring-purple-500/10
                    shadow-inner
                    shadow-black/20
                    "
                >

                    <option value="USD">
                    USD
                    </option>

                    <option value="EUR">
                    EUR
                    </option>

                    <option value="GBP">
                    GBP
                    </option>

                    <option value="JPY">
                    JPY
                    </option>

                    <option value="JOD">
                    JOD
                    </option>

                </select>

                <ChevronDown
                    size={18}
                    className="
                    absolute
                    right-4
                    top-[55px]
                    -translate-y-1/2
                    text-zinc-400
                    pointer-events-none
                    "
                />

                </div>

                {/* To */}
                <div className="relative">

                <p
                    className="
                    text-xs
                    text-zinc-500
                    mb-2
                    ml-1
                    "
                >
                    To
                </p>

                <select
                    value={toCurrency}
                    onChange={(e) =>
                    setToCurrency(
                        e.target.value
                    )
                    }
                    className="
                    w-full
                    appearance-none
                    cursor-pointer
                    bg-zinc-900/90
                    border
                    border-zinc-700
                    rounded-2xl
                    px-4
                    py-4
                    pr-10
                    text-white
                    outline-none
                    transition-all
                    duration-300
                    hover:border-purple-500/40
                    focus:border-purple-500
                    focus:ring-4
                    focus:ring-purple-500/10
                    shadow-inner
                    shadow-black/20
                    "
                >

                    <option value="EUR">
                    EUR
                    </option>

                    <option value="USD">
                    USD
                    </option>

                    <option value="GBP">
                    GBP
                    </option>

                    <option value="JPY">
                    JPY
                    </option>

                    <option value="JOD">
                    JOD
                    </option>

                </select>

                <ChevronDown
                    size={18}
                    className="
                    absolute
                    right-4
                    top-[55px]
                    -translate-y-1/2
                    text-zinc-400
                    pointer-events-none
                    "
                />

                </div>

            </div>

            {/* Swap Button */}
            <button
                onClick={handleSwap}
                className="
                absolute
                left-1/2
                top-[52px]
                -translate-x-1/2
                -translate-y-1/2
                w-11
                h-11
                rounded-full
                bg-purple-600
                border
                border-purple-500
                flex
                items-center
                justify-center
                text-white
                shadow-lg
                shadow-purple-500/20
                hover:scale-110
                hover:bg-purple-500
                transition-all
                duration-300
                "
            >

                <ArrowRightLeft size={18} />

            </button>

            </div>

            {/* Result */}
            <div
            className="
                bg-gradient-to-br
                from-purple-500/10
                to-zinc-900
                border
                border-zinc-800
                rounded-3xl
                p-6
            "
            >

            {isLoading ? (

                <div className="space-y-4">

                <Skeleton
                    className="
                    h-5
                    w-40
                    "
                />

                <Skeleton
                    className="
                    h-12
                    w-60
                    "
                />

                <Skeleton
                    className="
                    h-4
                    w-48
                    "
                />

                </div>

            ) : (

                <>

                {/* Direction */}
                <div
                    className="
                    flex
                    items-center
                    gap-2
                    text-sm
                    text-zinc-400
                    mb-4
                    "
                >

                    <span
                    className="
                        px-3
                        py-1
                        rounded-xl
                        bg-zinc-800
                        text-white
                        text-xs
                        font-medium
                    "
                    >
                    {fromCurrency}
                    </span>

                    <span>
                    →
                    </span>

                    <span
                    className="
                        px-3
                        py-1
                        rounded-xl
                        bg-purple-500/20
                        text-purple-300
                        text-xs
                        font-medium
                    "
                    >
                    {toCurrency}
                    </span>

                </div>

                {/* Result */}
                <h2
                    className="
                    text-5xl
                    font-bold
                    text-white
                    break-words
                    tracking-tight
                    "
                >

                    {Number(
                    data?.convertedAmount
                    ).toLocaleString("en-US")}

                    {" "}

                    <span
                    className="
                        text-2xl
                        text-zinc-400
                    "
                    >
                    {data?.toCurrency}
                    </span>

                </h2>

                {/* Details */}
                <div className="mt-6 space-y-2">

                    <p
                    className="
                        text-zinc-500
                        text-sm
                    "
                    >

                    {Number(
                        data?.amount
                    ).toLocaleString("en-US")}

                    {" "}

                    {data?.fromCurrency}

                    </p>

                    <p
                    className="
                        text-zinc-500
                        text-sm
                    "
                    >

                    1 {" "}
                    {data?.fromCurrency}

                    {" = "}

                    {data?.exchangeRate}

                    {" "}

                    {data?.toCurrency}

                    </p>

                </div>

                </>

            )}

            </div>

        </div>

        </Card>

    )
}