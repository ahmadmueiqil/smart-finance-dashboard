

type Props = {

    selected: string

    onChange: (
        value: string
    ) => void
    }

        const filters = [

        {
            label: "All",
            value: "ALL",
        },

        {
            label: "Deposit",
            value: "DEPOSIT",
        },

        {
            label: "Expense",
            value: "EXPENSE",
        },

        {
            label: "Transfer In",
            value: "TRANSFER_IN",
        },

        {
            label: "Transfer Out",
            value: "TRANSFER_OUT",
        },

        ]

    export function TransactionFilters({
    selected,
    onChange,
    }: Props) {

    return (

        <div
        className="
            flex
            gap-3
            flex-wrap
        "
        >

        {
            filters.map((filter) => {

            const active =
                selected === filter.value

            return (

                <button
                key={filter.value}
                onClick={() =>
                    onChange(filter.value)
                }
                className={`
                    px-4
                    py-2
                    rounded-xl
                    border
                    transition-all
                    duration-300
                    font-medium

                    ${
                    active
                        ? `
                        bg-purple-600
                        border-purple-500
                        text-white
                        shadow-lg
                        shadow-purple-500/20
                        `
                        : `
                        bg-zinc-900
                        border-zinc-800
                        text-zinc-400
                        hover:bg-zinc-800
                        hover:text-white
                        `
                    }
                `}
                >

                {filter.label}

                </button>

            )

            })
        }

        </div>

    )
}