import { z } from "zod"

import { useForm }
from "react-hook-form"

import { zodResolver }
from "@hookform/resolvers/zod"

import Modal
from "../../../component/ui/Modal"

import Input
from "../../../component/ui/Input"

import Button
from "../../../component/ui/Button"

import { useCreateBill }
from "../hooks/useCreateBill"



const schema = z.object({

    billTitle:
        z.string()
        .min(2),

    category:
        z.string()
        .min(1),

    amount:
        z.coerce.number()
        .positive(),
})

type CreateBillFormData  =
    z.infer<typeof schema>

type Props = {
    open: boolean
    onClose: () => void
}

export function CreateBillModal({
    open,
    onClose,
}: Props){


    const createBillMutation = useCreateBill()

        const {
        register,
        handleSubmit,
        reset,
        formState: { errors },
    } = useForm<
    z.input<typeof schema>,
    unknown,
    z.output<typeof schema>>({
        resolver: zodResolver(schema),
    })

        const onSubmit = (
        data: CreateBillFormData 
        ) => {

        createBillMutation.mutate(
        data,
        {
            onSuccess: () => {
            reset()
            onClose()
            },
        }
        )

    }

    return (

        <Modal
        open={open}
        onClose={onClose}
        >

        <div className="space-y-6">

            {/* Header */}
            <div>

            <h2
                className="
                text-2xl
                font-bold
                text-white
                "
            >
                Create Bill
            </h2>

            <p className="text-zinc-400 mt-2">
                Add a new bill to track
            </p>

            </div>

            {/* Form */}
            <form
            onSubmit={
                handleSubmit(onSubmit)
            }
            className="space-y-5"
            >

            {/* Title */}
            <div>

                <label
                className="
                    text-sm
                    text-zinc-300
                    mb-2
                    block
                "
                >
                Title
                </label>

                <Input
                {...register("billTitle")}
                placeholder="Netflix Subscription"
                />

                {
                errors.billTitle && (
                    <p
                    className="
                        text-red-400
                        text-sm
                        mt-2
                    "
                    >
                    {
                        errors.billTitle.message
                    }
                    </p>
                )
                }

            </div>

            {/* Bill Type */}
            <div>

                <label
                className="
                    text-sm
                    text-zinc-300
                    mb-2
                    block
                "
                >
                Bill Type
                </label>

                <select
                {...register("category")}
                className="
                    w-full
                    bg-zinc-900
                    border
                    border-zinc-800
                    rounded-xl
                    px-4
                    py-3
                    text-white
                    outline-none
                    focus:border-purple-500
                "
                >

                <option value="">
                    Select Type
                </option>

                <option value="ELECTRICITY">
                    Electricity
                </option>

                <option value="INTERNET">
                    Internet
                </option>

                <option value="MOBILE">
                    Mobile
                </option>

                <option value="LOANS">
                    Loans
                </option>

                <option value="CUSTOMBILL">
                    Custom Bill
                </option>

                </select>

            </div>

            {/* Amount */}
            <div>

                <label
                className="
                    text-sm
                    text-zinc-300
                    mb-2
                    block
                "
                >
                Amount
                </label>

                <Input
                type="number"
                {...register("amount")}
                placeholder="50"
                />

                {
                errors.amount && (
                    <p
                    className="
                        text-red-400
                        text-sm
                        mt-2
                    "
                    >
                    {
                        errors.amount.message
                    }
                    </p>
                )
                }

            </div>

            {/* Actions */}
            <div
                className="
                flex
                justify-end
                gap-3
                pt-2
                "
            >

                <Button
                type="button"
                variant="secondary"
                onClick={onClose}
                >
                Cancel
                </Button>

                <Button
                type="submit"
                >
                {
                    createBillMutation.isPending
                    ? "Creating..."
                    : "Create Bill"
                }
                </Button>

            </div>

            </form>

        </div>

        </Modal>

    )
}