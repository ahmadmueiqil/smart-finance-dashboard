import { z } from "zod"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import { Link } from "react-router-dom"

import Card from "../component/ui/Card"
import Input from "../component/ui/Input"
import Button from "../component/ui/Button"

import { useRegister } from "../features/auth/hooks/useRegister"


const registerSchema = z
    .object({
        email: z.string().email("Invalid email"),

        username: z
        .string()
        .min(3, "Username must be at least 3 characters"),

        password: z
        .string()
        .min(6, "Password must be at least 6 characters"),

        confirmPassword: z
        .string()
        .min(6, "Confirm your password"),
    })

    .refine((data) => data.password === data.confirmPassword, {
        message: "Passwords do not match",
        path: ["confirmPassword"],
    })

type RegisterData = z.infer<typeof registerSchema>

    export function RegisterPage() {
    const registerMutation = useRegister()

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<RegisterData>({
        resolver: zodResolver(registerSchema),
    })

    const onSubmit = (data: RegisterData) => {
        registerMutation.mutate(data)
    }


    return (
        <div
        className="
            min-h-screen
            bg-black
            flex
            items-center
            justify-center
            px-4
            py-8
        "
        >
        <div className="w-full max-w-md">

            <Card>

            {/* Logo */}
            <div className="flex flex-col items-center mb-10">

                <div
                className="
                    w-16 h-16
                    rounded-2xl
                    bg-purple-600
                    flex
                    items-center
                    justify-center
                    text-3xl
                    font-bold
                    mb-4
                    shadow-lg
                    shadow-purple-600/30
                    relative
                "
                >
                <span
                    className="
                    absolute
                    top-[43%]
                    left-1/2
                    -translate-x-1/2
                    -translate-y-1/2
                    "
                >
                    💳
                </span>
                </div>

                <h1 className="text-3xl md:text-4xl font-bold text-white text-center">
                Create Account
                </h1>

                <p className="text-zinc-400 mt-2 text-sm md:text-base text-center">
                Join Smart Finance Today
                </p>

            </div>

            {/* Form */}
            <form
                onSubmit={handleSubmit(onSubmit)}
                className="space-y-5"
            >

                {/* Username */}
                <div>

                <label
                    className="
                    block
                    text-sm
                    text-zinc-300
                    mb-2
                    "
                >
                    Full Name
                </label>

                <Input
                    {...register("username")}
                    placeholder="Enter your name"
                />

                {errors.username && (
                    <p className="text-red-500 text-sm mt-2">
                    {errors.username.message}
                    </p>
                )}

                </div>

                {/* Email */}
                <div>

                <label
                    className="
                    block
                    text-sm
                    text-zinc-300
                    mb-2
                    "
                >
                    Email
                </label>

                <Input
                    type="email"
                    {...register("email")}
                    placeholder="example@gmail.com"
                />

                {errors.email && (
                    <p className="text-red-500 text-sm mt-2">
                    {errors.email.message}
                    </p>
                )}

                </div>

                {/* Password */}
                <div>

                <label
                    className="
                    block
                    text-sm
                    text-zinc-300
                    mb-2
                    "
                >
                    Password
                </label>

                <Input
                    type="password"
                    {...register("password")}
                    placeholder="********"
                />

                {errors.password && (
                    <p className="text-red-500 text-sm mt-2">
                    {errors.password.message}
                    </p>
                )}

                </div>

                {/* Confirm Password */}
                <div>

                <label
                    className="
                    block
                    text-sm
                    text-zinc-300
                    mb-2
                    "
                >
                    Confirm Password
                </label>

                <Input
                    type="password"
                    {...register("confirmPassword")}
                    placeholder="********"
                />

                {errors.confirmPassword && (
                    <p className="text-red-500 text-sm mt-2">
                    {errors.confirmPassword.message}
                    </p>
                )}

                </div>

                {/* Terms */}
                <div
                className="
                    flex
                    items-center
                    gap-2
                    text-sm
                    text-zinc-400
                "
                >

                <input
                    type="checkbox"
                    className="accent-purple-600"
                />

                <p>
                    I agree to the{" "}

                    <span
                    className="
                        text-purple-500
                        hover:text-purple-400
                        cursor-pointer
                        transition
                    "
                    >
                    Terms & Conditions
                    </span>

                </p>

                </div>

                {/* Submit Button */}
                <Button
                type="submit"
                className="
                    w-full
                    bg-purple-600
                    hover:bg-purple-700
                    text-black
                    py-3
                    cursor-pointer
                "
                disabled={registerMutation.isPending}
                >
                {registerMutation.isPending
                    ? "Creating Account..."
                    : "Register"}
                </Button>

            </form>

            {/* Footer */}
            <div
                className="
                mt-8
                text-center
                text-sm
                text-zinc-400
                "
            >

                Already have an account?{" "}

                <Link
                to="/login"
                className="
                    text-purple-500
                    hover:text-purple-400
                    transition
                "
                >
                Login
                </Link>

            </div>

            </Card>

        </div>
        </div>
    )
}