import   type {ReactNode}  from "react";
import Card from "../../../component/ui/Card";
import { Skeleton } from "../../../component/ui/Skeleton";


type Props = {
    title: string,
    value: string,
    icon?: ReactNode
}

export function StateCard({
    title,
    value,
    icon,}
: Props){

    return (
        <Card>
            <div className="flex items-start justify-between ">
                <div>
                    <p className="text-zinc-400 text-sm mb-3">{title}</p>
                    {
                        value ? (

                            <h2 className="text-3xl font-bold text-white">
                            {value}
                            </h2>

                        ) : (

                            <Skeleton
                            className="
                                h-10
                                w-32
                            "
                            />

                        )
                        }
                </div>


                {icon && (
                    <div  className="
                            w-12 h-12
                            rounded-2xl
                            bg-white/5
                            backdrop-blur-sm
                            border
                            border-white/10
                            flex
                            items-center
                            justify-center
                            "
                >
                        {icon}
                    </div>
                )}
            </div>
        </Card>
    )
 }