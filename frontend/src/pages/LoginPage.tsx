import {z} from "zod"
import {useForm} from "react-hook-form"
import { useLogin } from "../features/auth/hooks/useLogin"
import {zodResolver} from "@hookform/resolvers/zod"
import Card from "../component/ui/Card"
import Input from "../component/ui/Input"
import Button from "../component/ui/Button"
import { Link } from "react-router-dom"


const loginDataSchema = z.object({
    email: z.string().email("Invalid email"),

    password: z.string().min(6,"password must be more then 6 character")
})

type loginFormData = z.infer<typeof loginDataSchema>

export function LoginPage(){
    const loginMutation = useLogin()
    

    const{
        register,
        handleSubmit,
        formState: {errors},
    } = useForm<loginFormData>({
        resolver: zodResolver(loginDataSchema),
    })

    const onSubmit = (data: loginFormData) => {
        loginMutation.mutate(data)
    }

    return(
        <div className="
            min-h-screen
            bg-black
            flex
            items-center
            justify-center
            px-4
            py-8
        ">
            <div className="w-full max-w-md">
                <Card>

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
                                relative
                            "
                            >
                                <span className="absolute top-[43%] left-1/2 -translate-x-1/2 -translate-y-1/2">💳</span>
                            
                        </div>
                        <h1 className="text-3xl md:text-4xl font-bold text-white">Smart Finance</h1>
                        <p className="text-zinc-400 mt-2 text-sm md:text-base">Manage your money smartly</p>
                    </div>



                    <form onSubmit={handleSubmit(onSubmit)}
                    className="space-y-5"
                    >
                        <div>
                            <label className="block text-sm text-zinc-300 mb-2">
                                Email
                            </label>

                            <Input {...register("email")}
                            placeholder="example@gmail.com"
                            type="email"/>

                            {errors.email && (
                                <p className="text-red-500 text-sm mt-2">
                                    {errors.email.message}
                                </p>
                            )}
                        </div>

                        <div>
                            <label className="block text-sm text-zinc-300 mb-2">
                                Password
                            </label>

                            <Input {...register("password")}
                            placeholder="Password"
                            type="password"/>

                            {errors.password && (
                                <p className="text-red-500 text-sm mt-2">
                                    {errors.password.message}
                                </p>
                            )}
                        </div>
                        
                        <Button type="submit"
                        className="w-full mt-2 cursor-pointer"
                        disabled={loginMutation.isPending}>
                            {loginMutation.isPending
                                ? "Logging in..."
                                : "Login"}
                            </Button>
                    </form>



                    <div className="mt-8 text-center text-sm text-zinc-400">

                        Don&apos;t have an account?{" "}

                        <Link
                            to="/register"
                            className="
                            text-purple-500
                            hover:text-purple-400
                            transition
                            "
                        >
                            Register
                        </Link>

                        </div>


                </Card>
            </div>
        </div>
    )

}