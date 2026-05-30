import { useState }
from "react"

import Card
from "../component/ui/Card"

import {
  Wallet,
  Mail,
  BadgeDollarSign,
} from "lucide-react"

import {
  useProfile
} from "../features/profile/hooks/useProfile"

import {
  Skeleton
} from "../component/ui/Skeleton"

import Modal
from "../component/ui/Modal"

import Input
from "../component/ui/Input"

import Button
from "../component/ui/Button"

import {
  useUpdateProfile
} from "../features/profile/hooks/useUpdateProfile"

import { toast }
from "sonner"

export function ProfilePage() {

  const {
    data,
    isLoading,
  } = useProfile()

  const [open, setOpen] =
    useState(false)

  const [username, setUsername] =
    useState("")

  const [email, setEmail] =
    useState("")

  const updateProfileMutation =
    useUpdateProfile()

  function handleOpenModal() {

    setUsername(
      data?.username ?? ""
    )

    setEmail(
      data?.email ?? ""
    )

    setOpen(true)

  }

  async function handleUpdateProfile() {

    try {

      await updateProfileMutation.mutateAsync({

        username,
        email,

      })

      toast.success(
        "Profile updated successfully"
      )

      setOpen(false)

    } catch (error: any) {

      toast.error(
        error?.response?.data?.message
        ||
        "Something went wrong"
      )

    }

  }

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
          Profile
        </h1>

        <p
          className="
            text-zinc-400
            mt-2
          "
        >
          Manage your account information
        </p>

      </div>

      {/* Profile Card */}
      <Card>

        {isLoading ? (

          <div className="space-y-4">

            <Skeleton
              className="
                h-16
                w-16
                rounded-full
              "
            />

            <Skeleton
              className="
                h-6
                w-40
              "
            />

            <Skeleton
              className="
                h-4
                w-64
              "
            />

          </div>

        ) : (

          <div className="space-y-8">

            {/* Top */}
            <div
              className="
                flex
                items-center
                justify-between
                flex-wrap
                gap-5
              "
            >

              <div
                className="
                  flex
                  items-center
                  gap-5
                "
              >

                <div
                  className="
                    w-20
                    h-20
                    rounded-full
                    bg-purple-600/20
                    border
                    border-purple-500/30
                    flex
                    items-center
                    justify-center
                    text-3xl
                    font-bold
                    text-purple-400
                  "
                >
                  {data?.username.charAt(0)}
                </div>

                <div>

                  <h2
                    className="
                      text-2xl
                      font-bold
                      text-white
                    "
                  >
                    {data?.username}
                  </h2>

                  <p className="text-zinc-400">
                    Smart Finance User
                  </p>

                </div>

              </div>

              <Button
                onClick={handleOpenModal}
                className="
                  bg-purple-600
                  hover:bg-purple-500
                  text-white
                "
              >
                Edit Profile
              </Button>

            </div>

            {/* Info Grid */}
            <div
              className="
                grid
                grid-cols-1
                md:grid-cols-2
                gap-4
              "
            >

              {/* Email */}
              <div
                className="
                  p-5
                  rounded-2xl
                  border
                  border-zinc-800
                  bg-black/20
                "
              >

                <div
                  className="
                    flex
                    items-center
                    gap-3
                    mb-3
                  "
                >

                  <Mail
                    size={18}
                    className="
                      text-purple-400
                    "
                  />

                  <p
                    className="
                      text-zinc-400
                      text-sm
                    "
                  >
                    Email
                  </p>

                </div>

                <h3
                  className="
                    text-white
                    font-medium
                  "
                >
                  {data?.email}
                </h3>

              </div>

              {/* Wallet */}
              <div
                className="
                  p-5
                  rounded-2xl
                  border
                  border-zinc-800
                  bg-black/20
                "
              >

                <div
                  className="
                    flex
                    items-center
                    gap-3
                    mb-3
                  "
                >

                  <Wallet
                    size={18}
                    className="
                      text-purple-400
                    "
                  />

                  <p
                    className="
                      text-zinc-400
                      text-sm
                    "
                  >
                    Wallet ID
                  </p>

                </div>

                <h3
                  className="
                    text-white
                    font-medium
                  "
                >
                  #{data?.walletId}
                </h3>

              </div>

              {/* Balance */}
              <div
                className="
                  p-5
                  rounded-2xl
                  border
                  border-zinc-800
                  bg-black/20
                  md:col-span-2
                "
              >

                <div
                  className="
                    flex
                    items-center
                    gap-3
                    mb-3
                  "
                >

                  <BadgeDollarSign
                    size={18}
                    className="
                      text-green-400
                    "
                  />

                  <p
                    className="
                      text-zinc-400
                      text-sm
                    "
                  >
                    Current Balance
                  </p>

                </div>

                <h2
                  className="
                    text-4xl
                    font-bold
                    text-white
                  "
                >
                  {Number(
                    data?.balance
                  ).toLocaleString(
                    "en-US"
                  )} JOD
                </h2>

              </div>

            </div>

          </div>

        )}

      </Card>

      {/* Edit Modal */}
      <Modal
        open={open}
        onClose={() =>
          setOpen(false)
        }
      >

        <div className="space-y-5">

          <div>

            <h2
              className="
                text-2xl
                font-bold
                text-white
              "
            >
              Edit Profile
            </h2>

            <p
              className="
                text-zinc-400
                mt-1
                text-sm
              "
            >
              Update your account information
            </p>

          </div>

        <label className="text-sm text-zinc-300 block">
            Username
        </label>

        <Input
            placeholder="Enter username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
        />

        <label className="text-sm text-zinc-300 block">
            Email
        </label>

        <Input
            type="email"
            placeholder="Enter email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
        />

          <Button
            onClick={handleUpdateProfile}
            disabled={
              updateProfileMutation.isPending
            }
            className="
              w-full
              bg-purple-600
              hover:bg-purple-500
              text-white
            "
          >

            {
              updateProfileMutation.isPending
                ? "Saving..."
                : "Save Changes"
            }

          </Button>

        </div>

      </Modal>

    </div>

  )
}