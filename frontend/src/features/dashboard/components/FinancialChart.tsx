import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts"
import { Cell } from "recharts"

import Card from "../../../component/ui/Card"

type Props = {
    Transfer_in: number
    expenses: number
    Transfer_out: number
    }

    function FinancialChart({
    Transfer_in,
    expenses,
    Transfer_out,
    }: Props) {

    const data = [
        {
            name: "Transfer in",
            amount: Transfer_in,
            color: "#22c55e",
        },
        {
            name: "Expenses",
            amount: expenses,
            color: "#ef4444",
        },
        {
            name: "Transfer out",
            amount: Transfer_out,
            color: "#ef4444"
        }
    ]

    return (
        <Card className="break-inside-avoid">
        <div className="mb-6">

            <h2 className="text-xl font-bold text-white">
            Financial Overview
            </h2>

            <p className="text-zinc-300 text-sm mt-1">
            Transfer in vs Expenses and Transfer out
            </p>

        </div>

        <div className="h-[300px]">

            <ResponsiveContainer
            width="100%"
            height="100%"
            >

            <BarChart data={data}>

                <XAxis dataKey="name" />

                <YAxis />

                <Tooltip />

        <Bar
        dataKey="amount"
        radius={[10, 10, 0, 0]}
        >

        {data.map((entry, index) => (
            <Cell
            key={index}
            fill={entry.color}
            />
        ))}

        </Bar>

            </BarChart>

            </ResponsiveContainer>

        </div>
        </Card>
    )
}

export default FinancialChart