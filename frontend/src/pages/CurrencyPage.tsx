import {
  QuickCurrencyConverter
} from "../features/dashboard/components/QuickCurrencyConverter"

import Card
from "../component/ui/Card"

import {
  usePopularCurrencies
} from "../features/currency/hooks/usePopularCurrencies"

export function CurrencyPage() {

    const {
    data: popularCurrencies,
} = usePopularCurrencies()



  return (

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
          Currency Converter
        </h1>

        <p
          className="
            text-zinc-400
            mt-2
          "
        >
          Convert currencies with live exchange rates
        </p>

      </div>

      {/* Main Converter */}
      <QuickCurrencyConverter />

      {/* Popular Currencies */}
      <Card>

        <div className="mb-6">

          <h2
            className="
              text-xl
              font-bold
              text-white
            "
          >
            Popular Currencies
          </h2>

          <p
            className="
              text-zinc-400
              text-sm
              mt-1
            "
          >
            Live market overview
          </p>

        </div>

        <div className="space-y-3">

          {popularCurrencies?.map((currency) => (

            <div
              key={currency.code}
              className="
                flex
                items-center
                justify-between
                p-4
                rounded-2xl
                border
                border-zinc-800
                bg-black/20
                hover:bg-zinc-900/40
                transition-all
                duration-300
              "
            >

              {/* Left */}
              <div>

                <h3
                  className="
                    text-white
                    font-semibold
                  "
                >
                  {currency.code}
                </h3>

                <p
                  className="
                    text-zinc-500
                    text-sm
                    mt-1
                  "
                >
                  Live Exchange Rate
                </p>

              </div>

              {/* Middle */}
              <div
                className="
                  text-white
                  font-bold
                  text-lg
                "
              >
                {currency.rate}
              </div>

              {/* Right */}
              <div
                className={`
                  text-sm
                  font-semibold
                    text-purple-400
                `}
              >
                Live
              </div>

            </div>

          ))}

        </div>

      </Card>

    </div>

  )
}