import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Funcionarios from "./pages/Funcionarios";
import NewFuncionario from "./pages/NewFuncionario";


export default function AppRoutes(){
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/funcionarios" element={<Funcionarios/>}></Route>
                <Route path="/funcionario/new/:funcionarioId" element={<NewFuncionario/>}></Route>
            </Routes>
        </BrowserRouter>
    )
}