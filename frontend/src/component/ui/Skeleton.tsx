type Props = {
  className?: string
}

export function Skeleton({
  className = "",
}: Props) {

  return (

    <div
      className={`
        relative
        overflow-hidden
        rounded-2xl
        bg-zinc-900
        border
        border-zinc-800/60
        ${className}
      `}
    >

      {/* Shimmer */}
      <div
        className="
          absolute
          inset-0
          -translate-x-full
          animate-[shimmer_2s_infinite]
          bg-gradient-to-r
          from-transparent
          via-white/10
          to-transparent
        "
      />

    </div>

  )
}