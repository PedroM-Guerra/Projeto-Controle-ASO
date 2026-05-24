import React from "react";
import { Link } from "react-router-dom";
import { FiArrowLeft } from "react-icons/fi";

import './styles.css';

import logo from '../../assets/logo.png'

export default function NewFuncionario(){
    return(
        <div className="new-funcionario-container">
            <div className="content">
                <section className="form">
                    <img src={logo} alt="SGAMO"/>
                    <h1>Cadastrar Novo Funcionário</h1>
                    <p>Preencha as informações do funcionário</p>
                    <Link className="back-link" to="/funcionarios">
                        <FiArrowLeft size={16} color="#251fc5"/>
                        Voltar
                    </Link>
                </section>
                <form>
                    <input placeholder="Nome"/>
                    <input placeholder="CPF"/>
                    <input placeholder="Matrícula"/>
                    <input placeholder="Gênero Biológico"/>
                    <input placeholder="Data de Nascimento"/>
                    <input placeholder="Data de Admissão"/>
                    <input placeholder="Data de Demissão"/>
                    <input placeholder="Setor"/>
                    <input placeholder="Setor"/>

                    <button className="button" type="submit">Add</button>
                </form>
            </div>
        </div>
    );
}