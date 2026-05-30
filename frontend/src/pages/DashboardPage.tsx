
import {
  Wallet,
  Receipt,
  ArrowLeftRight,
} from "lucide-react"
import {RecentBills} from "../features/dashboard/components/RecentBills"

import { StateCard } from "../features/dashboard/components/StatCard"
import { useDashboard } from "../features/dashboard/hooks/useDashboard"
import {RecentTransactions} from "../features/dashboard/components/RecentTransactions"
import FinancialChart from "../features/dashboard/components/FinancialChart"
import {QuickCurrencyConverter}
from "../features/dashboard/components/QuickCurrencyConverter"
import {
  useAuthStore
} from "../features/auth/store/auth.store"


export function DashboardPage() {

    const {data, isLoading} = useDashboard()

    const user =
    useAuthStore(
        (state) => state.user
    )


return (
    
    <div className="space-y-6">

        <div>
            <h1 className="text-3xl font-bold text-white">Welcome Back, {user?.username} 👋</h1>
            <p className="text-zinc-400 mt-2">Here's what's happening with your finances today.</p>
        </div>



            <div className=" grid grid-cols-1 md:grid-cols-1 lg:grid-cols-1 xl:grid-cols-3 gap-6 ">

            <StateCard title="Total Balance"
                        value={
                            isLoading
                            ? ""
                            : `${data?.balance ?? 0} JOD`
                        }
                        icon={<Wallet size={22}/>}></StateCard>
            <StateCard
                    title="Pending Bills"
                    value={
                        isLoading
                        ? ""
                        : `${data?.pindingBills ?? 0}`
                    }
                    icon={<Receipt size={22} />}
            />
            <StateCard
                        title="Transactions"
                        value={
                            isLoading
                            ? ""
                            : `${data?.lastTransactions?.content.length ?? 0}`
                        }
                        icon={<ArrowLeftRight size={22} />}
            />
            </div>

            <div
            className="
                columns-1
                xl:columns-3
                gap-6
                space-y-6
                
            "
            >


            <RecentTransactions
                transactions={data?.lastTransactions.content ?? []}
                />
                <RecentBills
                        bills={data?.lastBills.content ?? []}
                        />

                <QuickCurrencyConverter />

                <FinancialChart
                Transfer_in={Number(data?.totalTransferIn ?? 0)}
                expenses={Number(data?.totalExpanses ?? 0)}
                Transfer_out={Number(data?.totalTransferOut ?? 0)}
                />
                
                
        </div>

        

    </div>

)
}