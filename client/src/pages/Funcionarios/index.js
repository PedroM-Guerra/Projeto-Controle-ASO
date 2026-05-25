import React, {useState, useEffect} from "react";
import { useNavigate, Link } from "react-router-dom";
import { FiPower, FiEdit, FiSearch } from "react-icons/fi";

import api from "../../services/api";

import './styles.css';
import logo from '../../assets/logo.png'

export default function Funcionario(){

    const [funcionarios, setFuncionarios] = useState([]);
    const [page, setPage] = useState(0);
    const [searchTerm, setSearchTerm] = useState('');

    const navigate = useNavigate();

    async function editFuncionario(id) {
        try {
            navigate(`/funcionario/new/${id}`)
        } catch (error) {
            alert("Edição de Funcionário falhou, tente novamente.")
        }
    }

    async function fetchFuncionarios(searchPage = 0, isNewSearch = false) {
        try {
            let response;
            
            if (searchTerm.trim() !== '') {
                response = await api.get(`/api/funcionario/v1/findFuncionarioByName/${searchTerm}`, {
                params: {
                    page: searchPage,
                    limit: 4,
                    direction: 'asc'
                }
                });
            } else {
                response = await api.get('/api/funcionario/v1', {
                    params: {
                        page: searchPage,
                        limit: 4,
                        direction: 'asc'
                    }
                });
            }

            const data = response.data._embedded?.funcionarios || [];

            if (isNewSearch) {
                setFuncionarios(data);
                setPage(1);
            } else {
                setFuncionarios([ ...funcionarios, ...data]);
                setPage(searchPage + 1);
            }
        } catch (error) {
            alert("Erro ao buscar funcionários.");
        }
    }

    function handleSearch(e) {
        e.preventDefault();
        fetchFuncionarios(0, true);
    }

    useEffect(() => {
        fetchFuncionarios(0, true);
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

            <div className="list-header">
                <h1>Listagem de Funcionários</h1>
                
                <form onSubmit={handleSearch} className="search-form">
                    <input 
                        type="text" 
                        placeholder="Pesquisar por nome..." 
                        value={searchTerm}
                        onChange={e => setSearchTerm(e.target.value)}
                    />
                    <button type="submit">
                        <FiSearch size={16} />
                    </button>
                </form>
            </div>

            <ul>
                {funcionarios.map(funcionario =>(
                    <li key={funcionario.id}>
                        <strong>{funcionario.nome}</strong>
                        <p>{funcionario.cpf}</p>

                        <button onClick={() => editFuncionario(funcionario.id)} type="button">
                            <FiEdit size={20} color="#251fc5"/>
                        </button>
                    </li>
                ))}
            </ul>

            {/* CORREÇÃO AQUI: Passando explicitamente o estado da página atual */}
            <button className="button" onClick={() => fetchFuncionarios(page, false)} type="button">Carregar Mais</button>
        </div>
    );
}