import React from "react";
import { Link } from 'react-router-dom';
import { FiPower, FiEdit, FiTrash2 } from "react-icons/fi";

import './styles.css';
import logo from '../../assets/logo.png'

export default function Funcionarios(){
    return (
        <div className="funcionario-container">
            <header>
                <img src={logo} alt="SGAMO"/>
                <span>Bem Vindo, <strong>Usuário</strong>!</span>
                <Link className="button" to="/funcionario/new">Adicionar Novo Funcionário</Link>
                <button type="button">
                    <FiPower size={18} color="#251FC5"></FiPower>
                </button>
            </header>

            <h1>Listagem de Funcionários</h1>
            <ul>
                <li>
                    <strong>José Macedo</strong>
                    <p>023.023.023-02</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
                <li>
                    <strong>Maria Clara</strong>
                    <p>033.033.033-03</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
                <li>
                    <strong>Gabi Almeida</strong>
                    <p>021.013.021-01</p>

                    <button type="button">
                        <FiEdit size={20} color="#251fc5"/>
                    </button>

                    <button type="button">
                        <FiTrash2 size={20} color="#251fc5"/>
                    </button>
                </li>
            </ul>
        </div>
    )

}