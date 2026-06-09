import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Funcionarios from "./pages/Funcionarios";
import NewFuncionario from "./pages/NewFuncionario";
import Asos from './pages/Asos';
import NewAso from './pages/NewAso';


export default function AppRoutes(){
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Funcionarios />} />
                <Route path="/funcionario/:funcionarioId/aso/new/:asoId" element={<NewAso/>}></Route>
                <Route path="/funcionarios" element={<Funcionarios/>}></Route>
                <Route path="/funcionario/new/:funcionarioId" element={<NewFuncionario/>}></Route>
                <Route path="/funcionario/:funcionarioId/asos" element={<Asos/>}></Route>
                
            </Routes>
        </BrowserRouter>
    )
}
