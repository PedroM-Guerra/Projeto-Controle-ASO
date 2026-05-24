import React, {useState, useEffect} from "react";
import { useNavigate, Link } from "react-router-dom";
import { FiPower, FiEdit, FiTrash2 } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';
import logo from '../../assets/logo.png'

export default function Funcionario(){

    const [funcionarios, setFuncionarios] = useState([]);
    const [page, setPage] = useState(0);

    const navigate = useNavigate();

    async function editFuncionario(id) {
        try {
            navigate(`/funcionario/new/${id}`)
        } catch (error) {
            alert("Edição de Funcionário falhou, tente novamente.")
        }
    }

    async function fetchMoreFuncionarios() {
        const response = await api.get('/api/funcionario/v1', {
            params: {
                page: page,
                limit: 4,
                direction: 'asc'
            }
        })

        setFuncionarios([ ...funcionarios, ...response.data._embedded.funcionarios]);
        setPage(page + 1);
    }
    
    useEffect(() => {
        fetchMoreFuncionarios();
    }, []);

    return (
        <div className="funcionario-container">
            <header>
                <img src={logo} alt="SGAMO"/>
                <span>Bem Vindo, <strong>Usuário</strong>!</span>
                <Link className="button" to="/funcionario/new/0">Adicionar Novo Funcionário</Link>
                <button type="button">
                    <FiPower size={18} color="#251FC5"></FiPower>
                </button>
            </header>

            <h1>Listagem de Funcionários</h1>
            <ul>
                {funcionarios.map(funcionario =>(
                    <li>
                        <strong>{funcionario.nome}</strong>
                        <p>{funcionario.cpf}</p>

                        <button onClick={() => editFuncionario(funcionario.id)} type="button">
                            <FiEdit size={20} color="#251fc5"/>
                        </button>
                    </li>
                ))}
            </ul>

            <button className="button" onClick={fetchMoreFuncionarios} type="button">Carregar Mais </button>
        </div>
    )

}