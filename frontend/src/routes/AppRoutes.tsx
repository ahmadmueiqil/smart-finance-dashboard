import { BrowserRouter, Route, Routes } from "react-router-dom";
import { AuthLayout } from "../layouts/AuthLayout";
import  {LoginPage}  from "../pages/LoginPage";
import { RegisterPage } from "../pages/RegisterPage";
import DashboardLayout from "../layouts/DashboardLayout"
import { BillsPage } from "../pages/BillsPage";
import { CurrencyPage } from "../pages/CurrencyPage";
import { TransactionsPage } from "../pages/TransactionsPage";
import { DashboardPage } from "../pages/DashboardPage";
import { ProtectedRoute } from "./ProtectedRoute";
import { PublicRoute } from "./PublicRoute";
import { ProfilePage }
from "../pages/ProfilePage"
import { TransferPage } from "../pages/TransferPage";



export function AppRoutes(){


    return(
        <BrowserRouter>

        <Routes>

        <Route element={<PublicRoute/>}>

            <Route element = {<AuthLayout />}>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/register" element={<RegisterPage/>}/>
            </Route>
        </Route>

            <Route element={<ProtectedRoute />}>

                <Route element={<DashboardLayout/>}>
                
                    <Route path="/" element={<DashboardPage/>}/>
                    <Route path="/bills" element={<BillsPage/>}/>
                    <Route path="/currency" element={<CurrencyPage/>}/>
                    <Route path="/transactions" element={<TransactionsPage/>}/>
                    <Route path="/transfer" element={<TransferPage />} />
                    <Route path="/profile" element={<ProfilePage />} />

                </Route>

            </Route>
        </Routes>

        
        </BrowserRouter>
    )
}