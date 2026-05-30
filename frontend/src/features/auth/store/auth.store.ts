    import {create} from "zustand"

    type User = {

    username: string

    email: string

    }

    type AuthState = {

    token: string | null

    isAuthanticated: boolean

    user: User | null

    setToken: (
        token: string
    ) => void

    setUser: (
        user: User
    ) => void

    logout: () => void

    }


    export const useAuthStore =
    create<AuthState>((set) => ({

        token:
        localStorage.getItem("token"),

        isAuthanticated:
        !!localStorage.getItem("token"),

        user:
        JSON.parse(
            localStorage.getItem("user")
            || "null"
        ),

        setToken: (token) => {

        localStorage.setItem(
            "token",
            token
        )

        set({

            token,

            isAuthanticated: true,

        })

        },

        setUser: (user) => {

        localStorage.setItem(
            "user",
            JSON.stringify(user)
        )

        set({
            user
        })

        },

        logout: () => {

        localStorage.removeItem(
            "token"
        )

        localStorage.removeItem(
            "user"
        )

        set({

            token: null,

            user: null,

            isAuthanticated: false,

        })

        },

    }))