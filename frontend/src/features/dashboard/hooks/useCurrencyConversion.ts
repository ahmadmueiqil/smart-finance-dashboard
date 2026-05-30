import { useQuery }
from "@tanstack/react-query"

import { convertCurrency }
from "../services/Currencyservice"

type Props = {

  fromCurrency: string

  toCurrency: string

  amount: number

}

export function useCurrencyConversion({
  fromCurrency,
  toCurrency,
  amount,
}: Props) {

  return useQuery({

    queryKey: [
      "currency",
      fromCurrency,
      toCurrency,
      amount,
    ],

    queryFn: () =>
      convertCurrency({
        fromCurrency,
        toCurrency,
        amount,
      }),

    enabled:
      !!fromCurrency &&
      !!toCurrency &&
      amount > 0,

  })
}